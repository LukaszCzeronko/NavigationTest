import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataReader {
  // read json type file and setup random localisation for given number of locations
  public List<String> readFormattedJsonFile(int numberOfLocation) {
    List<Integer> ex = new ArrayList<>();
    List<String> coordinates = new ArrayList<>();
    try (FileReader reader = new FileReader("src\\main\\resources\\localisation.json")) {
      JSONParser parser = new JSONParser();
      JSONArray obj = (JSONArray) parser.parse(reader);
      int size = obj.size();
      for (int j = 0; j < numberOfLocation; j++) {
        Random rand = new Random();
        int int_random = rand.nextInt(size);
        ex.add(int_random);
      }
      for (int i = 0; i < numberOfLocation; i++) {
        coordinates.add(obj.get(ex.get(i)).toString());
      }
      // TODO log exception
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    // return coordinates in format (long,lat)
    return coordinates;
  }

  // read and parse pure json file with cities
  public List<String> readUnformattedJsonFile() {
    JSONParser jsonParser = new JSONParser();
    List<String> coordinates = new ArrayList<>();
    try (FileReader reader = new FileReader("src\\main\\resources\\cities.json")) {
      JSONParser parser = new JSONParser();
      JSONObject obj = (JSONObject) parser.parse(reader);
      JSONArray obj2 = (JSONArray) obj.get("features");
      for (int i = 0; i < obj2.size(); i++) {
        JSONObject ar = (JSONObject) obj2.get(i);
        JSONObject ar2 = (JSONObject) ar.get("geometry");
        coordinates.add(ar2.get("coordinates").toString());
      }
      // TODO log exception
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    // return whole json file
    return coordinates;
  }
  // write json file
  public void writeFile(List<String> json) {
    String jsonString = new Gson().toJson(json);
    try {
      FileWriter myWriter = new FileWriter("localisation.json");
      myWriter.write(jsonString);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
