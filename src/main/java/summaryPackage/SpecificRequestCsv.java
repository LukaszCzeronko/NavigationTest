package summaryPackage;

import lombok.extern.slf4j.Slf4j;
import model.Location;
import model.PostRequest;
import model.ProviderList;
import reader.DataReader;
import reader.RouteSerializer;
import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SpecificRequestCsv {
  public void createSpecificCsv(List<List<Location>> routes) {
    List<String> id = new ArrayList<>();
    DataReader dataReader = new DataReader();
    String json;
    json = dataReader.readRequest("routeRequest.json");
    RouteSerializer routeSerializer = new RouteSerializer();
    ProviderList providerList = routeSerializer.deserializeRequest(json);
    int sumOfRoutes = 0;
    List<List<String>> listOfRequests = new ArrayList<>();
    List<String> routeReq;
    for (int i = 0; i < providerList.getProviderList().size(); i++) {
      for (int j = 0;
          j
              < Utilities.calculatePercent(
                  routes.size(), providerList.getProviderList().get(i).getRatio());
          j++) {
        id.add(Utilities.generateId());
        providerList.getProviderList().get(i).setId(id.get(id.size() - 1));
        routeReq = new ArrayList<>();
        for (int l = 0; l < routes.get(j).size(); l++) {
          PostRequest postRequests = new PostRequest();
          postRequests.setId(id.get(id.size() - 1));
          postRequests.setLat(
              Utilities.transformDegree(routes.get(sumOfRoutes).get(l).getLatitude()));
          postRequests.setLon(
              Utilities.transformDegree(routes.get(sumOfRoutes).get(l).getLongitude()));
          postRequests.setApp(providerList.getProviderList().get(i).getApp());
          postRequests.setDf(providerList.getProviderList().get(i).getDf());
          postRequests.setGd(providerList.getProviderList().get(i).getGd());
          postRequests.setTp(providerList.getProviderList().get(i).getTp());
          postRequests.setVer(providerList.getProviderList().get(i).getVer());
          routeReq.add(postRequests.toString());
        }
        sumOfRoutes = sumOfRoutes + 1;
        listOfRequests.add(routeReq);
      }
    }
    String csvString = Utilities.formatString(listOfRequests, id);
    Utilities.writeFile(csvString, "csvRouteConfig.csv");
  }
}
