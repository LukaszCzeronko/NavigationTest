package validation;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.gavaghan.geodesy.*;
import org.testng.annotations.BeforeClass;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static io.restassured.RestAssured.*;

public class NavigationTestBase {

  @BeforeClass
  public void setUpConstants() {
    RestAssured.baseURI = "https://route.ls.hereapi.com/routing";
    RestAssured.basePath = "/7.2/calculateroute.json";
  }

  protected Response sendRequest(Method methodType, Map<String, String>... queryParameters) {
    RequestSpecification newRequestSpecification = given();
    newRequestSpecification.log().parameters();
    for (Map<String, String> queryParameter : queryParameters) {
      newRequestSpecification.queryParams(queryParameter);
    }
    newRequestSpecification.log().parameters();
    ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
    PrintStream requestPs = new PrintStream(requestOutputStream, true);
    PrintStream responsePs = new PrintStream(responseOutputStream, true);
    RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter(requestPs);
    ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter(responsePs);
    newRequestSpecification.filters(requestLoggingFilter, responseLoggingFilter);
    Response response = newRequestSpecification.request(methodType);
    String requestDetails = new String(requestOutputStream.toByteArray());
    String responseDetails = new String(responseOutputStream.toByteArray());
    return response;
  }

  protected Double calculateDistance(
      List<Double> posLat, List<Double> posLong, List<Double> elevation) {
    List<Double> pointDistance = new ArrayList<>();
    List<Double> pointAzimuth = new ArrayList<>();
    double distance = 0;
    for (int i = 0; i < (posLat.size() - 1); i++) {

      GeodeticCalculator geoCalc = new GeodeticCalculator();
      Ellipsoid reference = Ellipsoid.WGS84;

      GlobalPosition firstPoint;
      firstPoint = new GlobalPosition(posLat.get(i), posLong.get(i), elevation.get(i));
      GlobalPosition secondPoint;
      secondPoint = new GlobalPosition(posLat.get(i + 1), posLong.get(i + 1), elevation.get(i + 1));

      GeodeticMeasurement geoMeasurement;
      double p2pKilometers;
      double elevChangeMeters;
      geoMeasurement = geoCalc.calculateGeodeticMeasurement(reference, firstPoint, secondPoint);
      p2pKilometers = geoMeasurement.getPointToPointDistance() / 1000.0;
      elevChangeMeters = geoMeasurement.getElevationChange();
      pointDistance.add(p2pKilometers);
      pointAzimuth.add(geoMeasurement.getAzimuth());
      distance = p2pKilometers + distance;
    }
    positionFromCar(posLat, posLong, pointDistance, pointAzimuth, 0.2);

    return distance;
  }

  protected Map<Double, Double> positionFromCar(
      List<Double> posLat,
      List<Double> posLong,
      List<Double> distanceFromEachPoint,
      List<Double> azimuthValue,
      double distanceFromPoints) {
    Map<Double, Double> positionAfterParameter = new HashMap<>();
    List<Double> positionAfterParameterLat = new ArrayList<>();
    List<Double> positionAfterParameterLong = new ArrayList<>();
    positionAfterParameter.put(posLat.get(0), posLong.get(0));
    positionAfterParameterLat.add(posLat.get(0));
    positionAfterParameterLong.add(posLong.get(0));
    Map<Double, Double> startingPoints = new HashMap<>();
    double sDistance = 0;
    double cDist = 0;
    double cAng;
    double lowSum;
    for (int i = 0; i < distanceFromEachPoint.size(); i++) {
      sDistance = sDistance + distanceFromEachPoint.get(i) - cDist;
      cDist = 0;
      if (sDistance >= distanceFromPoints) {
        lowSum = sDistance - distanceFromEachPoint.get(i);
        cDist = distanceFromPoints - lowSum;
        cAng = azimuthValue.get(i);
        GeodeticCalculator geoCalc = new GeodeticCalculator();
        Ellipsoid reference = Ellipsoid.WGS84;
        GlobalCoordinates startPoint;
        startPoint = new GlobalCoordinates(posLat.get(i - 1), posLong.get(i - 1));
        double[] endBearing = new double[1];
        GlobalCoordinates dest =
            geoCalc.calculateEndingGlobalCoordinates(
                reference, startPoint, cAng, cDist, endBearing);
        sDistance = 0;

        posLat.set(i - 1, dest.getLatitude());
        posLong.set(i - 1, dest.getLongitude());
        positionAfterParameter.put(posLat.get(i), posLong.get(i));
        positionAfterParameterLat.add(posLat.get(i));
        positionAfterParameterLong.add((posLong.get(i)));
        --i;
      } else if ((i + 1) >= distanceFromEachPoint.size()) {

        GeodeticCalculator geoCalc = new GeodeticCalculator();
        Ellipsoid reference = Ellipsoid.WGS84;
        GlobalCoordinates startPoint;
        cAng = azimuthValue.get(i);
        startPoint =
            new GlobalCoordinates(
                positionAfterParameterLat.get(positionAfterParameterLat.size() - 1),
                positionAfterParameterLong.get(positionAfterParameterLong.size() - 1));

        double[] endBearing = new double[1];
        GlobalCoordinates dest =
            geoCalc.calculateEndingGlobalCoordinates(
                reference, startPoint, cAng, sDistance, endBearing);

        posLat.set(i - 1, dest.getLatitude());
        posLong.set(i - 1, dest.getLongitude());
        positionAfterParameter.put(posLat.get(posLat.size() - 1), posLong.get(posLong.size() - 1));
        positionAfterParameterLat.add(posLat.get(posLat.size() - 1));
        positionAfterParameterLong.add(posLong.get(posLong.size() - 1));
      }
    }
    for (int i = 0; i < positionAfterParameterLat.size(); i++) {
      String coordinates =
          positionAfterParameterLat.get(i) + "," + positionAfterParameterLong.get(i) + ";";
      System.out.println(coordinates);
    }
    return positionAfterParameter;
  }
}
