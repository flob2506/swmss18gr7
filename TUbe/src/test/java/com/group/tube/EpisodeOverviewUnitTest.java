package com.group.tube;


import android.widget.ListView;

import com.group.tube.ArrayAdapter.EpisodeArrayAdapter;
import com.group.tube.Models.Episodes;
import com.group.tube.utils.Utils;
import com.thoughtworks.xstream.mapper.Mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class EpisodeOverviewUnitTest {
    private static final String FIRST_EPISODE_ID = UUID.randomUUID().toString();
    private EpisodesOverviewActivity activity;
    private ListView listView;
    private EpisodeArrayAdapter arrayAdapter;
    private ArrayList<Episodes> episodes;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(EpisodesOverviewActivity.class);
        listView = activity.findViewById(R.id.listViewEpisodes);
        episodes = new ArrayList<>();
        episodes.add(new Episodes(FIRST_EPISODE_ID,"Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at", Utils.getDate("2013-11-14T15:15:00Z")));
        episodes.add(new Episodes("i bims die ID2","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at", Utils.getDate("2013-11-14T15:15:00Z")));
        episodes.add(new Episodes("i bims die ID3","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at", Utils.getDate("2013-11-14T15:15:00Z")));
        episodes.add(new Episodes("i bims die ID4","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at", Utils.getDate("2013-11-14T15:15:00Z")));
        episodes.add(new Episodes("i bims die ID5","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at", Utils.getDate("2013-11-14T15:15:00Z")));
        arrayAdapter = new EpisodeArrayAdapter(activity, episodes);
        listView.setAdapter(arrayAdapter);
    }

    @Test
    public void verifyAdapterIsEpisodeAdapter()
    {
        assertTrue(listView.getAdapter() instanceof EpisodeArrayAdapter);
    }

    @Test
    public void verifyAdapterFilled()
    {
        assertTrue(listView.getAdapter().getCount() == episodes.size());
    }

    @Test
    public void verifyAdapterSameItems()
    {
        for(int i = 0; i < episodes.size(); i++) {
            assertEquals(episodes.get(i), listView.getAdapter().getItem(i));
        }
    }

    @Test
    public void verifyAdapterFirstItemId()
    {
        assertEquals(episodes.get(0).getId(), FIRST_EPISODE_ID);
    }

    @Test
    public void verifyAdapterItemTitleChanges()
    {
        for(Episodes episode : episodes) {
            episode.setEpisode_title(UUID.randomUUID().toString());
        }
        for(int i = 0; i < episodes.size(); i++) {
            assertEquals(episodes.get(i).getEpisode_title(), ((Episodes)listView.getAdapter().getItem(i)).getEpisode_title());
        }
    }

    @Test
    public void verifyDate()
    {
        Date date = Utils.getDate("20123.0230.3304e");
        assert(date == null);
    }

}