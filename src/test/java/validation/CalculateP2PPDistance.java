package validation;

import calculation.CalculateP2PDistance;
import cli.Units;
import model.LocationPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CalculateP2PPDistance {
  @Parameterized.Parameter(0)
  public TestObject testObject;

  @Parameterized.Parameter(1)
  public Double[] result;

  @Parameterized.Parameter(2)
  public double maxRouteLength;

  @Parameterized.Parameter(3)
  public double step;

  public static final int IN_RANGE = 100000;
  public static final int OUT_OF_RANGE = 100;
  // lat/long
  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data =
        new Object[][] {
          {
            new TestObject(new Double[] {0.00, 0.00}, new Double[] {0.00, 0.00}),
            new Double[] {0.00},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {-1.00, 0.00}, new Double[] {0.00, 0.00}),
            new Double[] {110.57},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {0.00, 0.00}, new Double[] {0.00, 1.00}),
            new Double[] {110.57},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {0.00, 1.00}, new Double[] {0.00, 0.00}),
            new Double[] {110.57},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {0.00, 1.00}, new Double[] {1.00, 0.00}),
            new Double[] {156.00},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {180.00, 180.00}, new Double[] {180.00, 181.00}),
            new Double[] {111.00},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {0.00, 180.00}, new Double[] {0.00, 0.00}),
            new Double[] {19903.59},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {0.00, 0.00}, new Double[] {180.00, 0.00}),
            new Double[] {19903.59},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {60.00, 66.00}, new Double[] {30.00, 31.00}),
            new Double[] {669.80},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {60.00, 60.00}, new Double[] {30.00, 31.00}),
            new Double[] {55.80},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {45.00, 45.00}, new Double[] {30.00, 31.00}),
            new Double[] {78.85},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {30.00, 30.00}, new Double[] {30.00, 31.00}),
            new Double[] {96.49},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {0.00, 0.00}, new Double[] {30.00, 31.00}),
            new Double[] {111.3},
            1000,
            1.5
          },
          {
            new TestObject(new Double[] {0.00, 1.00, 1.11}, new Double[] {180.00, 181.00, 181.11}),
            new Double[] {156.89, 17.257},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {0.00, 1.00, 6.11}, new Double[] {180.00, 181.00, 181.11}),
            new Double[] {156.89, 565.19},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {0.00, 90.00, -90.00}, new Double[] {0.00, 0.00, 0.00}),
            new Double[] {10001.96, 20003.93},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {0.00, 0.00, 0.00}, new Double[] {0.00, 90.00, 180.00}),
            new Double[] {10018.75, 10018.75},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(
                new Double[] {0.00, 0.00, 0.00, 0.00, 0.00},
                new Double[] {0.00, 90.00, 180.00, 270.00, 360.00}),
            new Double[] {10018.75, 10018.75, 10018.75, 10018.75},
            IN_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {0.00, 1.00, 1.11}, new Double[] {180.00, 181.00, 181.11}),
            new Double[] {156.89},
            OUT_OF_RANGE,
            1.5
          },
          {
            new TestObject(new Double[] {0.00, 0.00, 1.11}, new Double[] {180.00, 180.50, 181.11}),
            new Double[] {55.65, 140.26},
            OUT_OF_RANGE,
            1.5
          },
          {
            new TestObject(
                new Double[] {0.00, 1.00, 1.11, 1.88},
                new Double[] {180.00, 181.00, 181.11, 181.84}),
            new Double[] {156.89},
            OUT_OF_RANGE,
            1.5
          },
          {
            new TestObject(
                new Double[] {0.00, 0.30, 0.70, 0.90},
                new Double[] {180.00, 180.40, 180.71, 180.81}),
            new Double[] {55.52, 56.0},
            OUT_OF_RANGE,
            1.5
          },
          {
            new TestObject(
                new Double[] {0.00, 0.30, 0.70, 0.90},
                new Double[] {180.00, 180.40, 180.71, 180.81}),
            new Double[] {55.52, 56.0},
            OUT_OF_RANGE,
            1.5
          },
          {
            new TestObject(
                new Double[] {0.00, 0.30, 0.70, 0.90},
                new Double[] {180.00, 180.00, 180.01, 180.81}),
            new Double[] {33.17, 44.24, 91.75},
            OUT_OF_RANGE,
            1.5
          },
          {
            new TestObject(
                new Double[] {0.00, 0.30, 1.70, 0.90},
                new Double[] {180.00, 180.40, 180.71, 180.81}),
            new Double[] {55.52, 158.60},
            OUT_OF_RANGE,
            1.5
          },
        };
    return Arrays.asList(data);
  }

  @Test
  public void calculateP2PDistanceTests() {
    LocationPoint locationPoint = generateLocationPoint(testObject);
    CalculateP2PDistance calculateP2PDistance = new CalculateP2PDistance();
    List<Double> distances =
        calculateP2PDistance
            .calculateDistance(locationPoint, maxRouteLength, Units.METRIC, step)
            .getPointDistance();
    assertEquals("Wrong number of distances returned", distances.size(), result.length);
    int i = 0;
    for (Double distance : result) {
      assertEquals("Wrong length of distance nr: " + i, distance, distances.get(i), 1.0);
      i++;
    }
  }

  private LocationPoint generateLocationPoint(TestObject testObject) {
    LocationPoint locationPoint = new LocationPoint();
    locationPoint.setPointLongitude(testObject.pointLongitude);
    locationPoint.setPointLatitude(testObject.pointLatitude);
    locationPoint.setPointElevation(Arrays.asList(new Double[] {0.0, 0.0, 0.0, 0.0, 0.0}.clone()));
    return locationPoint;
  }

  static class TestObject {
    List<Double> pointLatitude;
    List<Double> pointLongitude;

    public TestObject(Double[] pointLatitude, Double[] pointLongitude) {
      this.pointLatitude = Arrays.asList(pointLatitude);
      this.pointLongitude = Arrays.asList(pointLongitude);
    }
  }
}
