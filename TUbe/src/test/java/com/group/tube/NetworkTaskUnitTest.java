package com.group.tube;

import android.security.keystore.UserNotAuthenticatedException;

import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.networking.NetworkTask;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;


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

            @Override
            public void handleProcessException(Exception e) {
                fail("Exception mustn't be thrown");
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

            @Override
            public void handleProcessException(Exception e) {
                fail("Exception mustn't be thrown");
            }
        });

        this.networkTask.execute(NetworkConnector.TUBE_URL + "api/series");
        signal.await();
    }

    @Test
    public void networkConnector_credentialsDontWork() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        this.networkTask.setResponseHandler(new AsyncResponse<String>() {
            @Override
            public void processFinish(String jsonResponse) {
                fail("Exception mustn't be thrown");
            }

            @Override
            public void handleProcessException(Exception e) {
                signal.countDown();
            }
        });

        this.networkTask.execute(NetworkConnector.TUBE_URL + "api/series");
        signal.await();
    }


    @Test
    public void networkConnector_wronghttp() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        this.networkTask.setResponseHandler(new AsyncResponse<String>() {

            @Override
            public void processFinish(String jsonResponse) {
                fail("Exception must be thrown");
            }

            @Override
            public void handleProcessException(Exception e) {
                signal.countDown();
            }
        });
        this.networkTask.execute("https://notexistingsite.getId()ontexist.net/");
        // TODO await einschränken dann fail("jtime run out, exception wasn't rhotwen mustn't be thrown");
        signal.await();
    }
}