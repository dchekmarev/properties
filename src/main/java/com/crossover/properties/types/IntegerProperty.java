package com.crossover.properties.types;

public class IntegerProperty extends PropertyConfig {
  public IntegerProperty(String name, boolean required) {
    super(name, Integer.class.getName(), required);
  }

  @Override
  public Object parse(String strValue) {
    return Integer.valueOf(strValue);
  }
}
