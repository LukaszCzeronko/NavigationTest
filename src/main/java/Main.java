import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.*;

public class Main extends Client {
  public static void main(String[] args) {

    DataProvider dataProvider = new DataProvider();

    // Map<String,String> requestData=new HashMap(dataProvider.mergeRequestData());//create request
    // form
    Map<String, String> requestData = new HashMap<>();
    Client client = new Client();
    client.setUpConstants();
    Utilities utilities= new Utilities();
    // Response response=client.sendRequest(Method.GET,requestData);//send request

    // dataProvider.jsonResponseData(response);//do function inside->show calculated results

    ReadDataBase readDataBase = new ReadDataBase(); // read data base from file
    // System.out.println(readDataBase.read()); all filles

    double dist;
int quantityOfPoints=2; // set how many random place we want. Must be product of 2
    List<String> points = readDataBase.read2(quantityOfPoints);
    for (int i = 0; i < quantityOfPoints; i = i + 2) {
      client.setUpConstants(); // set up default constants
      requestData = new HashMap(dataProvider.setUpWayPoints(points.get(i), points.get(i + 1))); // set up pair of points
      Response response = client.sendRequest(Method.GET, requestData); // send request with given query parameters
        utilities.countryDistance(response);


     dist= dataProvider.jsonResponseData(response); // with given response calculate proper ponts and distance

    }
  }
}
