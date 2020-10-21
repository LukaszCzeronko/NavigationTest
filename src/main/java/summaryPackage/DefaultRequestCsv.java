package summaryPackage;

import model.Location;
import model.RequestConfig;
import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class DefaultRequestCsv {
  public void createDefaultCSV(List<List<Location>> routes) {
    List<List<String>> routesRequest = new ArrayList<>();
    List<String> routeRequest = null;
    List<String> id = new ArrayList<>();
    for (int j = 0; j < routes.size(); j++) {
      routeRequest = new ArrayList<>();
      id.add(Utilities.generateId());
      for (int i = 0; i < routes.get(j).size(); i++) {
        RequestConfig requestConfig = new RequestConfig();
        requestConfig.setId(id.get(j));
        requestConfig.setLat(Utilities.transformDegree(routes.get(j).get(i).getLatitude()));
        requestConfig.setLon(Utilities.transformDegree(routes.get(j).get(i).getLongitude()));
        routeRequest.add(requestConfig.toString());
      }
      routesRequest.add(routeRequest);
    }
    String csvString = Utilities.formatString(routesRequest, id);
    Utilities.writeFile(csvString, "csvRouteRequest.csv");
  }
}
