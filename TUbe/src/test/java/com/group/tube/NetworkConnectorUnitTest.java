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

    @Test
    public void loadMediaOfEpisode_test() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
        networkConnector.loadMediaOfEpisode(new AsyncResponse<String>() {
            @Override
            public void processFinish(String response) {
                assertEquals(mediaResponse, response);
                signal.countDown();
            }

            @Override
            public void handleProcessException(Exception e) {
                fail("Exception mustn't be thrown");
            }
        }, "0ebba909-dfcb-4bb1-b809-c49a518a8c40");
        signal.await();
    }

    final String mediaResponse = "[{\"metadata\":[],\"attachments\":[],\"channel\":\"engage-player\",\"id\":\"86d1b382-a8f5-40a2-bbbf-df4aad8e06d1\",\"media\":[],\"mediatype\":\"text\\/html\",\"url\":\"https:\\/\\/tube-test.tugraz.at\\/paella\\/ui\\/watch.html?id=0ebba909-dfcb-4bb1-b809-c49a518a8c40\"},{\"metadata\":[{\"flavor\":\"dublincore\\/episode\",\"size\":-1,\"checksum\":\"\",\"id\":\"d22b183c-3e2c-40bd-8489-58b9d1c6c681\",\"mediatype\":\"text\\/xml\",\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/fea4cd0d-6c8e-4e3a-a17a-ae9d0b571734\\/dublincore.xml\",\"tags\":[\"archive\",\"engage-download\"]},{\"flavor\":\"dublincore\\/series\",\"size\":-1,\"checksum\":\"9162f6b3f10b762581e984f7fccd6f52 (md5)\",\"id\":\"5f14fe84-9c29-4a10-9cbe-298d018e1d56\",\"mediatype\":\"text\\/xml\",\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/6c0d49aa-4e7f-4dae-a25b-ecbf94471b33\\/dublincore.xml\",\"tags\":[\"archive\",\"engage-download\"]}],\"attachments\":[{\"flavor\":\"presenter\\/timeline+preview\",\"ref\":\"track:track-4\",\"size\":0,\"checksum\":\"\",\"id\":\"c573438e-ec26-4ffe-91b9-1c5c65c3b672\",\"mediatype\":\"image\\/png\",\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/b1671c43-bb9b-4b1f-968a-385821f55a00\\/bunny_7e21b5f6_e970_4eeb_a417_7a4be6010a85_timelinepreviews.png\",\"tags\":[\"engage-download\"]},{\"flavor\":\"presenter\\/search+preview\",\"ref\":\"track:track-4\",\"size\":0,\"checksum\":\"\",\"id\":\"a45d4753-6676-4622-808a-2df354a78af3\",\"mediatype\":\"image\\/jpeg\",\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/attachment-5\\/bunny_1_000s_search.jpg\",\"tags\":[\"engage-download\"]},{\"flavor\":\"presenter\\/player+preview\",\"ref\":\"\",\"size\":0,\"checksum\":\"\",\"id\":\"b4628a32-11ee-4c93-aa63-25cadfa9cafa\",\"mediatype\":\"image\\/png\",\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/253952ee-109d-462b-a0b9-1f0d8950ddf8\\/coverimage.png\",\"tags\":[\"archive\",\"engage-download\"]}],\"channel\":\"api\",\"id\":\"88d30de0-b799-4b3b-a59f-679a4563d5fb\",\"media\":[{\"has_audio\":true,\"framerate\":25.0,\"description\":\"\",\"bitrate\":329857.0,\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/16ddf942-8746-475b-99ec-76c94ee7f3e6\\/bunny.mp4\",\"has_video\":true,\"tags\":[\"360p-quality\",\"engage-download\",\"engage-streaming\"],\"flavor\":\"presenter\\/delivery\",\"duration\":71600,\"size\":-1,\"framecount\":1790,\"checksum\":\"cf9afc05248bcae9b18ce291284e0948 (md5)\",\"width\":640,\"id\":\"7cd8b9b5-5021-45c3-b006-97c8791cac02\",\"mediatype\":\"video\\/mp4\",\"height\":360},{\"has_audio\":true,\"framerate\":25.0,\"description\":\"\",\"bitrate\":790676.0,\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/0e51e9fc-011b-4ade-a540-c91390e0d4c0\\/bunny.mp4\",\"has_video\":true,\"tags\":[\"480p-quality\",\"engage-download\",\"engage-streaming\"],\"flavor\":\"presenter\\/delivery\",\"duration\":71600,\"size\":-1,\"framecount\":1790,\"checksum\":\"5549495db09a83c31bfb66e5e0fc5bcc (md5)\",\"width\":854,\"id\":\"b6a8bcad-a6f2-4f48-9235-ac72356938ae\",\"mediatype\":\"video\\/mp4\",\"height\":480},{\"has_audio\":true,\"framerate\":25.0,\"description\":\"\",\"bitrate\":1211168.0,\"url\":\"https:\\/\\/tube-test.tugraz.at\\/static\\/mh_default_org\\/api\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\\/33c3ebcd-278c-4bb9-bc10-2ef9df32a6c0\\/bunny.mp4\",\"has_video\":true,\"tags\":[\"720p-quality\",\"engage-download\",\"engage-streaming\"],\"flavor\":\"presenter\\/delivery\",\"duration\":71600,\"size\":-1,\"framecount\":1790,\"checksum\":\"ede14a8dca414e3007a8e58990b73e08 (md5)\",\"width\":1280,\"id\":\"eee1b944-ca13-4aed-a732-e67562db533a\",\"mediatype\":\"video\\/mp4\",\"height\":720}],\"mediatype\":\"application\\/json\",\"url\":\"http:\\/\\/localhost:8080\\/api\\/events\\/0ebba909-dfcb-4bb1-b809-c49a518a8c40\"}]";
}
