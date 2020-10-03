package utils2;

import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtils {
  // Response response;

  //  public utils.DataProvider(Response response) {
  //    this.response=response;
  //
  //  }

  // use this if u want use your own points
  //  public Map<String, String> setUpWayPoints() {
  //    Map<String, String> wayPoint = new HashMap<>();
  //    String point0 = "52.529060,13.396926", point1 = "52.529478,13.407569"; //start and stop
  // points
  //    wayPoint.put("waypoint0","geo!stopOver!"+point0);
  //    wayPoint.put("waypoint1","geo!stopOver!"+point1);
  //    return wayPoint;
  //  }
  // use this for random points

  // merge attributes and geoPoints

  // with given json object return posLat,posLong,Elev
  public static LocationPoint getLocationPoint(Response response) {

    List<Double> posLat = new ArrayList<>();
    List<Double> posLong = new ArrayList<>();
    List<Double> elevation = new ArrayList<>();
    List<String> jsonResponse = response.jsonPath().getList("response.route[0].shape");
    for (int i = 0; i < jsonResponse.size(); i++) {
      double dLat;
      double dLong;
      double dElevation;
      String strToSplit;
      strToSplit = jsonResponse.get(i);
      String separated[] = strToSplit.split(",");
      dLat = Double.parseDouble(separated[0]);
      dLong = Double.parseDouble(separated[1]);
      dElevation = Double.parseDouble((separated[2]));
      posLat.add(dLat);
      posLong.add(dLong);
      elevation.add((dElevation));
    }
    List<Double> distanceCountry;
    distanceCountry = calculateCountryDistance(response);
    LocationPoint locationPoint = new LocationPoint(posLat, posLong, elevation, distanceCountry);
    return locationPoint;

    // return calculateDistance(posLat, posLong, elevation); // data to calculate distance

  }

  public static List<Double> calculateCountryDistance(Response response) {
    int i;
    double distance;
    String country;
    Map<String, Double> flagDistance = new HashMap<>();
    List<Double> distanceCountry = new ArrayList<>();
    i = response.jsonPath().getList("response.route[0].summaryByCountry").size();
    // System.out.println(i);
    for (int j = 0; j < i; j++) {
      distance =
          response
              .jsonPath()
              .getDouble("response.route[0].summaryByCountry[" + (j - 1) + "].distance");
      //  System.out.println(distance);
      distanceCountry.add(distance);
      country =
          response
              .jsonPath()
              .getString("response.route[0].summaryByCountry[" + (j - 1) + "].country");
      //  System.out.println(country);
      flagDistance.put(country, distance);
    }
    // calculateCountryDistance(distanceCountry);
    return distanceCountry;
    // return flagDistance;
  }
}
