package calculation;

import model.Location;
import model.LocationPoint;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Prototype {
    public List<Location> calculatePointsOnRoutePrototype(LocationPoint locationPoint) {
        Location location;
        List<Location> resultsCoordinates = new ArrayList<>();
      //  Map<Double, Double> newCalculatedCoordinates = new HashMap<>();
        List<Double> newCalculatedLat = new ArrayList<>();
        List<Double> newCalculatedLong = new ArrayList<>();
      //  newCalculatedCoordinates.put(
        //        locationPoint.getPointLatitude().get(0), locationPoint.getPointLongitude().get(0));
        newCalculatedLat.add(locationPoint.getPointLatitude().get(0));
        newCalculatedLong.add(locationPoint.getPointLongitude().get(0));

        double step= locationPoint.getStep();
for(int oldPosition=0;oldPosition<locationPoint.getPointLongitude().size();oldPosition++){
    double oldPositionLength=locationPoint.getPointDistance().get(oldPosition);
    double oldPositionAzimuth=locationPoint.getPointAzimuth().get(oldPosition);
    GeodeticCalculator geoCalc = new GeodeticCalculator();
    Ellipsoid reference = Ellipsoid.WGS84;
    GlobalCoordinates startPoint;

    if(oldPositionLength>step){
         while(oldPositionLength>0){

             startPoint =
                     new GlobalCoordinates(
                             newCalculatedLat.get(newCalculatedLat.size()-1),
                             newCalculatedLong.get(newCalculatedLong.size()-1));

             double[] endBearing = new double[1];
             GlobalCoordinates dest =
                     geoCalc.calculateEndingGlobalCoordinates(
                             reference, startPoint, oldPositionAzimuth, step*100, endBearing);

             newCalculatedLat.add(dest.getLatitude());
             newCalculatedLong.add(dest.getLongitude());
          System.out.println(oldPositionLength);
             oldPositionLength=oldPositionLength-step;
         }


    }
    else{

        System.out.println("sadsda");
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

}
