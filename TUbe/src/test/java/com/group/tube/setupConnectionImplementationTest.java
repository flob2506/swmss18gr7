package com.group.tube;

import com.group.tube.Models.Episode;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

public class setupConnectionImplementationTest {

    @Test
    public void setup_connection_test() throws Exception {

        final CountDownLatch signal = new CountDownLatch(1);
        MainActivity mainActivity = new MainActivity();
        NetworkConnector networkConnector = new NetworkConnector();
        mainActivity.setNetworkConnector(networkConnector);

        String episodeId = "91ff68f1-7a0d-4655-8cec-643c3cb8b0ae";
        networkConnector.loadEpisode(new AsyncResponse<Episode>() {
            @Override
            public void processFinish(Episode episode) {
                // videoPlayer would be the video view
                // this.videoPlayer.showVideo(episode.getUrl();
                signal.countDown();

                assertEquals("https://tube.tugraz.at/static/mh_default_org/engage-player/" +
                                "8556615e-a07f-43cc-b119-a54bf52cc63e/" +
                                "8d941380-946b-4c4c-9148-10e666ca1e6d/track.mp4",
                        episode.getPresentationUrl());
            }
        }, episodeId);

        signal.await();
        System.out.println("Passed test.");
    }
}
