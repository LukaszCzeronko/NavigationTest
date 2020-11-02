package http;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static utils.Utilities.splitAndRevertString;

@Slf4j
public class Client {
  private final Map<String, String> baseQueryParameters = new HashMap<String, String>();
  private String baseURI;
  private String basePath;
  private RequestSpecification newRequestSpecification;
  private Response response;
  private ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
  private ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();

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

  public void setUpWayPoints(String... point) {
    //    System.out.println(point.length);
    for (int i = 0; i < point.length; i++) {
      point[i] = point[i].replaceAll("\\[", "").replaceAll("\\]", "");
      point[i] = splitAndRevertString(point[i]);
      this.baseQueryParameters.put("waypoint" + i, "geo!stopOver!" + point[i]);
    }
  }

  public void setUpBaseQueryParameters() {
    this.baseQueryParameters.put("apiKey", "yCnlRpa6JipucjrFo3woY4fFYPN51gjjHhYd7cPVDmQ");
    this.baseQueryParameters.put("mode", "fastest;car;traffic:disabled");
    this.baseQueryParameters.put("routeattributes", "sh,summaryByCountry");
    this.baseQueryParameters.put("returnelevation", "true");
  }

  // send request with given queryParameters
  public Response sendRequest(boolean debug) {
    newRequestSpecification= RestAssured.given();
    newRequestSpecification.queryParams(this.baseQueryParameters);

    //      ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
      //      ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
      //      PrintStream requestPs = new PrintStream(requestOutputStream, true);
      //      PrintStream responsePs = new PrintStream(responseOutputStream, true);
      //      RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter(requestPs);
      //      ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter(responsePs);
      //      newRequestSpecification.filters(requestLoggingFilter, responseLoggingFilter);
      addFilters(debug);

      response = newRequestSpecification.request(Method.GET);
      logDetails(debug);
      //      String requestDetails = new String(requestOutputStream.toByteArray());
      //      String responseDetails = new String(responseOutputStream.toByteArray());
      //      log.info("request details: {}", requestDetails);
      //      log.info("response details: {}", responseDetails);
    return response;
  }

  private void addFilters(boolean debug) {
    if (debug) {
      PrintStream requestPs = new PrintStream(requestOutputStream, true);
      PrintStream responsePs = new PrintStream(responseOutputStream, true);
      RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter(requestPs);
      ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter(responsePs);
      newRequestSpecification.filters(requestLoggingFilter, responseLoggingFilter);
    }
  }

  private void logDetails(boolean debug) {

    log.info(
        "Request coordinates: {},{} Response status code: {}",
        this.baseQueryParameters.get("waypoint0"),
        this.baseQueryParameters.get("waypoint1"),
        response.getStatusLine());

    if (debug) {
      String requestDetails = new String(requestOutputStream.toByteArray());
      String responseDetails = new String(responseOutputStream.toByteArray());
      log.info("request details: {}", requestDetails);
      log.info("response details: {}", responseDetails);
    }
  }
}
