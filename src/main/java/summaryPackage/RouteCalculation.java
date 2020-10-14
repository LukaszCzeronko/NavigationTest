package summaryPackage;

import calculation.CalculateCoordinates;
import calculation.CalculateP2PDistance;
import cli.CliProperties;
import http.Client;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.LocationPoint;
import model.Route;
import reader.DataReader;
import reader.RouteSerializer;
import utils.ResponseUtils;
import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import static utils.ResponseUtils.validateJsonAgainstSchema;

@Slf4j
public class RouteCalculation {

  public List<Route> newPoints(CliProperties cliProperties) {
    LocationPoint locationPoint;
    LocationPoint locationPoint1;
    Client client = new Client();
    DataReader dataReader = new DataReader(); // read data base from file
    RouteSerializer routeSerializer = new RouteSerializer();
    List<String> points =
        dataReader.readFormattedJsonFile(
            (cliProperties.getNumberOfRoutes()) * 2, cliProperties.getInputFile());
    List<Route> routes = new ArrayList<>();
    double step = Utilities.calculateStep(cliProperties.getSpeed(), cliProperties.getInterval());
    int id = 0;
    for (int i = 0; i < (cliProperties.getNumberOfRoutes()) * 2; i = i + 2) {
      id++;
      Route route = new Route();
      client.setUpWayPoints(points.get(i), points.get(i + 1)); // set up pair of points
      Response response = client.sendRequest(); // send request with given query parameters
      ResponseUtils.getLocationPoint(response);
      locationPoint = ResponseUtils.getLocationPoint(response);
      CalculateP2PDistance calculateP2PDistance = new CalculateP2PDistance();
      CalculateCoordinates calculateCoordinates = new CalculateCoordinates();
      locationPoint1 =
          calculateP2PDistance.calculateDistance(
              locationPoint, cliProperties.getMaxRouteLength(), cliProperties.getUnits(), step);
      route.setLocation(calculateCoordinates.positionFromCar(locationPoint1));
      route.setId(id);
      route.setLength(locationPoint1.getOverallDistance());
      route.setUnits(cliProperties.getUnits().name());
      routes.add(route);
    }
    String results = routeSerializer.serialize(routes);
    log.info(results);
    String schema = dataReader.readSchema("src\\main\\resources\\my-schema.json");
    validateJsonAgainstSchema(results, schema);
    dataReader.writeFile(results);
    return routes;
  }
}
