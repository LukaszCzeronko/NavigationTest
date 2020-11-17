package validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Utilities;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TransformDegreeTest {
  @Parameterized.Parameter(0)
  public String testCase;

  @Parameterized.Parameter(1)
  public double angle;

  @Parameterized.Parameter(2)
  public long result;

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data =
        new Object[][] {
          {"TC 6.0", 1.0, 11930464},
          {"TC 6.1", 1.5, 17895697},
          {"TC 6.2", 10.0, 119304647},
          {"TC 6.3", 60.0, 715827882},
          {"TC 6.4", 90.0, 1073741824},
          {"TC 6.5", 123.0, 1467447159},
          {"TC 6.6", 123.123, 1468914606},
          {"TC 6.7", 180.0, 2147483648L},
          {"TC 6.8", 270.0, 3221225472L},
          {"TC 6.9", 360.0, 4294967296L},
          {"TC 6.10", 0.0, 0},
          {"TC 6.11", -1.0, -11930464},
          {"TC 6.12", -1.5, -17895697},
          {"TC 6.13", -10.0, -119304647},
          {"TC 6.14", -60.0, -715827882},
          {"TC 6.15", -90.0, -1073741824},
          {"TC 6.16", -123.0, -1467447159},
          {"TC 6.17", -123.123, -1468914606},
          {"TC 6.18", -180.0, -2147483648L},
          {"TC 6.19", -270.0, -3221225472L},
          {"TC 6.20", -360.0, -4294967296L},
        };
    return Arrays.asList(data);
  }

  @Test
  public void transformTest() {
    assertEquals(testCase, Utilities.transformDegree(angle), result);
  }
}
