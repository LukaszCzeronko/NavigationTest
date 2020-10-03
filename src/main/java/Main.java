import io.restassured.response.Response;
import utils2.ResponseUtils;
import utils.Utilities;

import java.util.*;

public class Main {
  private static final int QUANTITY_OF_POINTS = 2;

  public static void main(String[] args) {

    LocationPoint locationPoint;
    Client client = new Client();
    Utilities utilities = new Utilities();
    // Response response=client.sendRequest(Method.GET,requestData);//send request

    // dataProvider.jsonResponseData(response);//do function inside->show calculated results

    DataReader dataReader = new DataReader(); // read data base from file
    // System.out.println(readDataBase.read()); all filles
    List<Double> cDistance = new ArrayList<>();
    // set how many random place we want. Must be product of 2
    List<String> points = dataReader.readFormattedJsonFile(QUANTITY_OF_POINTS);
    for (int i = 0; i < QUANTITY_OF_POINTS; i = i + 2) {
      client.setUpWayPoints(points.get(i), points.get(i + 1)); // set up pair of points
      Response response = client.sendRequest(); // send request with given query parameters

      ResponseUtils.getLocationPoint(response);
      // utils.DataProvider dataProvider = new utils.DataProvider(response);
      // cDistance = dataProvider.countryDistance();
      locationPoint = ResponseUtils.getLocationPoint(response);

      CalculateP2PDistance calculateP2PDistance = new CalculateP2PDistance(locationPoint);
      Map<Double, Double> newCalculatedCoordinates =
          new CalculateCoordinates().positionFromCar(calculateP2PDistance.calculateDistance());

      // System.out.println(calculateCoordinates.positionFromCar());
      // utilities.countryDistance(response);
    }
  }
}
