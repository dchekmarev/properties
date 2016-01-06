package com.crossover.properties.readers;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SimplePropertyReader extends PropertyReader {
  @Override
  public Iterable<Map.Entry<String, String>> propertiesIterable() {
    Properties prop = new Properties();
    try {
      prop.load(inputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return (Set) prop.entrySet();
  }
}
