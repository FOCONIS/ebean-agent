package io.ebean.enhance.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hold a StateCache, where we store already seen files.
 *
 * @author Roland Praml, FOCONIS AG
 */
class StateCache {

  private static final Map<Path, StateCache> CACHES = new ConcurrentHashMap<>();

  private Path dataFile;
  private Map<UUID, byte[]> cache = new ConcurrentHashMap<>();
  private String lastClass;
  private static final byte[] EMPTY = new byte[] {};
  public static StateCache get(Path basePath) {
    return CACHES.computeIfAbsent(basePath, StateCache::new);
  }
  /**
   * Constructor.
   *
   */
  private StateCache(Path basePath) {
    this.dataFile = basePath.resolve("ebean-enhance.cache");
    if (Files.exists(dataFile)) {
      loadCache();
    }
    Runtime.getRuntime().addShutdownHook(new Thread(this::saveCache));
  }

  synchronized void loadCache() {
    long start = System.nanoTime();
    try (InputStream is = Files.newInputStream(dataFile);
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is))) {
      int count = ois.readInt();
      for (int i = 0; i < count; i++) {
        long msb = ois.readLong();
        long lsb = ois.readLong();
        int len = ois.readInt();
        UUID uuid = new UUID(msb, lsb);
        if (len == 0) {
          cache.put(uuid, EMPTY);
        } else {
          byte[] buf = new byte[len];
          ois.readFully(buf);
          cache.put(uuid, buf);
        }
      }
      start = (System.nanoTime() - start) / 1_000_000;
      System.out.println("Read " + cache.size() + " entries from " + dataFile + " in " + start + " ms");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  synchronized void saveCache() {
    if (lastClass != null) {
      try (OutputStream os = Files.newOutputStream(dataFile);
          ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os))) {
        oos.writeInt(cache.size());
        for (Entry<UUID, byte[]> entry : cache.entrySet()) {
          UUID uuid = entry.getKey();
          byte[] buf = entry.getValue();
          oos.writeLong(uuid.getMostSignificantBits());
          oos.writeLong(uuid.getLeastSignificantBits());
          oos.writeInt(buf.length);
          oos.write(buf);
        }
        System.out.println("Stored " + cache.size() + " entries to " + dataFile + "(last class: " + lastClass + ")");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Cache clean, " + cache.size() + " entries");
    }
  }

  void putCache(String className, byte[] bytes, byte[] enhancedBytes) {
    lastClass = className;
    UUID uuid = UUID.nameUUIDFromBytes(bytes);
    cache.put(uuid, enhancedBytes);
  }

  byte[] getCache(String className, byte[] bytes) {
    UUID uuid = UUID.nameUUIDFromBytes(bytes);
    return cache.get(uuid);
  }
}
