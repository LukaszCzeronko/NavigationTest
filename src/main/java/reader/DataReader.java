package reader;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class DataReader {
  // read json type file and setup random localisation for given number of locations
  public List<String> readFormattedJsonFile(int numberOfLocation, String sourceFile) {
    List<Integer> ex = new ArrayList<>();
    List<String> coordinates = new ArrayList<>();
    ClassLoader classLoader = getClass().getClassLoader();
    try (FileReader reader = new FileReader(classLoader.getResource(sourceFile).getFile())) {
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
    } catch (FileNotFoundException e) {
      log.error("An error occurred.", e);
    } catch (IOException e) {
      log.error("An error occurred.", e);
    } catch (ParseException e) {
      log.error("An error occurred.", e);
    }
    // return coordinates in format (long,lat)
    return coordinates;
  }

  // read and parse pure json file with cities
  public List<String> readUnformattedJsonFile() {
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
    } catch (FileNotFoundException e) {
      log.error("An error occurred.", e);
    } catch (IOException e) {
      log.error("An error occurred.", e);
    } catch (ParseException e) {
      log.error("An error occurred.", e);
    }
    // return whole json file
    return coordinates;
  }

  public String readSchema(String schemaPath) {
    JSONObject schema = null;
    try (FileReader reader = new FileReader(schemaPath)) {
      JSONParser parser = new JSONParser();
      schema = (JSONObject) parser.parse(reader);
    } catch (FileNotFoundException e) {
      log.error("An error occurred.", e);
    } catch (IOException e) {
      log.error("An error occurred.", e);
    } catch (ParseException e) {
      log.error("An error occurred.", e);
    }
    return schema.toString();
  }
  // write json file
  public void writeFile(String fileContent, String path) {
    try (FileWriter myWriter = new FileWriter(path)) {
      myWriter.write(fileContent);
      log.info("Successfully wrote to the file.");
    } catch (IOException e) {
      log.error("An error occurred.", e);
    }
  }

  public void writeCsv(List<List<String>> fileContent, List<String> id, String path) {
    try (FileWriter myWriter = new FileWriter(path)) {
      int i = 0;
      for (List<String> s : fileContent) {

        for (String g : s) {
          myWriter.write(g + "," + " " + "," + id.get(i));
          myWriter.write("\n");
        }
        myWriter.write("\n\n");
        i++;
      }
      log.info("Successfully wrote to the file.");
    } catch (IOException e) {
      log.error("An error occurred.", e);
    }
  }
}
