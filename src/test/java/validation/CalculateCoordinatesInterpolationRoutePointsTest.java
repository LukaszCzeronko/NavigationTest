package validation;


import Utils.SoftAssert;
import calculation.CalculateCoordinatesInterpolationRoutePoints;
import calculation.CalculateCoordinatesInterpolationFinal;
import model.Location;
import model.LocationPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CalculateCoordinatesInterpolationRoutePointsTest {

    @Parameterized.Parameter(0)
    public TestObject testObject;

    @Parameterized.Parameter(1)
    public Result result;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data =
                new Object[][] {
                        {
                                new TestObject(new Double[] {52.522105, 52.5230706,52.5235748,52.5238134}, new Double[] {13.416785, 13.4178257,13.4184051,13.4186437},new Double[]{33.26, 34.96, 31.32}, new Double[]{0.129,0.069,0.031},0.1
                                ),new Result(new Location[]{new Location(52.522105,13.416785),new Location(52.5235748,13.4184051),new Location(52.5235748,13.4184051),new Location(52.5238134,13.4186437)})
                        },
//                        {
//                                new TestObject(new Double[] {53.9663379, 53.9661956,53.966099,53.9659381,53.9657373}, new Double[] {14.9694148, 14.9692369,14.9690545,14.9686146,14.9679619},new Double[]{0.0,0.0,238.13,242.39}, new Double[]{0.020,0.016,0.034,0.048}, 0.1),
//                                new Result(new Location[]{new Location(53.9663379,14.9694148),new Location(53.9659381,14.9686146),new Location(53.9657373,14.9679619)})
//                        },
//
                        {
                                new TestObject(new Double[] {54.8011393, 54.8011029,54.800899,54.8006308,54.7983241,54.7962749}, new Double[] {22.1464429, 22.1467853,22.1495748,22.1553469,22.1627283,22.1691763},new Double[]{100.45,97.23,94.61,118.46,118.86}, new Double[]{0.022,0.18,0.371,0.538,0.472}, 0.1),
                                new Result(new Location[]{new Location(54.8011393,22.1464429),new Location(54.800899,22.1495748),new Location(54.8006308,22.1553469),new Location(54.7983241,22.1627283),new Location(54.7962749,22.1691763)})
                        },
//
//                        {
//                                new TestObject(new Double[] {53.9663379, 53.9661956,53.966099,53.9659381,53.9657373}, new Double[] {14.9694148, 14.9692369,14.9690545,14.9686146,14.9679619},new Double[]{0.0,0.0,238.13,242.39}, new Double[]{0.020,0.016,0.034,0.048}, 0.1),
//                                new Result(new Location[]{new Location(53.9663379,14.9694148),new Location(53.9659381,14.9686146),new Location(53.9657373,14.9679619)})
//                        },
//
//                        {
//                                new TestObject(new Double[] {53.9663379, 53.9661956,53.966099,53.9659381,53.9657373}, new Double[] {14.9694148, 14.9692369,14.9690545,14.9686146,14.9679619},new Double[]{0.0,0.0,238.13,242.39}, new Double[]{0.020,0.016,0.034,0.048}, 0.1),
//                                new Result(new Location[]{new Location(53.9663379,14.9694148),new Location(53.9659381,14.9686146),new Location(53.9657373,14.9679619)})
//                        },

                };
        return Arrays.asList(data);
    }

    @Test
    public void calculateP2PDistanceTests() {
        LocationPoint locationPoint = generateLocationPoint(testObject);
        CalculateCoordinatesInterpolationFinal prototype= new CalculateCoordinatesInterpolationFinal();
        CalculateCoordinatesInterpolationRoutePoints calculateCoordinatesInterpolationRoutePoints = new CalculateCoordinatesInterpolationRoutePoints();
//        for(int j=0;j<prototype.calculatePointsOnRoutePrototype(locationPoint).size();j++){
//
//
//     System.out.println(prototype.calculatePointsOnRoutePrototype(locationPoint).get(j).getLatitude()+","+prototype.calculatePointsOnRoutePrototype(locationPoint).get(j).getLongitude()+";");
//        }
    //        assertEquals(
    //                "Result:",
    //                result.locations,
    //                calculateCoordinates.calculatePointsOnRoute(locationPoint));

     //   SoftAssert softAssert= new SoftAssert();
SoftAssert.equals(result.locations,prototype.calculatePointsOnRoute(locationPoint));
   // System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwa"+a);
//        System.out.println(prototype.calculatePointsOnRoutePrototype(locationPoint));
       assertEquals(
                "Result:",
               result.locations,
               prototype.calculatePointsOnRoute(locationPoint));
    }

    private LocationPoint generateLocationPoint(TestObject testObject) {
        LocationPoint locationPoint = new LocationPoint();
        locationPoint.setPointLongitude(testObject.pointLongitude);
        locationPoint.setPointLatitude(testObject.pointLatitude);
       locationPoint.setStep(testObject.step);
       locationPoint.setPointDistance(testObject.pointDistance);
       locationPoint.setPointAzimuth(testObject.pointAzimuth);
        return locationPoint;
    }

    static class TestObject {
        List<Double> pointLatitude;
        List<Double> pointLongitude;
        List<Double> pointAzimuth;
        List<Double> pointDistance;
        double step;

        public TestObject(Double[] pointLatitude, Double[] pointLongitude,Double[] pointAzimuth,Double[] pointDistance,double step) {
            this.pointLatitude = Arrays.asList(pointLatitude);
            this.pointLongitude = Arrays.asList(pointLongitude);
            this.pointAzimuth=Arrays.asList(pointAzimuth);
            this.pointDistance=Arrays.asList(pointDistance);
            this.step= step;
        }
    }
    static class Result{
        List<Location> locations= new ArrayList<>();
        public Result(Location[] location){
            this.locations.addAll(Arrays.asList(location));
        }

        public List<Location> getLocations() {
            return locations;
        }
    }





}
