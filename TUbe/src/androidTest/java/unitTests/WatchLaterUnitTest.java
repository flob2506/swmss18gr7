package unitTests;

import com.group.tube.Models.Episode;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

public class WatchLaterUnitTest {
    @Test
    public void checkIdFilter() throws Exception {
        ArrayList<Episode> source = TestDataGenerator.getRandomEpisodeList();
        verifyEpisodesFiltered(source);
    }

    @Test
    public void loadAllEpisodes() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
        networkConnector.loadAllEpisodes(new AsyncResponse<ArrayList<Episode>>() {
            @Override
            public void processFinish(ArrayList<Episode> response) {
                assertTrue(response.size() > 0);
                verifyEpisodesFiltered(response);
                signal.countDown();
            }

            @Override
            public void handleProcessException(Exception e) {
                fail("Exception mustn't be thrown");
            }
        });
        signal.await();
    }

    private void verifyEpisodesFiltered(ArrayList<Episode> source) {
        ArrayList<Episode> episodes = new ArrayList<>();
        Set<String> episodeIds = new LinkedHashSet<>();
        for (int i = 0; i < source.size(); i++) {
            if (i % 2 == 0) {
                episodeIds.add(source.get(i).getId());
            }
        }
        Utils.addToEpisodeListIfInSet(source, episodes, episodeIds);
        assertEquals(episodeIds.size(), episodes.size());
        for (Episode episode : episodes) {
            assertTrue(episodeIds.contains(episode.getId()));
            assertTrue(source.contains(episode));
        }
    }

}