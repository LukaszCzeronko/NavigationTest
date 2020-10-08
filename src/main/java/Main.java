import calculation.CalculateCoordinates;
import calculation.CalculateP2PDistance;
import http.Client;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import model.LocationPoint;
import model.Route;
import reader.DataReader;
import reader.RouteSerializer;
import utils.ResponseUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Main {
  private static final int QUANTITY_OF_POINTS = 2; // set how many random place we want. Must be product of 2

  public static void main(String[] args) {
    LocationPoint locationPoint;
    Client client = new Client();
    DataReader dataReader = new DataReader(); // read data base from file
    RouteSerializer routeSerializer = new RouteSerializer();
    List<String> points = dataReader.readFormattedJsonFile(QUANTITY_OF_POINTS);
    Route route = new Route();
    for (int i = 0; i < QUANTITY_OF_POINTS; i = i + 2) {
      client.setUpWayPoints(points.get(i), points.get(i + 1)); // set up pair of points
      Response response = client.sendRequest(); // send request with given query parameters
      ResponseUtils.getLocationPoint(response);
      locationPoint = ResponseUtils.getLocationPoint(response);
      CalculateP2PDistance calculateP2PDistance = new CalculateP2PDistance();
      CalculateCoordinates calculateCoordinates = new CalculateCoordinates();
      route.setLocation(
          calculateCoordinates.positionFromCar(
              calculateP2PDistance.calculateDistance(locationPoint)));
    }
    route.setId(1);//example of using serializer
    route.setLength(312.2);
    route.setUnits("km");
    List<String> write = new ArrayList<>();
    write.add(routeSerializer.serialize(route));
    dataReader.writeFile(write);
  }
}
