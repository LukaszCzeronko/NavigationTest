package summaryPackage;

import model.Location;
import model.PostRequest;
import reader.DataReader;
import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class defaultRequestCsv {
  public void createDefaultCSV(List<List<Location>> routes) {
    List<List<String>> routesRequest = new ArrayList<>();
    List<String> routeRequest = null;
    List<String> id = new ArrayList<>();
    for (int j = 0; j < routes.size(); j++) {
      routeRequest = new ArrayList<>();
      id.add(Utilities.generateId());
      for (int i = 0; i < routes.get(j).size(); i++) {
        PostRequest postRequest = new PostRequest();
        //  postRequest.setApp("232");
        //  postRequest.setDf(2);
        //  postRequest.setGd("Ads");
        postRequest.setId(id.get(j));
        postRequest.setLat(Utilities.transformDegree(routes.get(j).get(i).getLatitude()));
        postRequest.setLon(Utilities.transformDegree(routes.get(j).get(i).getLongitude()));
        // postRequest.setTp("asd");
        // postRequest.setVer("afhgz");
        routeRequest.add(postRequest.toString());
      }
      routesRequest.add(routeRequest);
      System.out.println(routesRequest.get(j));
    }
    DataReader reader = new DataReader();
    reader.writeCsv(routesRequest, id, "routes.csv");
  }
}
