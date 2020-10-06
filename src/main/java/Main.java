import Calculation.CalculateCoordinates;
import Calculation.CalculateP2PDistance;
import Calculation.LocationPoint;
import http.Client;
import io.restassured.response.Response;
import Calculation.ResponseUtils;
import readData.DataReader;
import java.util.*;

public class Main {
  private static final int QUANTITY_OF_POINTS = 2;  // set how many random place we want. Must be product of 2

  public static void main(String[] args) {
    LocationPoint locationPoint;
    Client client = new Client();
    DataReader dataReader = new DataReader(); // read data base from file
    List<String> points = dataReader.readFormattedJsonFile(QUANTITY_OF_POINTS);
    for (int i = 0; i < QUANTITY_OF_POINTS; i = i + 2) {
      client.setUpWayPoints(points.get(i), points.get(i + 1)); // set up pair of points
      Response response = client.sendRequest(); // send request with given query parameters
      ResponseUtils.getLocationPoint(response);
      locationPoint = ResponseUtils.getLocationPoint(response);
      CalculateP2PDistance calculateP2PDistance = new CalculateP2PDistance();
      Map<Double, Double> newCalculatedCoordinates =
          new CalculateCoordinates().positionFromCar(calculateP2PDistance.calculateDistance(locationPoint));;
    }
  }
}
