package unitTests;


import com.group.tube.Comparators.DateSortComparator;
import com.group.tube.Models.Episode;
import com.group.tube.utils.TestDataGenerator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static junit.framework.Assert.assertEquals;

public class SortEpisodeByDateUnitTest {
    private ArrayList<Episode> episodes;

    @Before
    public void setUp() {
        episodes = TestDataGenerator.getRandomEpisodeList();
    }

    @Test
    public void verifyListSorted() {
        Collections.sort(episodes, new DateSortComparator());

        Episode prevEpisode = null;
        for (Episode episode : episodes) {
            if (prevEpisode != null) {
                assertEquals(true, prevEpisode.getDate().compareTo(episode.getDate()) <= 0);
            }
            prevEpisode = episode;
        }
    }
}
