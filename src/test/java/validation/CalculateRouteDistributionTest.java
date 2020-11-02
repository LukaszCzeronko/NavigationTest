package validation;

import lombok.Getter;
import lombok.Setter;
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

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CalculateRouteDistributionTest {

    @Parameterized.Parameter(0)
    public TestObject testObject;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data =
                new Object[][] {
                        {new TestObject(new Integer[] {0}, 5, new Integer[] {5})},
                        {new TestObject(new Integer[] {1}, 5, new Integer[] {5})},
                        {new TestObject(new Integer[] {50}, 5, new Integer[] {5})},
                        {new TestObject(new Integer[] {99}, 5, new Integer[] {5})},
                        {new TestObject(new Integer[] {100}, 5, new Integer[] {5})},
                        {new TestObject(new Integer[] {101}, 5, new Integer[] {5})},
                        {new TestObject(new Integer[] {50, 50}, 6, new Integer[] {3, 3})},
                        {new TestObject(new Integer[] {20, 80}, 10, new Integer[] {2, 8})},
                        {new TestObject(new Integer[] {0, 25}, 8, new Integer[] {6, 2})},
                        {new TestObject(new Integer[] {0, 0}, 6, new Integer[] {6, 0})},
                        {new TestObject(new Integer[] {100, 100}, 6, new Integer[] {6})},
                        {new TestObject(new Integer[] {0, 100}, 6, new Integer[] {0,6})},
                        {new TestObject(new Integer[] {0, 150}, 6, new Integer[] {0,6})},
                        {new TestObject(new Integer[] {50, 150}, 6, new Integer[] {3,3})},
                        {new TestObject(new Integer[] {200, 150}, 6, new Integer[] {6})},
                        {new TestObject(new Integer[] {90, 150}, 6, new Integer[] {5,1})},
                        {new TestObject(new Integer[] {95, 150}, 6, new Integer[] {5,1})},
                        {new TestObject(new Integer[] {82, 150}, 6, new Integer[] {4,2})},
                        {new TestObject(new Integer[] {0, 33}, 3, new Integer[] {3,0})},
                        {new TestObject(new Integer[] {33, 67}, 3, new Integer[] {1,2})},
                        {new TestObject(new Integer[] {10, 50,40}, 10, new Integer[] {1,5,4})},
                        {new TestObject(new Integer[] {100, 50,40}, 10, new Integer[] {10})},
                        {new TestObject(new Integer[] {0, 50,40}, 10, new Integer[] {1,5,4})},
                        {new TestObject(new Integer[] {10, 50,60}, 10, new Integer[] {1,5,4})},
                        {new TestObject(new Integer[] {0, 0,0}, 10, new Integer[] {10,0,0})},
                        {new TestObject(new Integer[] {1, 2,3}, 10, new Integer[] {10,0,0})},
                };
        return Arrays.asList(data);
    }

    @Test
    public void calculateRouteDistributionTests() {
        RequestConfigList requestConfigList = generateRequestConfigList(testObject);
        assertEquals(
                "Result",
                testObject.result,
                Utilities.calculateRouteDistribution(requestConfigList, testObject.numberOfRoutes));
    }

    private RequestConfigList generateRequestConfigList(TestObject testObject) {
        RequestConfigList requestConfigList = new RequestConfigList();
        List<RequestConfig> req = new ArrayList<>();
        for (Integer ratio : testObject.ratios) {
            RequestConfig requestConfig = new RequestConfig();
            requestConfig.setRatio(ratio);
            req.add(requestConfig);
        }
        requestConfigList.setConfigList(req);
        return requestConfigList;
    }

    @Getter
    @Setter
    static class TestObject {
        List<Integer> ratios;
        List<Integer> result;
        int numberOfRoutes;

        public TestObject(Integer[] ratios, int numberOfRoutes, Integer[] result) {
            this.ratios = Arrays.asList(ratios);
            this.result = Arrays.asList(result);
            this.numberOfRoutes = numberOfRoutes;
        }
    }
}
