package utils;

import lombok.extern.slf4j.Slf4j;

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
    degree = degree / 360.0000000;
    degree = degree * 10000000;
    double c = Math.pow(2, 32);
    degree = degree * c;
    degree = degree / 10000000;
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
}
