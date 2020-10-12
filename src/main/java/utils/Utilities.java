package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Utilities {
  // split and reverse string
  public static String splitAndRevertString(String strToReverse) {
    String str1, str2;
    str1 = strToReverse;
    String separated[] = str1.split(",");
    str2 = separated[1] + "," + separated[0];
    return str2;
  }

  public static void validateJsonAgainstSchema(String json, String schema) {
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
