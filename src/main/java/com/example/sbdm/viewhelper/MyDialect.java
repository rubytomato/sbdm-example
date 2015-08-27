package com.example.sbdm.viewhelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;

public class MyDialect extends AbstractDialect implements IExpressionEnhancingDialect {
  private static final Map<String, Object> EXPRESSION_OBJECTS;

  static {
    Map<String, Object> objects = new HashMap<>();
    objects.put("vhs", new StringHelper());
    objects.put("vhd", new DateHelper());
    EXPRESSION_OBJECTS = Collections.unmodifiableMap(objects);
  }

  @Override
  public String getPrefix() {
    return null;
  }

  @Override
  public Map<String, Object> getAdditionalExpressionObjects(
      IProcessingContext arg0) {
    return EXPRESSION_OBJECTS;
  }

}