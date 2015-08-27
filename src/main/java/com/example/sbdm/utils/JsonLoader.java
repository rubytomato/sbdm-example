package com.example.sbdm.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.JavaType;
import org.springframework.util.ResourceUtils;

public class JsonLoader {

  private static final String PATTERN = "yyyy-MM-dd";
  //yyyy-MM-dd HH:mm:ss E

  public static <T> T single(String path, Class<T> clazz) {

    T obj = null;

    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN, Locale.JAPAN);

      File file = ResourceUtils.getFile(path);

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.setDateFormat(dateFormat);

      obj = objectMapper.readValue(file, clazz);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return obj;

  }

  public static <T> List<T> multi(String path, Class<T> clazz) {

    ArrayList<T> list = null;

    try {
      File file = ResourceUtils.getFile(path);

      ObjectMapper objectMapper = new ObjectMapper();
      JavaType listType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);

      list = objectMapper.readValue(file, listType);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return list;

  }

  public static String toJson(Object value) {

    String json = null;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter;
    try {
      objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
      json = objectWriter.writeValueAsString(value);
      //json = objectMapper.writeValueAsString(value);
    } catch (JsonGenerationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return json;
  }

}
