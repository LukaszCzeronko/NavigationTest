package utils;

public class Utilities {
  // split and reverse string
  public static String splitAndRevertString(String strToReverse) {
    String str1, str2;
    str1 = strToReverse;
    String separated[] = str1.split(",");
    str2 = separated[1] + "," + separated[0];
    return str2;
  }
}
