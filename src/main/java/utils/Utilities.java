package utils;

import lombok.extern.slf4j.Slf4j;
import model.RequestConfigList;
import validation.ValidationUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Utilities {
  private static final String ALPHABET = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";

  public static String splitAndRevertString(String strToReverse) {
    String str1, str2;
    str1 = strToReverse;
    String[] separated = str1.split(",");
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
    for (int i = 0; i < 17; i++) {
      int index = (int) (ALPHABET.length() * Math.random());
      generatedId.append(ALPHABET.charAt(index));
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

  public static List<Integer> calculateRouteDistribution(
      RequestConfigList requestConfigList, int numberOfRoutes) {
    ValidationUtils.checkGreaterEqualsZero("Number of routes is smaller or equals zero ",numberOfRoutes);
    int counter = numberOfRoutes;
    List<Integer> distribution = new ArrayList<>();
    for (int i = 0; i < requestConfigList.getConfigList().size(); i++) {
      int ratioForCurrentConfig = requestConfigList.getConfigList().get(i).getRatio();
      int routesWithGivenConfig = Utilities.calculatePercent(numberOfRoutes, ratioForCurrentConfig);
      if (counter - routesWithGivenConfig > 0) {
        distribution.add(routesWithGivenConfig);
        counter = counter - routesWithGivenConfig;
      } else {
        distribution.add(counter);
        counter = 0;
        break;
      }
    }
    if (requestConfigList.getConfigList().get(0).getRatio() == 0 && counter > 0) {
      distribution.set(0, counter);
    }
    if (counter > 0 && requestConfigList.getConfigList().size() == 1) {
      distribution.set(0, numberOfRoutes);
    }
    if (counter > 0 && requestConfigList.getConfigList().get(0).getRatio() != 0 && requestConfigList.getConfigList().size() != 1) {
      int actualDistribution=distribution.get(0);
      distribution.set(0, actualDistribution+counter);
    }
    return distribution;
  }
}
