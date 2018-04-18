package com.group.tube.utils;

import android.widget.ListView;

import com.group.tube.ArrayAdapter.EpisodeArrayAdapter;
import com.group.tube.EpisodesOverviewActivity;
import com.group.tube.Models.Episodes;
import com.group.tube.R;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Date;
import java.util.UUID;

public final class TestDataGenerator {
    public static ArrayList<Episodes> getRandomEpisodeList()
    {
        ArrayList<Episodes> episodes = new ArrayList<>();
        int numOfEpisodes = ThreadLocalRandom.current().nextInt(10, 20);
        for (int i = 0; i < numOfEpisodes; i++) {
            episodes.add(new Episodes("i bims die ID " +  UUID.randomUUID().toString(),"Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at", getRandomDate()));
        }


        return episodes;

    }

    public static Date getRandomDate()
    {
         return new Date( ThreadLocalRandom.current().nextInt(100, 119), // java shit adds 1900
            ThreadLocalRandom.current().nextInt(1, 13),
            ThreadLocalRandom.current().nextInt(1, 29),
            ThreadLocalRandom.current().nextInt(0, 25),
            ThreadLocalRandom.current().nextInt(0, 61),
            ThreadLocalRandom.current().nextInt(0, 61)
        );
    }
}
