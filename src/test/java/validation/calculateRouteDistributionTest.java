package validation;

import model.RequestConfig;
import model.RequestConfigList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Utilities;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class calculateRouteDistributionTest {

@Parameterized.Parameter(0)
    public RequestConfigList requestConfigList;
@Parameterized.Parameter(1)
    public int numberOfRoutes;
@Parameterized.Parameter(2)
    public List<Integer> result;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        RequestConfigList requestConfigList= new RequestConfigList();
        RequestConfig requestConfig= new RequestConfig();
        List<RequestConfig> req= new ArrayList<>();
        requestConfig.setRatio(50);
        req.add(requestConfig);
        RequestConfig requestConfig2= new RequestConfig();
        requestConfig2.setRatio(50);
        req.add(requestConfig2);
        requestConfigList.setConfigList(req);

            List<Integer> results= new ArrayList<>();
            results.add(3);
            results.add(3);
        Object[][] data = new Object[][] {{requestConfigList,6,results}};
        return Arrays.asList(data);
    }
    @Test
    public void calculateRouteDistributionTests(){
        assertEquals("Result",result, Utilities.calculateRouteDistribution(requestConfigList,numberOfRoutes));

    }
}
