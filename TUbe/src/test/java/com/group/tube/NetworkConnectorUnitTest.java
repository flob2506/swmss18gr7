package com.group.tube;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.EpisodesAsyncResponse;
import com.group.tube.networking.NetworkConnector;

import org.json.JSONException;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NetworkConnectorUnitTest {

    @Test
    public void loadAllCourses() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword("tube-mobile", "J8Mz4ftVNEZ54Wo6");
        networkConnector.loadAllCourses(new AsyncResponse<ArrayList<Course>>() {
            @Override
            public void processFinish(ArrayList<Course> response) {
                assertEquals("bcfa6470-b7c8-4d85-8678-89c6844b1660", response.get(3).getId());
                assertEquals("HS i13", response.get(7).getCourseTitle());
                signal.countDown();
            }
        });
        signal.await();
    }

    /*@Test(expected = ParseException.class)
    public void loadEpisodesOfCourse_faultyResponseHandler() throws ParseException {
        new EpisodesAsyncResponse(null).processFinish("blablabla");
    }*/


    @Test
    public void loadEpisodesOfCourse() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword("tube-mobile", "J8Mz4ftVNEZ54Wo6");
        networkConnector.loadEpisodesOfCourse(new AsyncResponse<ArrayList<Episode>>() {
            @Override
            public void processFinish(ArrayList<Episode> response) {
                assertEquals("2a96ea26-1e1c-4ab8-8dee-d7ebeb076512", response.get(0).getId());
                assertEquals("Test Event", response.get(1).getEpisodeTitle());
                signal.countDown();
            }
        }, "bcfa6470-b7c8-4d85-8678-89c6844b1660");
        signal.await();
    }

    @Test
    public void loadEpisodesOfCourse_wrongCourseID() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword("tube-mobile", "J8Mz4ftVNEZ54Wo6");
        networkConnector.loadEpisodesOfCourse(new AsyncResponse<ArrayList<Episode>>() {
            @Override
            public void processFinish(ArrayList<Episode> response) {
                assertEquals(0, response.size());
                signal.countDown();
            }
        }, "This-is-wrong");
        signal.await();
    }
}
