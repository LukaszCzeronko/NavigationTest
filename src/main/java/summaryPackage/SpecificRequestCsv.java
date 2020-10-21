package summaryPackage;

import lombok.extern.slf4j.Slf4j;
import model.Location;
import model.RequestConfig;
import model.RequestConfigList;
import reader.ConfigSerializer;
import reader.DataReader;
import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SpecificRequestCsv {
  public void createSpecificCsv(List<List<Location>> routes) {
    List<String> id = new ArrayList<>();
    DataReader dataReader = new DataReader();
    String config;
    config = dataReader.readRequest("configuration.json");
    RequestConfigList requestConfigList = ConfigSerializer.deserializeRequest(config);
    int sumOfRoutes = 0;
    List<List<String>> listOfRequests = new ArrayList<>();
    List<String> requestsForSingleRoute;
    int numberOfRoutes = routes.size();
    List<Integer> routeDistribution =
        Utilities.calculateRouteDistribution(requestConfigList, numberOfRoutes);
    for (int i = 0; i < requestConfigList.getConfigList().size(); i++) {
      for (int j = 0; j < routeDistribution.get(i); j++) {
        id.add(Utilities.generateId());
        requestConfigList.getConfigList().get(i).setId(id.get(id.size() - 1));
        requestsForSingleRoute = new ArrayList<>();
        int sizeOfRoute = routes.get(j).size();
        for (int l = 0; l < sizeOfRoute; l++) {
          RequestConfig requestsConfig = new RequestConfig();
          requestsConfig.setId(id.get(id.size() - 1));
          requestsConfig.setLat(
              Utilities.transformDegree(routes.get(sumOfRoutes).get(l).getLatitude()));
          requestsConfig.setLon(
              Utilities.transformDegree(routes.get(sumOfRoutes).get(l).getLongitude()));
          RequestConfig configModel = requestConfigList.getConfigList().get(i);
          requestsConfig.setApp(configModel.getApp());
          requestsConfig.setDf(configModel.getDf());
          requestsConfig.setGd(configModel.getGd());
          requestsConfig.setTp(configModel.getTp());
          requestsConfig.setVer(configModel.getVer());
          requestsForSingleRoute.add(requestsConfig.toString());
        }
        sumOfRoutes = sumOfRoutes + 1;
        listOfRequests.add(requestsForSingleRoute);
      }
    }
    String csvString = Utilities.formatString(listOfRequests, id);
    Utilities.writeFile(csvString, "csvRouteRequest.csv");
  }
}
