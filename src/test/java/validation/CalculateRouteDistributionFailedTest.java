package validation;

import model.RequestConfig;
import model.RequestConfigList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CalculateRouteDistributionFailedTest {
  private static final String EXP_PARAMETER = "Number of routes is smaller or equals zero";

  @Parameterized.Parameter(0)
  public TestObject testObject;

  @Parameterized.Parameter(1)
  public String message;

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data =
        new Object[][] {
          {new TestObject(new Integer[] {50, 50}, -6), EXP_PARAMETER},
          {new TestObject(new Integer[] {50, 50}, -1), EXP_PARAMETER},
          {new TestObject(new Integer[] {50, 50}, 0), EXP_PARAMETER},
        };
    return Arrays.asList(data);
  }

  @Test
  public void calculateRouteDistributionTests() {
    RequestConfigList requestConfigList = generateRequestConfigList(testObject);

    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              Utilities.calculateRouteDistribution(requestConfigList, testObject.numberOfRoutes);
            });
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(message));
  }

  private RequestConfigList generateRequestConfigList(TestObject testObject) {
    RequestConfigList requestConfigList = new RequestConfigList();
    List<RequestConfig> request = new ArrayList<>();
    for (Integer ratio : testObject.ratios) {
      RequestConfig requestConfig = new RequestConfig();
      requestConfig.setRatio(ratio);
      request.add(requestConfig);
    }
    requestConfigList.setConfigList(request);
    return requestConfigList;
  }

  static class TestObject {
    List<Integer> ratios;
    int numberOfRoutes;

    public TestObject(Integer[] ratios, int numberOfRoutes) {
      this.ratios = Arrays.asList(ratios);
      this.numberOfRoutes = numberOfRoutes;
    }
  }
}
