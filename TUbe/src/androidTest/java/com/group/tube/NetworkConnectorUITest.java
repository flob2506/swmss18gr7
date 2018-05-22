package com.group.tube;

import android.graphics.drawable.Drawable;

import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NetworkConnectorUITest {


    //needs to be UI-Test due to BitMapFactory not being mocked for Espresso
    @Test
    public void loadTestDrawable() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        //calculated with real tube-logo.png
        final int hashCodeTUbeLogo = 64879524;

        final NetworkConnector networkConnector = new NetworkConnector();

        networkConnector.downloadDrawable(new AsyncResponse<Drawable>() {
            @Override
            public void processFinish(Drawable response) {

                //Safety check to make sure hashcode correct
                assertNotEquals(hashCodeTUbeLogo + 1, response.hashCode());
                assertEquals(hashCodeTUbeLogo, response.hashCode());
                signal.countDown();
            }

            @Override
            public void handleProcessException(Exception e) {
                fail("Exception mustn't be thrown");
            }
        }, "https://tube.tugraz.at/paella/ui/img/tube-logo.png");
        signal.await();
    }
}
