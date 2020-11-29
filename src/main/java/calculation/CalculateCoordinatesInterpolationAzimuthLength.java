package calculation;

import cli.Units;
import model.Location;
import model.LocationPoint;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;
import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class CalculateCoordinatesInterpolationAzimuthLength {
  private static final int MULTIPLIER = 1000;

  public static List<Location> calculatePointsOnRoute(LocationPoint locationPoint, Units units) {
    Location location;
    List<Location> resultsCoordinates = new ArrayList<>();
    List<Double> newCalculatedLat = new ArrayList<>();
    List<Double> newCalculatedLong = new ArrayList<>();
    newCalculatedLat.add(locationPoint.getPointLatitude().get(0));
    newCalculatedLong.add(locationPoint.getPointLongitude().get(0));
    List<Integer> lastIndexUsed = new ArrayList<>();
    lastIndexUsed.add(0);
    GeodeticCalculator geoCalc = new GeodeticCalculator();
    Ellipsoid reference = Ellipsoid.WGS84;
    double[] endBearing = new double[1];
    double step = locationPoint.getStep();
    int maxLocationIndex = locationPoint.getPointDistance().size();
    for (int oldPosition = 0; oldPosition < maxLocationIndex; oldPosition++) {
      double oldPositionLength = locationPoint.getPointDistance().get(oldPosition);
      double oldPositionAzimuth = locationPoint.getPointAzimuth().get(oldPosition);
      GlobalCoordinates startPoint;
      double extraDistance = 0;
      if (oldPositionLength > step) {
        while (oldPositionLength > step) {
          int lastIndex = lastIndexUsed.get(lastIndexUsed.size() - 1);
          double averageAngle =
              Utilities.calculateP2PAzimuth(
                  locationPoint.getPointLatitude().get(lastIndex),
                  locationPoint.getPointLongitude().get(lastIndex),
                  locationPoint.getPointLatitude().get(oldPosition),
                  locationPoint.getPointLongitude().get(oldPosition));
          if (oldPosition - lastIndex == 0) {
            averageAngle = oldPositionAzimuth;
          }
          lastIndexUsed.add(oldPosition);
          startPoint =
              new GlobalCoordinates(
                  newCalculatedLat.get(newCalculatedLat.size() - 1),
                  newCalculatedLong.get(newCalculatedLong.size() - 1));
          oldPositionLength = oldPositionLength - step;
          GlobalCoordinates dest =
              geoCalc.calculateEndingGlobalCoordinates(
                  reference,
                  startPoint,
                  averageAngle,
                  step * MULTIPLIER * units.getUnit(),
                  endBearing);
          newCalculatedLat.add(dest.getLatitude());
          newCalculatedLong.add(dest.getLongitude());
          extraDistance = oldPositionLength;
        }
        if (extraDistance > 0 && maxLocationIndex > oldPosition + 1) {
          double nextDistanceValue = locationPoint.getPointDistance().get(oldPosition + 1);
          locationPoint.getPointDistance().set(oldPosition + 1, nextDistanceValue + extraDistance);

        } else if (extraDistance > 0 && maxLocationIndex == oldPosition + 1) {
          newCalculatedLat.add(locationPoint.getPointLatitude().get(maxLocationIndex));
          newCalculatedLong.add(locationPoint.getPointLongitude().get(maxLocationIndex));
          break;
        }
      } else {
        if (maxLocationIndex > oldPosition + 1) {
          locationPoint
              .getPointDistance()
              .set(
                  oldPosition + 1,
                  locationPoint.getPointDistance().get(oldPosition + 1)
                      + locationPoint.getPointDistance().get(oldPosition));
        } else {
          break;
        }
      }
    }
    for (int i = 0; i < newCalculatedLat.size(); i++) {
      location = new Location();
      location.setLatitude(newCalculatedLat.get(i));
      location.setLongitude(newCalculatedLong.get(i));
      resultsCoordinates.add(location);
    }
    return resultsCoordinates;
  }

  public static List<Location> calculatePointsOnRoute(
      LocationPoint locationPoint, Units units, boolean debug) {
    List<Location> resultsCoordinates;
    resultsCoordinates = calculatePointsOnRoute(locationPoint, units);

    if (debug) {
      Utilities.debug(resultsCoordinates);
    }
    return resultsCoordinates;
  }
}
