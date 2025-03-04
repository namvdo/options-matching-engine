package dev.namvdo.planmatcher.dataaccess;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StorageUtils {

  private StorageUtils() {

  }


  public static final String MOCK_PLAN_DATA_PATH = "data/plans.json";
  public static final String MOCK_PLAN_OPTIONS_DATA_PATH = "data/options.json";

  public static JsonElement getDefaultPlans() {
    return getJsonArray(MOCK_PLAN_DATA_PATH);
  }

  public static JsonElement getDefaultPlanOptions() {
    return getJsonObject(MOCK_PLAN_OPTIONS_DATA_PATH);
  }



  public static JsonArray getJsonArray(String mockPlanDataPath) {
    try {
      ClassPathResource classPathResource = new ClassPathResource(mockPlanDataPath);
      String content = null;
      content = classPathResource.getContentAsString(StandardCharsets.UTF_8);
      return JsonParser.parseString(content).getAsJsonArray();
    } catch (IOException e) {
      return JsonParser.parseString("[{}]").getAsJsonArray();
    }
  }


  public static JsonObject getJsonObject(String mockPlanDataPath) {
    try {
      ClassPathResource classPathResource = new ClassPathResource(mockPlanDataPath);
      String content = null;
      content = classPathResource.getContentAsString(StandardCharsets.UTF_8);
      return JsonParser.parseString(content).getAsJsonObject();
    } catch (IOException e) {
      return JsonParser.parseString("{}").getAsJsonObject();
    }
  }

}
