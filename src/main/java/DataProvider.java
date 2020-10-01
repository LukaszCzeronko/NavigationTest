import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProvider extends GeoUtility {
  Map<String, String> baseQueryParameters = new HashMap<String, String>();

  protected Map<String, String> setUpAttributes() {
    baseQueryParameters.put("apiKey", "yCnlRpa6JipucjrFo3woY4fFYPN51gjjHhYd7cPVDmQ");
    baseQueryParameters.put("mode", "fastest;car;traffic:disabled");
    baseQueryParameters.put("routeattributes", "sh,summaryByCountry");
    baseQueryParameters.put("returnelevation", "true");
    return baseQueryParameters;
  }
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
  public Map<String, String> setUpWayPoints(String point0, String point1) {
    Map<String, String> wayPoint = new HashMap<>();
    point0 = point0.replaceAll("\\[", "").replaceAll("\\]", "");
    point1 = point1.replaceAll("\\[", "").replaceAll("\\]", "");
    point0 = reverseString(point0);
    point1 = reverseString(point1);
    wayPoint.put("waypoint0", "geo!stopOver!" + point0);
    wayPoint.put("waypoint1", "geo!stopOver!" + point1);
    mergeRequestData(wayPoint);
    return mergeRequestData(wayPoint);
  }
  // merge attributes and geoPoints
  protected Map<String, String> mergeRequestData(Map<String, String> wayPoint) {
    Map<String, String> mergedRequestData = new HashMap<>(wayPoint);

    setUpAttributes()
        .forEach(
            (key, value) ->
                mergedRequestData.merge(
                    key, value, (v1, v2) -> v1.equalsIgnoreCase(v2) ? v1 : v1 + "," + v2));
    return mergedRequestData;
  }
  // with given json object return posLat,posLong,Elev
  protected double jsonResponseData(Response response) {
    List<String> jsonResponse = response.jsonPath().getList("response.route[0].shape");
    List<Double> posLat = new ArrayList<>();
    List<Double> posLong = new ArrayList<>();
    List<Double> elevation = new ArrayList<>();

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
    return calculateDistance(posLat, posLong, elevation); // data to calculate distance

  }
}
