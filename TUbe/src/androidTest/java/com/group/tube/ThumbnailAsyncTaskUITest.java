package com.group.tube;

import android.graphics.drawable.Drawable;

import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.ThumbnailAsyncTask;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

public class ThumbnailAsyncTaskUITest {


    //needs to be UI-Test due to BitMapFactory not being mocked for Espresso
    @Test
    public void loadTestDrawable() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        final ThumbnailAsyncTask thumbnailAsyncTask = new ThumbnailAsyncTask();

        thumbnailAsyncTask.setResponseHandler(new AsyncResponse<Drawable>() {
            @Override
            public void processFinish(Drawable response) {
                assertNotNull(response);

                signal.countDown();
            }

            @Override
            public void handleProcessException(Exception e) {
                fail("Exception mustn't be thrown");
            }
        });

        thumbnailAsyncTask.execute("https://tube.tugraz.at/paella/ui/img/tube-logo.png");
        signal.await();
    }

    @Test
    public void loadTestDrawableWrongURL() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        final ThumbnailAsyncTask thumbnailAsyncTask = new ThumbnailAsyncTask();

        thumbnailAsyncTask.setResponseHandler(new AsyncResponse<Drawable>() {
            @Override
            public void processFinish(Drawable response) {
                fail("There must be an exception!");
            }

            @Override
            public void handleProcessException(Exception e) {
                //should throw fileNotFoundException
                assertEquals(java.io.FileNotFoundException.class, e.getClass());
                signal.countDown();
            }
        });
        thumbnailAsyncTask.execute("https://tube.tugraz.at/paella/ui/img/not-existing.png");
        signal.await();
    }
}
