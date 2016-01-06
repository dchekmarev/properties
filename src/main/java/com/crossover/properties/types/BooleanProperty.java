package com.crossover.properties.types;

public class BooleanProperty extends PropertyConfig {
  public BooleanProperty(String name, boolean required) {
    super(name, Boolean.class.getName(), required);
  }

  @Override
  public Object parse(String strValue) {
    return Boolean.parseBoolean(strValue);
  }
}
