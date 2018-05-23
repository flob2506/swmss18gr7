package com.group.tube;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

public class NetworkConnectorUnitTest {

    @Test
    public void loadAllCourses() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
        networkConnector.loadAllCourses(new AsyncResponse<ArrayList<Course>>() {
            @Override
            public void processFinish(ArrayList<Course> response) {
                assertEquals("bcfa6470-b7c8-4d85-8678-89c6844b1660", response.get(3).getId());
                assertEquals("HS i13", response.get(7).getCourseTitle());
                signal.countDown();
            }

            @Override
            public void handleProcessException(Exception e) {
                fail("Exception mustn't be throw");
            }
        });
        signal.await();
    }




    @Test
    public void loadEpisodesOfCourse() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
        networkConnector.loadEpisodesOfCourse(new AsyncResponse<ArrayList<Episode>>() {
            @Override
            public void processFinish(ArrayList<Episode> response) {
                assertEquals("2a96ea26-1e1c-4ab8-8dee-d7ebeb076512", response.get(0).getId());
                assertEquals("Test Event", response.get(1).getEpisodeTitle());
                signal.countDown();
            }

            @Override
            public void handleProcessException(Exception e) {
                fail("Exception mustn't be thrown");
            }
        }, "bcfa6470-b7c8-4d85-8678-89c6844b1660");
        signal.await();
    }

    @Test
    public void loadEpisodesOfCourse_wrongCourseID() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
        networkConnector.loadEpisodesOfCourse(new AsyncResponse<ArrayList<Episode>>() {
            @Override
            public void processFinish(ArrayList<Episode> response) {
                assertEquals(0, response.size());
                signal.countDown();
            }

            @Override
            public void handleProcessException(Exception e) {
                fail("Exception mustn't be thrown");
            }
        }, "This-is-wrong");
        signal.await();
    }
}
