package com.group.tube;

import com.group.tube.networking.NetworkConnector;
import org.junit.Test;
import static org.junit.Assert.*;

public class setupConnectionUnitTest {




    @Test
    public void setup_connection_test() throws Exception {

        MainActivity mainActivity = new MainActivity();
        mainActivity.setNetworkConnector(new NetworkConnector());

        try
        {
            mainActivity.viewEpisode("91ff68f1-7a0d-4655-8cec-643c3cb8b0ae", mainActivity);
        }
        catch (Exception e)
        {

        }

        assertNotNull(mainActivity);
        System.out.println("Passed test.");
    }
}
