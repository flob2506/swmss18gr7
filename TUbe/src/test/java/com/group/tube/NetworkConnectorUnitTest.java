package com.group.tube;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.utils.TestDataGenerator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
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
                assertTrue(response.size() > 0);
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

        Course course = TestDataGenerator.getCourse();
        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
        networkConnector.loadEpisodesOfCourse(new AsyncResponse<ArrayList<Episode>>() {
            @Override
            public void processFinish(ArrayList<Episode> response) {
                assertTrue(response.size() > 0);
                signal.countDown();
            }

            @Override
            public void handleProcessException(Exception e) {
                fail("Exception mustn't be thrown");
            }
        }, course.getId());
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
