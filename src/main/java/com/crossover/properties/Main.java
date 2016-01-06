package com.crossover.properties;

import com.crossover.properties.types.AwsRegionProperty;
import com.crossover.properties.types.BooleanProperty;
import com.crossover.properties.types.FloatProperty;
import com.crossover.properties.types.IntegerProperty;
import com.crossover.properties.types.PropertyConfig;
import com.crossover.properties.types.TextProperty;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  private static final List<PropertyConfig> PROPERTIES = ImmutableList.of(
      new TextProperty("JDBC_DRIVER", true), // com.mysql.jdbc.Driver
      new TextProperty("JDBC_URL", true), // jdbc:mysql://localhost/test
      new TextProperty("JDBC_USERNAME", true), // username123
      new TextProperty("JDBC_PASSWORD", true), // password123
      new BooleanProperty("hibernate.generate_statistics", true), // true
      new BooleanProperty("hibernate.show_sql", true), // true
      new BooleanProperty("jpa.showSql", true), // true
      new TextProperty("aws_access_key", true), // AKIAJSF6XRIJNJTTTL3Q
      new TextProperty("aws_secret_key", true), // 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
      new IntegerProperty("aws_account_id", true), // 12345678
      new AwsRegionProperty("aws_region_id", true), // us-east-1
      new TextProperty("auth.endpoint.uri", true), // https://authserver/v1/auth
      new IntegerProperty("job.timeout", true), // 3600
      new IntegerProperty("job.maxretry", true), // 4
      new TextProperty("sns.broadcast.topic_name", true), // broadcast
      new IntegerProperty("sns.broadcast.visibility_timeout", true), // 30
      new FloatProperty("score.factor", true) // 2.4
  );

  public static void main(String[] args) throws Exception {
    Properties properties = new Properties(PROPERTIES, args);

    List<String> keys = PROPERTIES.stream()
        .map(propertyConfig -> propertyConfig.name)
        .collect(Collectors.toList());
    keys.sort(String::compareToIgnoreCase);

    for (String key : keys) {
      System.out.println(key + ", " + properties.getType(key) + ", " + properties.getRawValue(key));
    }

  }
}
