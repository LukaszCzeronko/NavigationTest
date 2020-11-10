package Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.Location;

import java.util.ArrayList;
import java.util.List;

public class SoftAssert {

  public static void equals(List<Location> actual, List<Location> expected, String message) {
    if (actual.size() != expected.size()) {
      System.err.println("Actual size :"+actual.size()+" is different than expected size: "+expected.size());
      failure(message);
    }
    List<Result> results = new ArrayList<>();
    for(int currentIndex=0;currentIndex<actual.size()-1;currentIndex++){
      Result result= new Result(actual.get(currentIndex),expected.get(currentIndex),false);
      if(actual.get(currentIndex).getLatitude()==expected.get(currentIndex).getLatitude()){
        if(actual.get(currentIndex).getLongitude()==expected.get(currentIndex).getLongitude()){
          result.setResult(true);
        }
      }
      results.add(result);
    }
    boolean isFailed=false;
    for (Result result : results) {
      if(!result.isResult()){
        System.out.println("Actual :"+result.getActual()+" is different than expected: "+result.getExpected());
        isFailed=true;
      }
    }
    if(isFailed){
      failure(message);
    }
  }

  public static void equals(List<Location> actual, List<Location> expected) {
    equals(actual, expected, null);
  }

  private static void failure(String message) {
    if (message == null) {
      throw new AssertionError();
    } else throw new AssertionError(message);
  }
@AllArgsConstructor
  @Data
  static class Result {
    Location actual;
    Location expected;
    boolean result;
  }
}
