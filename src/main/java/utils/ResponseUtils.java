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
import java.util.List;

@Slf4j
public class ResponseUtils {
  private static final String JSON_SHAPE_PATH = "response.route[0].shape";

  public static LocationPoint getLocationPoint(Response response) {
    List<Double> posLat = new ArrayList<>();
    List<Double> posLong = new ArrayList<>();
    List<Double> elevation = new ArrayList<>();
    List<String> jsonResponse = response.jsonPath().getList(JSON_SHAPE_PATH);
    for (int i = 0; i < jsonResponse.size(); i++) {
      double dLat;
      double dLong;
      double dElevation;
      String strToSplit;
      strToSplit = jsonResponse.get(i);
      String[] separated = strToSplit.split(",");
      dLat = Double.parseDouble(separated[0]);
      dLong = Double.parseDouble(separated[1]);
      dElevation = Double.parseDouble((separated[2]));
      posLat.add(dLat);
      posLong.add(dLong);
      elevation.add((dElevation));
    }
    LocationPoint locationPoint = new LocationPoint(posLat, posLong, elevation);
    return locationPoint;
  }

  public static void validateJsonAgainstSchema(String json, String schema) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode schemaNode = mapper.readTree(schema);
      JsonNode jsonToValidate = JsonLoader.fromString(json);
      ProcessingReport validate =
          JsonSchemaFactory.byDefault().getJsonSchema(schemaNode).validate(jsonToValidate);
      if (validate.isSuccess()) {
        return;
      } else {
        throw new RuntimeException("schema validation failed: " + validate.toString());
      }
    } catch (IOException | ProcessingException e) {
      log.error("Problem while while parsing JSON file or schema: " + e.getMessage());
    }
  }
}
