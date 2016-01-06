package com.crossover.properties.readers;

import com.github.wnameless.json.flattener.JsonFlattener;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JsonPropertyReader extends PropertyReader {
  @Override
  public Iterable<Map.Entry<String, String>> propertiesIterable() {
    String jsonString = new Scanner(inputStream).useDelimiter("\\Z").next();
    Map<String, Object> stringObjectMap = JsonFlattener.flattenAsMap(jsonString);
    return stringObjectMap.keySet().stream()
        .map(key -> new AbstractMap.SimpleImmutableEntry<>(clearKey(key), stringObjectMap.get(key).toString()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        .entrySet();
  }

  private String clearKey(String key) {
    return key.replace("[\\\"", "").replace("\\\"]", "");
  }
}
