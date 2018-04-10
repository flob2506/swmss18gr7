package com.group.tube;

import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkTask;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class NetworkConnectorUnitTest {
    private NetworkTask networkTask = new NetworkTask();

    @Test
    public void networkConnector_returnsString() throws Exception {
            final CountDownLatch signal = new CountDownLatch(1);

        this.networkTask.setResponseHandler(new AsyncResponse<String>(){

            @Override
            public void processFinish(String jsonResponse){
                signal.countDown();
                assertFalse(jsonResponse.isEmpty());
            }
        });
        this.networkTask.execute("https://baconipsum.com/api/?type=meat-and-filler");
        signal.await();

    }
}