import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticMeasurement;
import org.gavaghan.geodesy.GlobalPosition;

import java.util.ArrayList;
import java.util.List;

public class CalculateP2PDistance {
  LocationPoint locationPoint;
  List<Double> posLat;
  List<Double> posLong;
  List<Double> elevation;
  List<Double> cDistance;
  // TODO remove class fields
  public CalculateP2PDistance(LocationPoint locationPoint) {
    this.locationPoint = locationPoint;
    this.posLat = locationPoint.getPointLatitude();
    this.posLong = locationPoint.getPointLongitude();
    this.elevation = locationPoint.getPointElevation();
    this.cDistance = locationPoint.getPointDistance();
  }

  public LocationPoint calculateDistance() {
    // PointsUtils returnPoints;
    //  System.out.println(this.cDistance);
    List<Double> pointDistance = new ArrayList<>();
    List<Double> pointAzimuth = new ArrayList<>();
    double distance = 0;

    int j = 0;

    for (int i = 0; i < (this.posLat.size() - 1); i++) {

      GeodeticCalculator geoCalc = new GeodeticCalculator();
      Ellipsoid reference = Ellipsoid.WGS84;
      GlobalPosition firstPoint;
      firstPoint =
          new GlobalPosition(this.posLat.get(i), this.posLong.get(i), this.elevation.get(i));
      GlobalPosition secondPoint;
      secondPoint =
          new GlobalPosition(
              this.posLat.get(i + 1), this.posLong.get(i + 1), this.elevation.get(i + 1));

      GeodeticMeasurement geoMeasurement;
      double p2pKilometers;
      double elevChangeMeters;
      geoMeasurement = geoCalc.calculateGeodeticMeasurement(reference, firstPoint, secondPoint);
      p2pKilometers = geoMeasurement.getPointToPointDistance() / 1000.0;
      elevChangeMeters = geoMeasurement.getElevationChange();
      pointDistance.add(p2pKilometers);
      pointAzimuth.add(geoMeasurement.getAzimuth());
      distance = p2pKilometers + distance;
      System.out.println(this.cDistance.get(j) / 1000 + "  aaaa  " + distance);

      if (distance >= (this.cDistance.get(j) / 1000)) {
        System.out.println(this.cDistance.get(j));
        System.out.println("country nr " + j + " distance=" + distance);
        distance = 0;
        j = j + 1;
        if (j == this.cDistance.size()) {
          break;
        }
      }
      System.out.println(j);
    }
    System.out.println(distance);
    LocationPoint returnPoints = new LocationPoint(posLat, posLong, pointDistance, pointAzimuth, 3);
    return returnPoints;
  }
}
