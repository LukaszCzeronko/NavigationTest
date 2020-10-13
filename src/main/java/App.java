import calculation.CalculateCoordinates;
import calculation.CalculateP2PDistance;
import http.Client;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.LocationPoint;
import model.Route;
import picocli.CommandLine;
import reader.DataReader;
import reader.RouteSerializer;
import utils.ResponseUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static utils.ResponseUtils.validateJsonAgainstSchema;

@Slf4j
public class App implements Callable<Integer> {
  private static final int QUANTITY_OF_POINTS =
      2; // set how many random place we want. Must be product of 2

  @CommandLine.Parameters(index = "0", description = "input-file")
  private int number;

  @CommandLine.Parameters(index = "1", description = "number-of-routes")
  private int number2;

  @CommandLine.Parameters(index = "2", description = "max-route-length")
  private int number3;

  @CommandLine.Option(names = "-unit", description = "unit system: km or mile")
  String units = "km";

  @CommandLine.Option(names = "-speed", description = "speed of a car")
  double speed = 90.0;

  @CommandLine.Option(names = "-itv", description = "interval rate")
  int interval = 180;

  public static void main(String... args) {
    int exitCode = new CommandLine(new App()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public Integer call() throws Exception {
    LocationPoint locationPoint;
    Client client = new Client();
    DataReader dataReader = new DataReader(); // read data base from file
    RouteSerializer routeSerializer = new RouteSerializer();
    List<String> points = dataReader.readFormattedJsonFile(QUANTITY_OF_POINTS);
    List<Route> routes = new ArrayList<>();
    int id = 0;
    for (int i = 0; i < QUANTITY_OF_POINTS; i = i + 2) {
      id++;
      Route route = new Route();
      client.setUpWayPoints(points.get(i), points.get(i + 1)); // set up pair of points
      Response response = client.sendRequest(); // send request with given query parameters
      ResponseUtils.getLocationPoint(response);
      locationPoint = ResponseUtils.getLocationPoint(response);
      CalculateP2PDistance calculateP2PDistance = new CalculateP2PDistance();
      CalculateCoordinates calculateCoordinates = new CalculateCoordinates();
      route.setLocation(
          calculateCoordinates.positionFromCar(
              calculateP2PDistance.calculateDistance(locationPoint)));
      route.setId(id);
      route.setLength(312.2); // TODO replace example value with real route length
      route.setUnits("km"); // TODO replace with type of units passed from CLI argument/default
      routes.add(route);
    }
    String results = routeSerializer.serialize(routes);
    log.info(results);
    String schema = dataReader.readSchema("src\\main\\resources\\my-schema.json");
    validateJsonAgainstSchema(results, schema);
    dataReader.writeFile(results);
    return null;
  }
}
