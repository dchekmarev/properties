package com.crossover.properties;

import com.crossover.properties.readers.JsonPropertyReader;
import com.crossover.properties.readers.PropertyReader;
import com.crossover.properties.readers.SimplePropertyReader;
import com.crossover.properties.types.PropertyConfig;
import com.crossover.properties.types.TextProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap;
import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Properties {

  private final List<PropertyConfig> propertiesConfig;
  private final Map<String, PropertyConfig> indexedProperties;
  private final Map<String, String> rawProperties;
  private final Map<String, Object> properties;

  public Properties(List<PropertyConfig> propertiesConfig, String... urls) {
    this.propertiesConfig = propertiesConfig;
    this.indexedProperties = propertiesConfig.stream()
        .collect(Collectors.toMap(propertyConfig -> propertyConfig.name, propertyConfig -> propertyConfig));
    this.rawProperties = loadRawProperties(urls);
//    checkForUnknownProperties();
    checkNotSetProperties();
    this.properties = processRawProperties();
  }

  private Map<String, String> loadRawProperties(String[] urls) {
    Map<String, String> properties = new HashMap<>();

    // read/override properties as text
    asList(urls).stream()
        .map(uri -> getReaderBySuffix(uri).withUri((getStreamByUri(uri))))
        .map(PropertyReader::propertiesIterable)
        .map(ImmutableList::copyOf)
        .flatMap(Collection::stream)
        .forEach(entry -> properties.put(entry.getKey().toLowerCase(), entry.getValue()));

    return properties;
  }

  private void checkForUnknownProperties() {
    rawProperties.entrySet().stream()
        .filter(entry -> !indexedProperties.containsKey(entry.getKey()))
        .forEach(entry -> warnUnknownProperty(entry.getKey(), entry.getValue()));
  }

  private void checkNotSetProperties() {
    List<PropertyConfig> notSetProperties = propertiesConfig.stream()
        .filter(propertyConfig -> propertyConfig.required)
        .filter(propertyConfig -> !rawProperties.containsKey(propertyConfig.name))
        .collect(Collectors.toList());

    if (!notSetProperties.isEmpty()) {
      for (PropertyConfig propertyConfig : notSetProperties) {
        System.err.println(propertyConfig.name + " is not set");
      }
      throw new IllegalStateException("required properties not set");
    }
  }

  private Map<String, Object> processRawProperties() {
    return rawProperties.entrySet().stream()
        .map(entry -> new AbstractMap.SimpleImmutableEntry<>(
            entry.getKey(),
            indexedProperties.getOrDefault(entry.getKey(), new TextProperty(entry.getKey(), false)).parse(entry.getValue())
        ))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  public Object get(String name) {
    return properties.get(name.toLowerCase());
  }

  public String getType(String name) {
    return properties.get(name.toLowerCase()).getClass().getName();
  }

  public String getRawValue(String name) {
    return rawProperties.get(name.toLowerCase());
  }

  private static void warnUnknownProperty(String key, String value) {
    System.err.println(String.format("WARN unknown property \"%s\" with value \"%s\"", key, value));
  }

  @VisibleForTesting
  static PropertyReader getReaderBySuffix(String uri) {
    Preconditions.checkNotNull(uri);
    if (uri.toLowerCase().endsWith(".json")) {
      return new JsonPropertyReader();
    } else if (uri.toLowerCase().endsWith(".properties")) {
      return new SimplePropertyReader();
    }
    throw new IllegalStateException("can't find out the properties file type for " + uri);
  }

  @VisibleForTesting
  static InputStream getStreamByUri(String uri) {
    try {
      if (uri.startsWith("classpath:")) {
        return Properties.class.getResourceAsStream(uri.replace("classpath:", ""));
      } else {
        return urlUnchecked(uri).openStream();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @VisibleForTesting
  static URL urlUnchecked(String uri) {
    try {
      return new URL(uri);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
