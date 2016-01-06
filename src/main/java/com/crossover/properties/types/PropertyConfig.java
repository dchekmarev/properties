package com.crossover.properties.types;

abstract public class PropertyConfig {
  public final String name;
  public final String type;
  public final boolean required;

  public PropertyConfig(String name, String type, boolean required) {
    this.name = name.toLowerCase();
    this.type = type;
    this.required = required;
  }

  abstract public Object parse(String strValue);
}
