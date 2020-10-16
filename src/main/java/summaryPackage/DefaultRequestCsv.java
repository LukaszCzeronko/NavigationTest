package summaryPackage;

import model.Location;
import model.PostRequest;
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
        PostRequest postRequest = new PostRequest();
        postRequest.setId(id.get(j));
        postRequest.setLat(Utilities.transformDegree(routes.get(j).get(i).getLatitude()));
        postRequest.setLon(Utilities.transformDegree(routes.get(j).get(i).getLongitude()));
        routeRequest.add(postRequest.toString());
      }
      routesRequest.add(routeRequest);
      System.out.println(routesRequest.get(j));
    }
    String csvString = Utilities.formatString(routesRequest, id);
    Utilities.writeFile(csvString, "csvRoute.csv");
  }
}
