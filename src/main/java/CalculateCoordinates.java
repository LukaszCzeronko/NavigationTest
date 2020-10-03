import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateCoordinates {
  public Map<Double, Double> positionFromCar(LocationPoint locationPoint) {

    Map<Double, Double> positionAfterParameter = new HashMap<>();
    List<Double> positionAfterParameterLat = new ArrayList<>();
    List<Double> positionAfterParameterLong = new ArrayList<>();
    positionAfterParameter.put(
        locationPoint.getPointLatitude().get(0), locationPoint.getPointLongitude().get(0));
    positionAfterParameterLat.add(locationPoint.getPointLatitude().get(0));
    positionAfterParameterLong.add(locationPoint.getPointLongitude().get(0));
    Map<Double, Double> startingPoints = new HashMap<>();
    double sDistance = 0;
    double cDist = 0;
    double cAng;
    double lowSum;
    for (int i = 0; i < locationPoint.getPointDistance().size(); i++) {
      sDistance = sDistance + locationPoint.getPointDistance().get(i) - cDist;
      cDist = 0;
      if (sDistance >= locationPoint.getStep()) {
        lowSum = sDistance - locationPoint.getPointDistance().get(i);
        cDist = locationPoint.getStep() - lowSum;
        cAng = locationPoint.getPointAzimuth().get(i);
        GeodeticCalculator geoCalc = new GeodeticCalculator();
        Ellipsoid reference = Ellipsoid.WGS84;
        GlobalCoordinates startPoint;
        startPoint =
            new GlobalCoordinates(
                locationPoint.getPointLatitude().get(i - 1),
                locationPoint.getPointLongitude().get(i - 1));
        double[] endBearing = new double[1];
        GlobalCoordinates dest =
            geoCalc.calculateEndingGlobalCoordinates(
                reference, startPoint, cAng, cDist, endBearing);
        sDistance = 0;
        locationPoint.getPointLatitude().set(i - 1, dest.getLatitude());
        locationPoint.getPointLongitude().set(i - 1, dest.getLongitude());
        positionAfterParameter.put(
            locationPoint.getPointLatitude().get(i), locationPoint.getPointLongitude().get(i));
        positionAfterParameterLat.add(locationPoint.getPointLatitude().get(i));
        positionAfterParameterLong.add((locationPoint.getPointLongitude().get(i)));
        --i;
      } else if ((i + 1) >= locationPoint.getPointDistance().size()) {
        GeodeticCalculator geoCalc = new GeodeticCalculator();
        Ellipsoid reference = Ellipsoid.WGS84;
        GlobalCoordinates startPoint;
        cAng = locationPoint.getPointAzimuth().get(i);
        startPoint =
            new GlobalCoordinates(
                positionAfterParameterLat.get(positionAfterParameterLat.size() - 1),
                positionAfterParameterLong.get(positionAfterParameterLong.size() - 1));
        double[] endBearing = new double[1];
        GlobalCoordinates dest =
            geoCalc.calculateEndingGlobalCoordinates(
                reference, startPoint, cAng, sDistance, endBearing);

        locationPoint.getPointLatitude().set(i - 1, dest.getLatitude());
        locationPoint.getPointLongitude().set(i - 1, dest.getLongitude());

        positionAfterParameter.put(
            locationPoint.getPointLatitude().get(locationPoint.getPointDistance().size()),
            locationPoint.getPointLongitude().get(locationPoint.getPointDistance().size()));
        positionAfterParameterLat.add(
            locationPoint.getPointLatitude().get(locationPoint.getPointDistance().size()));
        positionAfterParameterLong.add(
            locationPoint.getPointLongitude().get(locationPoint.getPointDistance().size()));

        //                positionAfterParameter.put(posLat.get(posLat.size() - 1),
        // posLong.get(posLong.size() - 1));
        //                positionAfterParameterLat.add(posLat.get(posLat.size() - 1));
        //                positionAfterParameterLong.add(posLong.get(posLong.size() - 1));
      }
    }
    for (int i = 0; i < positionAfterParameterLat.size(); i++) {
      String coordinates =
          positionAfterParameterLat.get(i) + "," + positionAfterParameterLong.get(i) + ";";
      //  System.out.println(coordinates);
    }
    return positionAfterParameter;
  }
}
