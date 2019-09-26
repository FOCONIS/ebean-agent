package io.ebean.enhance.common;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.UUID;

/**
 * Hold a StateCache, where we store already seen files.
 *
 * @author Roland Praml, FOCONIS AG
 */
class StateCache {

  private static final StateCache INSTANCE = new StateCache();

  private Properties enhanceCache;

  public static StateCache getInstance() {
    return INSTANCE.enhanceCache != null ? INSTANCE : null;
  }

  /**
   * Constructor.
   *
   */
  private StateCache() {
    Path file = Paths.get("target");
    if (Files.exists(file)) {
      Path propFile = file.resolve("enhance-cache.properties");
      enhanceCache = new Properties();
      if (Files.exists(propFile)) {
        try (Reader rd = Files.newBufferedReader(propFile)) {
          enhanceCache.load(rd);
          System.out.println("Read " + enhanceCache.size() + " entries from " + propFile);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          try (Writer out = Files.newBufferedWriter(propFile)) {
            enhanceCache.store(out, "classes, which need no enhancement");
            System.out.println("Stored " + enhanceCache.size() + " entries to " + propFile);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
    }
  }

  /**
   * checks the cache, if the bytecode needs enhancement.
   */
  boolean isSkipEnhance(String className, byte[] bytes) {
    String uuidStr = enhanceCache.getProperty(className);
    if (uuidStr != null) {
      // build checksum of file
      UUID uuid = UUID.nameUUIDFromBytes(bytes);
      if (uuidStr.equals(uuid.toString())) {
        return true;
      } else {
        enhanceCache.remove(className);
      }
    }
    return false;
  }

  /**
   * stores in the cache, that this bytecode needs no enhancement.
   */
  void setSkipEnhance(String className, byte[] bytes) {
    UUID uuid = UUID.nameUUIDFromBytes(bytes);
    enhanceCache.setProperty(className, uuid.toString());
  }
}
