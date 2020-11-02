package validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Utilities;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CalculatePercentTest {
  @Parameterized.Parameter(0)
  public int numberOfRoutes;

  @Parameterized.Parameter(1)
  public int ratio;

  @Parameterized.Parameter(2)
  public int result;

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data = new Object[][] {{1, 2, 0}, {10, 20, 2}, {0, 0, 0}, {1, 100, 1},{0,100,0},{1,50,0},{2,33,0},{2,50,1},{100,0,0},{10,99,9}};
    return Arrays.asList(data);
  }

  @Test
  public void calculatePercentTest() {
    assertEquals("Result", result, Utilities.calculatePercent(numberOfRoutes, ratio));
  }
}
