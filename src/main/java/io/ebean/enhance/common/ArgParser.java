package io.ebean.enhance.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Helper to parse javaagent or ant string arguments.
 */
class ArgParser {

  /**
  * Parse the args returning as name value pairs.
  */
  static HashMap<String,String> parse(String args){

    HashMap<String,String> map = new HashMap<>();

    if (args != null){
      String[] split = args.split(";");
      for (String nameValuePair : split) {
        String[] nameValue = nameValuePair.split("=");
        if (nameValue.length == 2){
          map.put(nameValue[0].toLowerCase(), nameValue[1]);
        }
      }
    }
    File argFile = new File("ebean-agent.properties");
    if (argFile.exists()) {
      Properties prop = new Properties();
      try (InputStream is = new FileInputStream(argFile)) {
        prop.load(is);
      } catch(IOException e) {
        throw new RuntimeException("error loading file " + argFile);
      }
      for ( Entry<Object, Object> entry : prop.entrySet()) {
        map.put(entry.getKey().toString().toLowerCase(), (String)entry.getValue());
      }
    }

    return map;
  }
}
