package utils;

import cli.Units;
import lombok.extern.slf4j.Slf4j;
import model.Location;
import model.RequestConfigList;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalPosition;
import validation.ValidationUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
public class Utilities {
  private static final String ALPHABET = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
  private static final int SECONDS_IN_HOUR = 3600;
  private static final double TRANSFORM_MULTIPLIER = 360.000000000;
  private static final int TRANSFORM_DIVIDER = 1000000000;
  private static final int TRANSFORM_RATIO_DIVIDER = 100;
  private static final double DISTANCE_RATIO_DIVIDER = 1000.0;
  private static final String CREDENTIALS_SOURCE = "credentials.properties";
  private static final int ID_LENGTH = 10;

  public static String splitAndRevertString(String strToReverse) {
    String str1, str2;
    str1 = strToReverse;
    String[] separated = str1.split(",");
    str2 = separated[1] + "," + separated[0];
    return str2;
  }

  public static double calculateStep(double speed, int time) {
    double distance = speed * (double) time / SECONDS_IN_HOUR;
    return distance;
  }

  public static long transformDegree(double degree) {
    degree = (degree / TRANSFORM_MULTIPLIER) * TRANSFORM_DIVIDER;
    double c = Math.pow(2, 32);
    degree = (degree * c) / TRANSFORM_DIVIDER;
    long d = (long) degree;
    return d;
  }

  public static String generateId() {
    StringBuilder generatedId = new StringBuilder();
    for (int i = 0; i < ID_LENGTH; i++) {
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
    } catch (IOException e) {
      log.error("An error occurred.", e);
    }
  }

  public static int calculatePercent(int numberOfRoutes, int ratio) {
    float result = ((numberOfRoutes * ratio) / TRANSFORM_RATIO_DIVIDER);
    return (int) result;
  }

  public static List<Integer> calculateRouteDistribution(
      RequestConfigList requestConfigList, int numberOfRoutes) {
    ValidationUtils.checkGreaterEqualsZero(
        "Number of routes is smaller or equals zero ", numberOfRoutes);
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
    if (counter > 0
        && requestConfigList.getConfigList().get(0).getRatio() != 0
        && requestConfigList.getConfigList().size() != 1) {
      int actualDistribution = distribution.get(0);
      distribution.set(0, actualDistribution + counter);
    }
    return distribution;
  }

  public static double calculateP2PAzimuth(double lat1, double lon1, double lat2, double lon2) {
    GeodeticCalculator geoCalc = new GeodeticCalculator();
    Ellipsoid reference = Ellipsoid.WGS84;
    GlobalPosition firstPoint;
    firstPoint = new GlobalPosition(lat1, lon1, 1);
    GlobalPosition secondPoint;
    secondPoint = new GlobalPosition(lat2, lon2, 1);
    GeodeticCurve geoCurve = geoCalc.calculateGeodeticCurve(reference, firstPoint, secondPoint);
    geoCalc.calculateGeodeticCurve(reference, firstPoint, secondPoint);
    return geoCurve.getAzimuth();
  }

  public static double calculateP2PDistance(
      double lat1, double lon1, double lat2, double lon2, Units multiplier) {
    GeodeticCalculator geoCalc = new GeodeticCalculator();
    Ellipsoid reference = Ellipsoid.WGS84;
    GlobalPosition firstPoint;
    firstPoint = new GlobalPosition(lat1, lon1, 1);
    GlobalPosition secondPoint;
    secondPoint = new GlobalPosition(lat2, lon2, 1);
    GeodeticCurve geoCurve = geoCalc.calculateGeodeticCurve(reference, firstPoint, secondPoint);
    double ellipseKilometers =
        geoCurve.getEllipsoidalDistance() / (DISTANCE_RATIO_DIVIDER * multiplier.getUnit());
    geoCalc.calculateGeodeticCurve(reference, firstPoint, secondPoint);
    return ellipseKilometers;
  }

  public static void debug(List<Location> location) {
    for (Location point : location) {
      log.info(point.getLatitude() + "," + point.getLongitude() + ";");
    }
  }

  public static Map<String, String> getCredentials() {
    Properties prop = new Properties();
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    Map<String, String> properties = new HashMap<>();
    InputStream stream = loader.getResourceAsStream(CREDENTIALS_SOURCE);
    try {
      prop.load(stream);
      ValidationUtils.checkIfEmpty("Credentials properties are empty", prop);
      properties = (Map) prop;
    } catch (IOException e) {
      log.error("Can't load credentials file", e);
    }
    return properties;
  }
}
