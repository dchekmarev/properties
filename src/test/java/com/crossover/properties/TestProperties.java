package com.crossover.properties;

import com.crossover.properties.readers.JsonPropertyReader;
import com.crossover.properties.readers.SimplePropertyReader;
import com.crossover.properties.types.IntegerProperty;
import java.util.Collections;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class TestProperties {
  @Test(expected = RuntimeException.class)
  public void testUrlUnckeckedEmplty() {
    Properties.urlUnchecked("");
  }

  @Test(expected = RuntimeException.class)
  public void testUrlUnckeckedTrash() {
    Properties.urlUnchecked("qweqwe");
  }

  @Test(expected = RuntimeException.class)
  public void testUrlUnckeckedUnknownProto() {
    Properties.urlUnchecked("fail:qweqwe");
  }

  @Test
  public void testUrlUnckeckedPlainfile() {
    Properties.urlUnchecked("file:qweqwe");
  }

  @Test
  public void testUrlUnckeckedHTTP() {
    Properties.urlUnchecked("http://test.com");
  }

  @Test
  public void testUnknownClasspathResource() {
    assertNull(Properties.getStreamByUri("classpath:/SoMeThInGuNkNoWn"));
  }

  @Test
  public void testClasspathResource() {
    assertNotNull(Properties.getStreamByUri("classpath:/test.json"));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetReaderByNoSuffix() {
    Properties.getReaderBySuffix("nosuffix");
  }

  @Test(expected = IllegalStateException.class)
  public void testGetReaderByUnknownSuffix() {
    Properties.getReaderBySuffix("something.unknownsuffix");
  }

  @Test
  public void testGetReaderByJSONSuffix() {
    assertEquals(JsonPropertyReader.class, Properties.getReaderBySuffix("something.json").getClass());
    assertEquals(JsonPropertyReader.class, Properties.getReaderBySuffix("file:something.jSoN").getClass());
  }

  @Test
  public void testGetReaderByPropSuffix() {
    assertEquals(SimplePropertyReader.class, Properties.getReaderBySuffix("something.properties").getClass());
    assertEquals(SimplePropertyReader.class, Properties.getReaderBySuffix("file:something.PropErTiEs").getClass());
  }

  @Test(expected = IllegalStateException.class)
  public void testPropertiesNotSet() {
    new Properties(Collections.singletonList(new IntegerProperty("uNkNoWnPrOpErTyNaMe", true)));
  }

  @Test
  public void testPropertiesSimple() {
    new Properties(Collections.singletonList(new IntegerProperty("test", true)), "classpath:/test.json");
  }

  @Test
  public void testPropertiesOverride() {
    Properties properties = new Properties(Collections.singletonList(new IntegerProperty("test", true)), "classpath:/test.json");
    assertEquals(123, properties.get("test"));
    properties = new Properties(Collections.singletonList(new IntegerProperty("test", true)), "classpath:/test.json", "classpath:/test.properties");
    assertEquals(456, properties.get("test"));
  }
}
