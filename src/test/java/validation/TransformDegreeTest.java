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
  public double angle;

  @Parameterized.Parameter(1)
  public long result;

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data =
        new Object[][] {
          {1.0, 11930464},
          {1.5, 17895697},
          {10.0, 119304647},
          {60.0, 715827882},
          {90.0, 1073741824},
          {123.0, 1467447159},
          {123.123, 1468914606},
          {180.0, 2147483648L},
          {270.0, 3221225472L},
          {360.0, 4294967296L},
          {0.0, 0},
          {-1.0, -11930464},
          {-1.5, -17895697},
          {-10.0, -119304647},
          {-60.0, -715827882},
          {-90.0, -1073741824},
          {-123.0, -1467447159},
          {-123.123, -1468914606},
          {-180.0, -2147483648L},
          {-270.0, -3221225472L},
          {-360.0, -4294967296L},
        };
    return Arrays.asList(data);
  }

  @Test
  public void transformTest() {
    assertEquals(Utilities.transformDegree(angle), result);
  }
}
