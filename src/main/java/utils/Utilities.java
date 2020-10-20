package utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
public class Utilities {
  // split and reverse string
  public static String splitAndRevertString(String strToReverse) {
    String str1, str2;
    str1 = strToReverse;
    String separated[] = str1.split(",");
    str2 = separated[1] + "," + separated[0];
    return str2;
  }

  public static double calculateStep(double speed, int time) {
    double distance = speed * (double) time / 3600;
    return distance;
  }

  public static long transformDegree(double degree) {
    degree = (degree / 360.0000000) * 10000000;
    double c = Math.pow(2, 32);
    degree = (degree * c) / 10000000;
    long d = (long) degree;
    return d;
  }

  public static String generateId() {
    StringBuilder generatedId = new StringBuilder();
    String alphabet = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
    for (int i = 0; i < 17; i++) {
      int index = (int) (alphabet.length() * Math.random());
      generatedId.append(alphabet.charAt(index));
    }
    return generatedId.toString();
  }

  public static String formatString(List<List<String>> fileContent, List<String> id) {
    int i = 0;
    StringBuilder stringBuilder = new StringBuilder();
    for (List<String> s : fileContent) {

      for (String g : s) {
        stringBuilder.append(g).append(",").append(" ").append(",").append(id.get(i)).append("\n");
      }
      stringBuilder.append("\n\n");
      i++;
    }
    return stringBuilder.toString();
  }

  public static void writeFile(String fileContent, String path) {
    try (FileWriter myWriter = new FileWriter(path)) {
      myWriter.write(fileContent);
      log.info("Successfully wrote to the file.");
    } catch (IOException e) {
      log.error("An error occurred.", e);
    }
  }

  public static int calculatePercent(int numberOfRoutes, int ratio) {
    float result = ((numberOfRoutes * ratio) / 100);
    return (int) result;
  }
}
