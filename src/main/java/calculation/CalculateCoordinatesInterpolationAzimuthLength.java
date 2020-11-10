package calculation;

import model.Location;
import model.LocationPoint;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;
import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class CalculateCoordinatesInterpolationAzimuthLength {
  public List<Location> calculatePointsOnRoutePrototype(LocationPoint locationPoint) {
    Location location;
    List<Location> resultsCoordinates = new ArrayList<>();
    List<Double> newCalculatedLat = new ArrayList<>();
    List<Double> newCalculatedLong = new ArrayList<>();
    newCalculatedLat.add(locationPoint.getPointLatitude().get(0));
    newCalculatedLong.add(locationPoint.getPointLongitude().get(0));
    List<Integer> lastIndexUsed = new ArrayList<>();
    lastIndexUsed.add(0);

    double step = locationPoint.getStep();
    int locationIndex = locationPoint.getPointDistance().size();
    for (int oldPosition = 0; oldPosition < locationIndex; oldPosition++) {
      double oldPositionLength = locationPoint.getPointDistance().get(oldPosition);
      double oldPositionAzimuth = locationPoint.getPointAzimuth().get(oldPosition);
      GeodeticCalculator geoCalc = new GeodeticCalculator();
      Ellipsoid reference = Ellipsoid.WGS84;
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
          double[] endBearing = new double[1];
          GlobalCoordinates dest =
              geoCalc.calculateEndingGlobalCoordinates(
                  reference, startPoint, averageAngle, step * 1000, endBearing);
          newCalculatedLat.add(dest.getLatitude());
          newCalculatedLong.add(dest.getLongitude());
          extraDistance = oldPositionLength;
        }
        if (extraDistance > 0 && locationIndex > oldPosition + 1) {
          double nextDistanceValue = locationPoint.getPointDistance().get(oldPosition + 1);
          locationPoint.getPointDistance().set(oldPosition + 1, nextDistanceValue + extraDistance);

        } else if (extraDistance > 0 && locationIndex == oldPosition + 1) {
          newCalculatedLat.add(locationPoint.getPointLatitude().get(locationIndex));
          newCalculatedLong.add(locationPoint.getPointLongitude().get(locationIndex));
          break;
        }
      } else {
        if (locationIndex > oldPosition + 1) {
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
      System.out.println(newCalculatedLat.get(i) + "," + newCalculatedLong.get(i) + ";");
      location = new Location();
      location.setLatitude(newCalculatedLat.get(i));
      location.setLongitude(newCalculatedLong.get(i));
      resultsCoordinates.add(location);
    }
    return resultsCoordinates;
  }
}
