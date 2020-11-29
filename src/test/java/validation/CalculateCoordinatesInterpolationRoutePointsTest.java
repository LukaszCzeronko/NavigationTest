package validation;

import Utils.SoftAssert;
import calculation.CalculateCoordinatesInterpolationHighAccuracy;
import cli.Units;
import model.Location;
import model.LocationPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class CalculateCoordinatesInterpolationRoutePointsTest {
  @Parameterized.Parameter() public TestObject testObject;

  @Parameterized.Parameter(1)
  public Result result;

  private static final double THRESHOLD = 0.00001;
  private static final Units UNITS_SYSTEM = Units.METRIC;

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data =
        new Object[][] {
          {
            new TestObject(
                new Double[] {53.9663379, 53.9661956, 53.966099, 53.9659381, 53.9657373},
                new Double[] {14.9694148, 14.9692369, 14.9690545, 14.9686146, 14.9679619},
                new Double[] {0.0, 0.0, 238.13, 242.39},
                new Double[] {0.020, 0.016, 0.034, 0.048},
                0.1),
            new Result(
                new Location[] {
                  new Location(53.9663379, 14.9694148),
                  new Location(53.9658131, 14.9682095),
                  new Location(53.9657373, 14.9679619)
                })
          },
          {
            new TestObject(
                new Double[] {
                  54.8011393, 54.8011029, 54.800899, 54.8006308, 54.7983241, 54.7962749
                },
                new Double[] {
                  22.1464429, 22.1467853, 22.1495748, 22.1553469, 22.1627283, 22.1691763
                },
                new Double[] {100.45, 97.23, 94.61, 118.46, 118.86},
                new Double[] {0.022, 0.18, 0.371, 0.538, 0.472},
                0.5),
            new Result(
                new Location[] {
                  new Location(54.8011393, 22.1464429),
                  new Location(54.8006837, 22.1541935),
                  new Location(54.7988087, 22.1611646),
                  new Location(54.7966508, 22.1679827),
                  new Location(54.7962749, 22.1691763)
                })
          },
          {
            new TestObject(
                new Double[] {
                  52.8119027, 52.8117621, 52.8122342, 52.8128564, 52.8160965, 52.8174869,
                },
                new Double[] {
                  22.3437163, 22.3437667, 22.3450649, 22.3468566, 22.3558581, 22.3596671
                },
                new Double[] {0.0, 58.97, 60.12, 59.22, 58.87},
                new Double[] {0.016, 0.102, 0.139, 0.704, 0.299},
                0.6),
            new Result(
                new Location[] {
                  new Location(52.81190, 22.34371),
                  new Location(52.81443, 22.35122),
                  new Location(52.81719, 22.35886),
                  new Location(52.81748, 22.35966),
                })
          },
          {
            new TestObject(
                new Double[] {
                  54.0321672, 54.0437651, 54.0511787, 54.0542579, 54.0596545, 54.0608968
                },
                new Double[] {
                  37.5224411, 37.5277734, 37.5307667, 37.5319147, 37.5316465, 37.531625,
                },
                new Double[] {15.11, 13.33, 12.34, 358.33, 0.00},
                new Double[] {1.336, 0.847, 0.350, 0.600, 0.138},
                0.7),
            new Result(
                new Location[] {
                  new Location(54.0321672, 37.5224411),
                  new Location(54.0382387, 37.5252262),
                  new Location(54.0443128, 37.5279939),
                  new Location(54.0504313, 37.5304648),
                  new Location(54.0566332, 37.5317969),
                  new Location(54.0608968, 37.531625),
                })
          },
          {
            new TestObject(
                new Double[] {
                  42.4300146, 42.4300897, 42.4255621, 42.4118185, 42.3816919, 42.3286113
                },
                new Double[] {
                  12.4285734, 12.4505782, 12.4679911, 12.471714, 12.4718428, 12.4761281,
                },
                new Double[] {89.73, 109.40, 168.69, 180.00, 176.58},
                new Double[] {1.806, 1.515, 1.558, 3.349, 5.912},
                5.0),
            new Result(
                new Location[] {
                  new Location(42.4300146, 12.4285734),
                  new Location(42.4107292, 12.4717139),
                  new Location(42.3657455, 12.4731278),
                  new Location(42.3286113, 12.4761281)
                })
          },
          {
            new TestObject(
                new Double[] {
                  60.3099532, 60.3141153, 60.3263032, 60.3323436, 60.3344142, 60.338695
                },
                new Double[] {
                  13.0974491, 13.0999732, 13.1263554, 13.1381249, 13.1425667, 13.1488753,
                },
                new Double[] {16.72, 46.97, 43.96, 46.71, 36.10},
                new Double[] {0.483, 1.986, 0.933, 0.336, 0.589},
                0.8),
            new Result(
                new Location[] {
                  new Location(60.3099532, 13.0974491),
                  new Location(60.3160567, 13.1041660),
                  new Location(60.3209500, 13.1147600),
                  new Location(60.3258433, 13.1253556),
                  new Location(60.3309845, 13.1354612),
                  new Location(60.3362479, 13.1452641),
                  new Location(60.338695, 13.1488753),
                })
          },
        };
    return Arrays.asList(data);
  }

  @Test
  public void calculateP2PDistanceTests() {
    LocationPoint locationPoint = generateLocationPoint(testObject);
    SoftAssert.equals(
        result.locations,
        CalculateCoordinatesInterpolationHighAccuracy.calculatePointsOnRoute(
            locationPoint, UNITS_SYSTEM),
        THRESHOLD);
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

    public TestObject(
        Double[] pointLatitude,
        Double[] pointLongitude,
        Double[] pointAzimuth,
        Double[] pointDistance,
        double step) {
      this.pointLatitude = Arrays.asList(pointLatitude);
      this.pointLongitude = Arrays.asList(pointLongitude);
      this.pointAzimuth = Arrays.asList(pointAzimuth);
      this.pointDistance = Arrays.asList(pointDistance);
      this.step = step;
    }
  }

  static class Result {
    List<Location> locations = new ArrayList<>();

    public Result(Location[] location) {
      this.locations.addAll(Arrays.asList(location));
    }
  }
}
