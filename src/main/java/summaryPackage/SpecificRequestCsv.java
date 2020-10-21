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
    int numberOfProcessedRoutes = 0;
    List<List<String>> listOfRequests = new ArrayList<>();
    List<String> requestsForSingleRoute;
    int numberOfRoutes = routes.size();
    List<Integer> routeDistribution = Utilities.calculateRouteDistribution(requestConfigList, numberOfRoutes);
    int numberOfConfigs = routeDistribution.size();

    System.out.println(numberOfConfigs);
    System.out.println(routeDistribution.toString());

    for (int configNumber = 0; configNumber < numberOfConfigs; configNumber++) {
      int maxNumberOfRoutesForConfig = routeDistribution.get(configNumber);
      for (int routeIndexInConfig = 0; routeIndexInConfig < maxNumberOfRoutesForConfig; routeIndexInConfig++) {
        String idForRoute = Utilities.generateId();
        id.add(idForRoute);

//        requestConfigList.getConfigList().get(configNumber).setId(idForRoute);
        requestsForSingleRoute = new ArrayList<>();
        int sizeOfRoute = routes.get(numberOfProcessedRoutes).size();
        for (int locationIndex = 0; locationIndex < sizeOfRoute; locationIndex++) {
          RequestConfig requestConfig = new RequestConfig();
          requestConfig.setId(idForRoute);

          System.out.println(routeIndexInConfig+ " "+locationIndex +" " + numberOfProcessedRoutes +" "+ numberOfRoutes );

          requestConfig.setLat(
              Utilities.transformDegree(routes.get(numberOfProcessedRoutes).get(locationIndex).getLatitude()));
          requestConfig.setLon(
              Utilities.transformDegree(routes.get(numberOfProcessedRoutes).get(locationIndex).getLongitude()));
          RequestConfig configModel = requestConfigList.getConfigList().get(configNumber);
          requestConfig.setApp(configModel.getApp());
          requestConfig.setDf(configModel.getDf());
          requestConfig.setGd(configModel.getGd());
          requestConfig.setTp(configModel.getTp());
          requestConfig.setVer(configModel.getVer());
          requestsForSingleRoute.add(requestConfig.toString());
        }
        numberOfProcessedRoutes = numberOfProcessedRoutes + 1;
        listOfRequests.add(requestsForSingleRoute);
      }
    }
    String csvString = Utilities.formatString(listOfRequests, id);
    Utilities.writeFile(csvString, "csvRouteRequest.csv");
  }
}
