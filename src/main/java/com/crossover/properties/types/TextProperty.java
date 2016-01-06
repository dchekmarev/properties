package com.crossover.properties.types;

public class TextProperty extends PropertyConfig {
  public TextProperty(String name, boolean required) {
    super(name, String.class.getName(), required);
  }

  @Override
  public Object parse(String strValue) {
    return strValue;
  }
}
