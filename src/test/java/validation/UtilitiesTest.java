package validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Utilities;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UtilitiesTest {
  @Parameterized.Parameter(0)
  public int numberOfRoutes;

  @Parameterized.Parameter(1)
  public int ratio;

  @Parameterized.Parameter(2)
  public int result;

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data = new Object[][] {{1, 2, 0}, {10, 20, 2},{0,0,0},{1,100,1}};
    return Arrays.asList(data);
  }

  @Test
  public void calculatePercentTest() {
    assertEquals("Result", result, Utilities.calculatePercent(numberOfRoutes, ratio));
  }
}
