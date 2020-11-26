package calculation;

import cli.Units;
import model.LocationPoint;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticMeasurement;
import org.gavaghan.geodesy.GlobalPosition;

import java.util.ArrayList;
import java.util.List;

public class CalculateP2PDistance {
  public LocationPoint calculateDistance(
      LocationPoint locationPoint, double maxRouteLength, Units multiplier, double step) {
    List<Double> pointDistance = new ArrayList<>();
    List<Double> pointAzimuth = new ArrayList<>();
    locationPoint.setStep(step);
    double distance = 0;
    for (int i = 0; i < (locationPoint.getPointLatitude().size() - 1); i++) {
      GeodeticCalculator geoCalc = new GeodeticCalculator();
      Ellipsoid reference = Ellipsoid.WGS84;
      GlobalPosition firstPoint;
      firstPoint =
          new GlobalPosition(
              locationPoint.getPointLatitude().get(i),
              locationPoint.getPointLongitude().get(i),
              locationPoint.getPointElevation().get(i));
      GlobalPosition secondPoint;
      secondPoint =
          new GlobalPosition(
              locationPoint.getPointLatitude().get(i + 1),
              locationPoint.getPointLongitude().get(i + 1),
              locationPoint.getPointElevation().get(i + 1));
      GeodeticMeasurement geoMeasurement;
      double p2pKilometers;
      geoMeasurement = geoCalc.calculateGeodeticMeasurement(reference, firstPoint, secondPoint);
      p2pKilometers = geoMeasurement.getPointToPointDistance() / (1000.0 * multiplier.getUnit());
      pointDistance.add(p2pKilometers);
      pointAzimuth.add(geoMeasurement.getAzimuth());
      distance = p2pKilometers + distance;
      if (distance >= maxRouteLength) {
        locationPoint.setOverallDistance(distance);
        break;
      }
    }
    LocationPoint returnPoints =
        new LocationPoint(
            locationPoint.getPointLatitude(),
            locationPoint.getPointLongitude(),
            pointDistance,
            pointAzimuth,
            locationPoint.getStep(),
            locationPoint.getOverallDistance());
    return returnPoints;
  }
}
