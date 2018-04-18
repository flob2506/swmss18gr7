package com.group.tube;


import com.group.tube.Comparators.DateSortComparator;
import com.group.tube.Models.Episodes;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class SortEpisodeByDateUnitTest {
    private ArrayList<Episodes> episodes;

    @Before
    public void setUp()
    {
        episodes = TestDataGenerator.getRandomEpisodeList();
    }

    @Test
    public void verifyListSorted()
    {
        Collections.sort(episodes, new DateSortComparator());

        Episodes prevEpisode = null;
        for (Episodes episode : episodes) {
            if (prevEpisode != null)
                assert(prevEpisode.getDate().compareTo(episode.getDate()) <= 0);
            prevEpisode = episode;
        }
    }
}
