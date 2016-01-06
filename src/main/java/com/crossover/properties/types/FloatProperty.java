package com.crossover.properties.types;

public class FloatProperty extends PropertyConfig {
  public FloatProperty(String name, boolean required) {
    super(name, Float.class.getName(), required);
  }

  @Override
  public Object parse(String strValue) {
    return Float.valueOf(strValue);
  }
}
