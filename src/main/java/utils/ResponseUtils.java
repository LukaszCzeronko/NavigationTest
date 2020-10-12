package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.LocationPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ResponseUtils {
  public static LocationPoint getLocationPoint(Response response) {
    List<Double> posLat = new ArrayList<>();
    List<Double> posLong = new ArrayList<>();
    List<Double> elevation = new ArrayList<>();
    List<String> jsonResponse = response.jsonPath().getList("response.route[0].shape");
    for (int i = 0; i < jsonResponse.size(); i++) {
      double dLat;
      double dLong;
      double dElevation;
      String strToSplit;
      strToSplit = jsonResponse.get(i);
      String separated[] = strToSplit.split(",");
      dLat = Double.parseDouble(separated[0]);
      dLong = Double.parseDouble(separated[1]);
      dElevation = Double.parseDouble((separated[2]));
      posLat.add(dLat);
      posLong.add(dLong);
      elevation.add((dElevation));
    }
    List<Double> distanceCountry;
    distanceCountry = calculateCountryDistance(response);
    LocationPoint locationPoint = new LocationPoint(posLat, posLong, elevation, distanceCountry);
    return locationPoint;
  }

  public static List<Double> calculateCountryDistance(Response response) {
    int i;
    double distance;
    String country;
    Map<String, Double> flagDistance = new HashMap<>();
    List<Double> distanceCountry = new ArrayList<>();
    i = response.jsonPath().getList("response.route[0].summaryByCountry").size();
    for (int j = 0; j < i; j++) {
      distance =
          response
              .jsonPath()
              .getDouble("response.route[0].summaryByCountry[" + (j - 1) + "].distance");
      distanceCountry.add(distance);
      country =
          response
              .jsonPath()
              .getString("response.route[0].summaryByCountry[" + (j - 1) + "].country");
      flagDistance.put(country, distance);
    }
    return distanceCountry;
  }

  public static void validateJsonAgainstSchema(String json, String schema) {
    ObjectMapper objectMapper = new ObjectMapper();
    // this line will generate JSON schema from your class
    //        JsonNode schemaNode;
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode schemaNode = mapper.readTree(schema);
      log.info(schemaNode.asText());
      JsonNode jsonToValidate = JsonLoader.fromString(json);
      ProcessingReport validate =
          JsonSchemaFactory.byDefault().getJsonSchema(schemaNode).validate(jsonToValidate);
      System.out.println("Valid? " + validate.isSuccess());
      if (validate.isSuccess()) {
        return;
      } else {
        throw new RuntimeException("schema validation failed: " + validate.toString());
      }
    } catch (IOException | ProcessingException e) {
      System.out.println("Problem while while parsing JSON file or schema: " + e.getMessage());
    }
  }
}
