package reader;

import lombok.extern.slf4j.Slf4j;
import model.RequestConfig;
import model.RequestConfigList;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;

@Slf4j
public class ConfigSerializer {

  public static RequestConfigList deserializeRequest(String json) {
    ObjectMapper mapper = new ObjectMapper();
    RequestConfigList config = null;
    try {
      List<RequestConfig> mappedValue =
          mapper.readValue(json, new TypeReference<List<RequestConfig>>() {});
      config = new RequestConfigList();
      config.setConfigList(mappedValue);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return config;
  }
}
