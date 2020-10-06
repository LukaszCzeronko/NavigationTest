package Calculation;

import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtils {
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
  }

  public static List<Double> calculateCountryDistance(Response response) {
    int i;
    double distance;
    String country;
    Map<String, Double> flagDistance = new HashMap<>();
    List<Double> distanceCountry = new ArrayList<>();
    i = response.jsonPath().getList("response.route[0].summaryByCountry").size();
    for (int j = 0; j < i; j++) {
      distance =
          response
              .jsonPath()
              .getDouble("response.route[0].summaryByCountry[" + (j - 1) + "].distance");
      distanceCountry.add(distance);
      country =
          response
              .jsonPath()
              .getString("response.route[0].summaryByCountry[" + (j - 1) + "].country");
      flagDistance.put(country, distance);
    }
    return distanceCountry;
  }
}
