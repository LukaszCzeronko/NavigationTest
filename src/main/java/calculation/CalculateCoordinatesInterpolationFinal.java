package calculation;

import model.Location;
import model.LocationPoint;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;
import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class CalculateCoordinatesInterpolationFinal {

  public List<Location> calculatePointsOnRoute(LocationPoint locationPoint) {
    Location location;
    List<Location> resultsCoordinates = new ArrayList<>();
    List<Double> newCalculatedLat = new ArrayList<>();
    List<Double> newCalculatedLong = new ArrayList<>();
    newCalculatedLat.add(locationPoint.getPointLatitude().get(0));
    newCalculatedLong.add(locationPoint.getPointLongitude().get(0));
    double step = locationPoint.getStep();
    double originalRouteSize = locationPoint.getPointDistance().size();
    for (int routeIndex = 0; routeIndex < originalRouteSize; routeIndex++) {
      GeodeticCalculator geoCalc = new GeodeticCalculator();
      Ellipsoid reference = Ellipsoid.WGS84;
      GlobalCoordinates startPoint;
      double currentDistance = locationPoint.getPointDistance().get(routeIndex);

      // get first route that length>step and aprox back
      if (step < currentDistance) {

        startPoint =
            new GlobalCoordinates(
                locationPoint.getPointLatitude().get(routeIndex),
                locationPoint.getPointLongitude().get(routeIndex));
        currentDistance = currentDistance - step;
        double lackingDistance;
        if (routeIndex == 0) {
          lackingDistance = step;
        } else {
          lackingDistance = step - locationPoint.getPointDistance().get(routeIndex - 1);
        }
        double[] endBearing = new double[1];
        GlobalCoordinates dest =
            geoCalc.calculateEndingGlobalCoordinates(
                reference,
                startPoint,
                locationPoint.getPointAzimuth().get(routeIndex),
                lackingDistance * 1000,
                endBearing);
        newCalculatedLat.add(dest.getLatitude());
        newCalculatedLong.add(dest.getLongitude());
        double newDistance =
            Utilities.calculateP2PDistance(
                dest.getLatitude(),
                dest.getLongitude(),
                locationPoint.getPointLatitude().get(routeIndex + 1),
                locationPoint.getPointLongitude().get(routeIndex + 1));
        double newAzimuth =
            Utilities.calculateP2PAzimuth(
                dest.getLatitude(),
                dest.getLongitude(),
                locationPoint.getPointLatitude().get(routeIndex + 1),
                locationPoint.getPointLongitude().get(routeIndex + 1));
        locationPoint.getPointLatitude().set(routeIndex, dest.getLatitude());
        locationPoint.getPointLongitude().set(routeIndex, dest.getLongitude());
        locationPoint.getPointDistance().set(routeIndex, newDistance);
        locationPoint.getPointAzimuth().set(routeIndex, newAzimuth);
        routeIndex = routeIndex - 1;
        if (currentDistance > step) {
          routeIndex = routeIndex + 1;
          while (currentDistance > step) {
            startPoint =
                new GlobalCoordinates(
                    newCalculatedLat.get(newCalculatedLat.size() - 1),
                    newCalculatedLong.get(newCalculatedLong.size() - 1));
            currentDistance = currentDistance - step;
            dest =
                geoCalc.calculateEndingGlobalCoordinates(
                    reference,
                    startPoint,
                    locationPoint.getPointAzimuth().get(routeIndex),
                    step * 1000,
                    endBearing);
            newCalculatedLat.add(dest.getLatitude());
            newCalculatedLong.add(dest.getLongitude());
          }
          newDistance =
              Utilities.calculateP2PDistance(
                  dest.getLatitude(),
                  dest.getLongitude(),
                  locationPoint.getPointLatitude().get(routeIndex + 1),
                  locationPoint.getPointLongitude().get(routeIndex + 1));
          newAzimuth =
              Utilities.calculateP2PAzimuth(
                  dest.getLatitude(),
                  dest.getLongitude(),
                  locationPoint.getPointLatitude().get(routeIndex + 1),
                  locationPoint.getPointLongitude().get(routeIndex + 1));
          locationPoint.getPointLatitude().set(routeIndex, dest.getLatitude());
          locationPoint.getPointLongitude().set(routeIndex, dest.getLongitude());
          locationPoint.getPointDistance().set(routeIndex, newDistance);
          locationPoint.getPointAzimuth().set(routeIndex, newAzimuth);
          routeIndex = routeIndex - 1;
        }
      } else {

        if (originalRouteSize > routeIndex + 1) {
          locationPoint
              .getPointDistance()
              .set(
                  routeIndex + 1,
                  locationPoint.getPointDistance().get(routeIndex + 1)
                      + locationPoint.getPointDistance().get(routeIndex));
        }
      }
      if (originalRouteSize == routeIndex + 1) {
        newCalculatedLat.add(locationPoint.getPointLatitude().get(routeIndex + 1));
        newCalculatedLong.add(locationPoint.getPointLongitude().get(routeIndex + 1));
        break;
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

  public List<Location> calculatePointsOnRoute(LocationPoint locationPoint, boolean debug) {
    List<Location> resultsCoordinates;
    resultsCoordinates = calculatePointsOnRoute(locationPoint);

    if (debug) {
      Utilities.debug(resultsCoordinates);
    }
    return resultsCoordinates;
  }
}
