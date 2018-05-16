package com.group.tube;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.group.tube.ArrayAdapter.EpisodeArrayAdapter;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static com.group.tube.CoursesOverviewActivity.EXTRA_COURSE_OBJECT;
import static org.junit.Assert.*;

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)

public class EpisodeOverviewUnitTest {
    private EpisodesOverviewActivity activity;
    private ListView listView;
    private EpisodeArrayAdapter arrayAdapter;
    private ArrayList<Episode> episodes;

    @Before
    public void setUp() {
        Course course = new Course();
        String courseTitle = "143.700 14S Architekturtheorie heute";
        String courseID = "e1ad5c57-2e78-4933-99cb-645ab5b865c5";

        final Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_COURSE_OBJECT, course);
        intent.putExtras(bundle);



        activity = Robolectric.buildActivity(EpisodesOverviewActivity.class, intent).create().get();
        listView = activity.findViewById(R.id.listViewEpisodes);
        episodes = TestDataGenerator.getRandomEpisodeList();
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
    public void verifyAdapterItemTitleChanges()
    {
        for(Episode episode : episodes) {
            episode.setEpisodeTitle(UUID.randomUUID().toString());
        }
        for(int i = 0; i < episodes.size(); i++) {
            assertEquals(episodes.get(i).getEpisodeTitle(), ((Episode)listView.getAdapter().getItem(i)).getEpisodeTitle());
        }
    }


}