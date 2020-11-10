package summary;

import calculation.CalculateCoordinatesInterpolationFinal;
import calculation.CalculateP2PDistance;
import cli.CliProperties;
import http.Client;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.Location;
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
  private static final String MY_SCHEMA = "src\\main\\resources\\my-schema.json";

  public List<Route> calculatePoints(CliProperties cliProperties) {
    LocationPoint locationPoint;
    LocationPoint locationPoint1;
    Client client = new Client();
    DataReader dataReader = new DataReader();
    RouteSerializer routeSerializer = new RouteSerializer();
    int amountOfRoutes = cliProperties.getNumberOfRoutes() * 2;
    List<String> points =
        dataReader.readFormattedJsonFile(amountOfRoutes, cliProperties.getInputFile());
    List<Route> routes = new ArrayList<>();
    double step = Utilities.calculateStep(cliProperties.getSpeed(), cliProperties.getInterval());
    int id = 0;
    for (int i = 0; i < amountOfRoutes; i = i + 2) {
      id++;
      Route route = new Route();
      client.setUpWayPoints(points.get(i), points.get(i + 1));
      Response response = client.sendRequest(cliProperties.isDebug());
      locationPoint = ResponseUtils.getLocationPoint(response);
      CalculateP2PDistance calculateP2PDistance = new CalculateP2PDistance();
      CalculateCoordinatesInterpolationFinal calculateCoordinatesInterpolationFinal =
          new CalculateCoordinatesInterpolationFinal();
      locationPoint1 =
          calculateP2PDistance.calculateDistance(
              locationPoint, cliProperties.getMaxRouteLength(), cliProperties.getUnits(), step);
      List<Location> location;
      location =
          calculateCoordinatesInterpolationFinal.calculatePointsOnRoute(
              locationPoint1, cliProperties.isDebug());
      route.setLocation(location);
      route.setId(id);
      route.setLength(locationPoint1.getOverallDistance());
      route.setUnits(cliProperties.getUnits().name());
      routes.add(route);
    }
    String results = routeSerializer.serialize(routes);
    String schema = dataReader.readSchema(MY_SCHEMA);
    validateJsonAgainstSchema(results, schema);
    Utilities.writeFile(results, cliProperties.getOutputFile());
    return routes;
  }
}
