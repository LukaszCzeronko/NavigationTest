package reader;

import lombok.extern.slf4j.Slf4j;
import model.Route;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Slf4j
public class RouteSerializer {
  private final ObjectMapper objectMapper = new ObjectMapper();

  public String serialize(List<Route> route) {
    String jsonString = "";
    try {
      jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(route);
    } catch (IOException e) {
      log.error("There was a error while serializing Route object: ", e.getMessage());
    }
    return jsonString;
  }

  public Route deserialize(String json) {
    Route route = null;
    try {
      route = objectMapper.readValue(json, Route.class);
    } catch (IOException e) {
      log.error("There was a error while deserializing Route object: ", e.getMessage());
    }
    return route;
  }
}
