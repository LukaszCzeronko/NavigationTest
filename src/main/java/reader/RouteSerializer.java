package reader;

import lombok.extern.slf4j.Slf4j;
import model.PostRequest;
import model.ProviderList;
import model.Route;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

@Slf4j
public class RouteSerializer {
  private ObjectMapper objectMapper = new ObjectMapper();

  public String serialize(List<Route> route) {
    String jsonString = "";
    try {
      jsonString = objectMapper.writeValueAsString(route);
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

  public ProviderList deserializeRequest(String json) {
    ObjectMapper mapper = new ObjectMapper();
    ProviderList list = null;
    try {
      List<PostRequest> providerList =
          mapper.readValue(json, new TypeReference<List<PostRequest>>() {});
      for (PostRequest provider : providerList) {
        System.out.println(provider);
      }
      list = new ProviderList();
      list.setProviderList(providerList);
      System.out.println(list);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return list;
  }
}
