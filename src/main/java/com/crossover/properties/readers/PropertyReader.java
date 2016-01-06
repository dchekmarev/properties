package com.crossover.properties.readers;

import java.io.InputStream;
import java.util.Map;

abstract public class PropertyReader {
  protected InputStream inputStream;

  public PropertyReader withUri(InputStream inputStream) {
    this.inputStream = inputStream;
    return this;
  }

  abstract public Iterable<Map.Entry<String, String>> propertiesIterable();
}
