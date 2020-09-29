package validation;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseTest extends NavigationTestBase {

  @DataProvider(name = "baseQueryParameters")
  public Object[][] queryParametersName() {
    Map<String, String> baseParametersName = new HashMap<>();
    baseParametersName.put("apiKey", "yCnlRpa6JipucjrFo3woY4fFYPN51gjjHhYd7cPVDmQ");
    baseParametersName.put("waypoint1", "geo!stopOver!49.209002,27.988172");
    baseParametersName.put("waypoint0", "geo!stopOver!46.254345,2.763563");
    baseParametersName.put("mode", "fastest;car;traffic:disabled");
    baseParametersName.put("routeattributes", "sh");
    baseParametersName.put("returnelevation", "true");

    return new Object[][] {
      {baseParametersName},
    };
  }

  @Test(dataProvider = "baseQueryParameters")
  public void requestTest(HashMap<String, String> queryParameters) {
    Response response = sendRequest(Method.GET, queryParameters);
    Assert.assertEquals(response.statusCode(), 200);

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
    calculateDistance(posLat, posLong, elevation);
  }
}
