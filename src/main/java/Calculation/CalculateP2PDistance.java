package Calculation;

import groovy.util.logging.Slf4j;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticMeasurement;
import org.gavaghan.geodesy.GlobalPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class CalculateP2PDistance {
  Logger logger= LoggerFactory.getLogger(CalculateP2PDistance.class);
  public LocationPoint calculateDistance(LocationPoint locationPoint) {
    List<Double> pointDistance = new ArrayList<>();
    List<Double> pointAzimuth = new ArrayList<>();
    double distance = 0;
    int j = 0;
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
      p2pKilometers = geoMeasurement.getPointToPointDistance() / 1000.0;
      pointDistance.add(p2pKilometers);
      pointAzimuth.add(geoMeasurement.getAzimuth());
      distance = p2pKilometers + distance;
      logger.info("Distance {}.",distance);
      if (distance >= (locationPoint.getCountryDistance().get(j) / 1000)) {
        distance = 0;
        j = j + 1;
        if (j == locationPoint.getCountryDistance().size()) {
          break;
        }
      }
    }
    LocationPoint returnPoints =
        new LocationPoint(
            locationPoint.getPointLatitude(),
            locationPoint.getPointLongitude(),
            pointDistance,
            pointAzimuth,
            3);
    return returnPoints;
  }
}
