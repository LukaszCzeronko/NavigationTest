import io.restassured.response.Response;
import org.json.simple.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utilities {
  // split and reverse string
  public String reverseString(String strToReverse) {
    String str1, str2;
    str1 = strToReverse;
    String separated[] = str1.split(",");
    str2 = separated[1] + "," + separated[0];
    return str2;
  }

  public Map<String,Double> countryDistance(Response response) {
    int i;
    double distance;
    String country;
    Map<String,Double> flagDistance= new HashMap<>();
    i = response.jsonPath().getList("response.route[0].summaryByCountry").size();
    System.out.println(i);
    for (int j = 0; j < i; j++) {
      distance =
          response
              .jsonPath()
              .getDouble("response.route[0].summaryByCountry[" + (j - 1) + "].distance");
      System.out.println(distance);
      country =
          response
              .jsonPath()
              .getString("response.route[0].summaryByCountry[" + (j - 1) + "].country");
      System.out.println(country);
      flagDistance.put(country,distance);
    }

    return flagDistance;
  }
  public double countryMultiplier(double distance){
    double multi=7.3;

    return multi;
  }
}
