package calculation;

import cli.Units;
import lombok.extern.slf4j.Slf4j;
import model.Location;
import model.LocationPoint;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;
import utils.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CalculateCoordinatesInterpolationRoutePoints {
  public static List<Location> calculatePointsOnRoute(LocationPoint locationPoint, Units units) {
    Location location;
    List<Location> resultsCoordinates = new ArrayList<>();
    Map<Double, Double> newCalculatedCoordinates = new HashMap<>();
    List<Double> newCalculatedLat = new ArrayList<>();
    List<Double> newCalculatedLong = new ArrayList<>();
    newCalculatedCoordinates.put(
        locationPoint.getPointLatitude().get(0), locationPoint.getPointLongitude().get(0));
    newCalculatedLat.add(locationPoint.getPointLatitude().get(0));
    newCalculatedLong.add(locationPoint.getPointLongitude().get(0));
    double sDistance = 0;
    double cDist = 0;
    double cAng;
    double lowSum = 0;
    GeodeticCalculator geoCalc = new GeodeticCalculator();
    Ellipsoid reference = Ellipsoid.WGS84;
    double[] endBearing = new double[1];
    GlobalCoordinates startPoint;
    for (int i = 0; i < locationPoint.getPointDistance().size(); i++) {
      sDistance = sDistance + locationPoint.getPointDistance().get(i) - cDist;
      cDist = 0;
      if (sDistance >= locationPoint.getStep()) {
        lowSum = sDistance - locationPoint.getPointDistance().get(i);
        cDist = locationPoint.getStep() - lowSum;
        cAng = locationPoint.getPointAzimuth().get(i);
        startPoint =
            new GlobalCoordinates(
                locationPoint.getPointLatitude().get(i - 1),
                locationPoint.getPointLongitude().get(i - 1));
        GlobalCoordinates dest =
            geoCalc.calculateEndingGlobalCoordinates(
                reference, startPoint, cAng, cDist * units.getUnit(), endBearing);
        sDistance = 0;
        locationPoint.getPointLatitude().set(i - 1, dest.getLatitude());
        locationPoint.getPointLongitude().set(i - 1, dest.getLongitude());
        newCalculatedCoordinates.put(
            locationPoint.getPointLatitude().get(i), locationPoint.getPointLongitude().get(i));
        newCalculatedLat.add(locationPoint.getPointLatitude().get(i - 1));
        newCalculatedLong.add((locationPoint.getPointLongitude().get(i - 1)));
        --i;
      } else if ((i + 1) >= locationPoint.getPointDistance().size()) {
        cAng = locationPoint.getPointAzimuth().get(i);
        startPoint =
            new GlobalCoordinates(
                newCalculatedLat.get(newCalculatedLat.size() - 1),
                newCalculatedLong.get(newCalculatedLong.size() - 1));
        GlobalCoordinates dest =
            geoCalc.calculateEndingGlobalCoordinates(
                reference, startPoint, cAng, sDistance * units.getUnit(), endBearing);
        locationPoint.getPointLatitude().set(i - 1, dest.getLatitude());
        locationPoint.getPointLongitude().set(i - 1, dest.getLongitude());
        newCalculatedCoordinates.put(
            locationPoint.getPointLatitude().get(locationPoint.getPointDistance().size()),
            locationPoint.getPointLongitude().get(locationPoint.getPointDistance().size()));
        newCalculatedLat.add(
            locationPoint.getPointLatitude().get(locationPoint.getPointDistance().size()));
        newCalculatedLong.add(
            locationPoint.getPointLongitude().get(locationPoint.getPointDistance().size()));
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
