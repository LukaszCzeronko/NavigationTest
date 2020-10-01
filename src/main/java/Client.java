import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

public class Client extends DataProvider {
  @BeforeClass
  public void setUpConstants() {
    RestAssured.baseURI = "https://route.ls.hereapi.com/routing";
    RestAssured.basePath = "/7.2/calculateroute.json";
  }

  // send request with given queryParameters
  public Response sendRequest(Method methodType, Map<String, String>... queryParameters) {
    System.out.println("aaa" + RestAssured.basePath + "aa");
    RequestSpecification newRequestSpecification = RestAssured.given();
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
    System.out.println(requestDetails);
    // System.out.println(responseDetails);
    return response;
  }
}
