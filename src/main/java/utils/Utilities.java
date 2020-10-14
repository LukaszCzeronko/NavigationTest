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
}
