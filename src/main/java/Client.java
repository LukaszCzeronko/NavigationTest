import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static utils.Utilities.splitAndRevertString;

public class Client {
  private Map<String, String> baseQueryParameters = new HashMap<String, String>();
  private String baseURI;
  private String basePath;

  public Client() {
    RestAssured.baseURI = "https://route.ls.hereapi.com/routing";
    RestAssured.basePath = "/7.2/calculateroute.json";
    setUpBaseQueryParameters();
  }

  public Client(String baseURI, String basePath) {
    this.baseURI = baseURI;
    this.basePath = basePath;
    RestAssured.baseURI = this.baseURI;
    RestAssured.basePath = this.basePath;
    setUpBaseQueryParameters();
  }
  // TODO string ...
  public void setUpWayPoints(String point0, String point1) {
    point0 = point0.replaceAll("\\[", "").replaceAll("\\]", "");
    point1 = point1.replaceAll("\\[", "").replaceAll("\\]", "");
    point0 = splitAndRevertString(point0);
    point1 = splitAndRevertString(point1);
    this.baseQueryParameters.put("waypoint0", "geo!stopOver!" + point0);
    this.baseQueryParameters.put("waypoint1", "geo!stopOver!" + point1);
  }

  public void setUpBaseQueryParameters() {
    this.baseQueryParameters.put("apiKey", "yCnlRpa6JipucjrFo3woY4fFYPN51gjjHhYd7cPVDmQ");
    this.baseQueryParameters.put("mode", "fastest;car;traffic:disabled");
    this.baseQueryParameters.put("routeattributes", "sh,summaryByCountry");
    this.baseQueryParameters.put("returnelevation", "true");
  }

  // send request with given queryParameters
  public Response sendRequest() {
    RequestSpecification newRequestSpecification = RestAssured.given();
    newRequestSpecification.log().parameters();
    newRequestSpecification.queryParams(this.baseQueryParameters);
    newRequestSpecification.log().parameters();
    ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
    PrintStream requestPs = new PrintStream(requestOutputStream, true);
    PrintStream responsePs = new PrintStream(responseOutputStream, true);
    RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter(requestPs);
    ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter(responsePs);
    newRequestSpecification.filters(requestLoggingFilter, responseLoggingFilter);
    Response response = newRequestSpecification.request(Method.GET);
    String requestDetails = new String(requestOutputStream.toByteArray());
    String responseDetails = new String(responseOutputStream.toByteArray());
    System.out.println(requestDetails);
    // System.out.println(responseDetails);
    return response;
  }
}
