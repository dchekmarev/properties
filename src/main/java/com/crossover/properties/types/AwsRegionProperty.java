package com.crossover.properties.types;

import com.amazonaws.regions.Regions;

public class AwsRegionProperty extends PropertyConfig {
  public AwsRegionProperty(String name, boolean required) {
    super(name, Regions.class.getName(), required);
  }

  @Override
  public Object parse(String strValue) {
    for (Regions region : Regions.values()) {
      if (region.getName().equals(strValue)) {
        return region;
      }
    }
    throw new IllegalArgumentException(String.format("unknown aws region %s", strValue));
  }
}
