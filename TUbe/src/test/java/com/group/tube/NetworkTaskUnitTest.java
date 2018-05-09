package com.group.tube;

import android.security.keystore.UserNotAuthenticatedException;

import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkTask;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;


public class NetworkTaskUnitTest {
    private NetworkTask networkTask = new NetworkTask();

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void networkConnector_returnsString() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        this.networkTask.setResponseHandler(new AsyncResponse<String>() {

            @Override
            public void processFinish(String jsonResponse) {
                assertFalse(jsonResponse.isEmpty());
                signal.countDown();
            }
        });

        this.networkTask.execute("https://baconipsum.com/api/?type=meat-and-filler");
        signal.await();
    }

    @Test
    public void networkConnector_credentialsWork() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        this.networkTask.setLoginAndPassword("tube-mobile", "J8Mz4ftVNEZ54Wo6");
        this.networkTask.setResponseHandler(new AsyncResponse<String>() {

            @Override
            public void processFinish(String jsonResponse) {
                assertFalse(jsonResponse.isEmpty());
                signal.countDown();
            }
        });

        this.networkTask.execute("https://tube-test.tugraz.at/api/series");
        signal.await();
    }

    @Test(expected = UserNotAuthenticatedException.class)
    public void networkConnector_credentialsDontWork() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        this.networkTask.setResponseHandler(new AsyncResponse<String>() {
            @Override
            public void processFinish(String jsonResponse) {
                signal.countDown();
            }
        });

        this.networkTask.execute("https://tube-test.tugraz.at/api/series");
        signal.await();
    }


    @Test
    public void networkConnector_wronghttp() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        this.networkTask.setResponseHandler(new AsyncResponse<String>() {

            @Override
            public void processFinish(String jsonResponse) {
                assertNull(jsonResponse);
                signal.countDown();
            }
        });
        this.networkTask.execute("https://notexistingsite.getId()ontexist.net/");
        signal.await();
    }
}