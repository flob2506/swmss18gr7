package com.group.tube;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.group.tube.ArrayAdapter.EpisodeArrayAdapter;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;

import junit.framework.Assert;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.group.tube.CoursesOverviewActivity.EXTRA_COURSE_OBJECT;
import static java.lang.Thread.sleep;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EpisodeOverviewUITest {

    private ListView episodeListView;
    private EpisodeArrayAdapter arrayAdapter;
    private ArrayList<Episode> episodes;
    private Course course;

    @Rule
    public IntentsTestRule<EpisodesOverviewActivity> episodesOverviewActivityTestRule =
            new IntentsTestRule<>(EpisodesOverviewActivity.class, false, false);


    @Before
    public void setUp() {
        final Intent intent = new Intent();
        Bundle bundle = new Bundle();
        course = TestDataGenerator.getCourse();
        bundle.putSerializable(EXTRA_COURSE_OBJECT, course);
        intent.putExtras(bundle);
        episodesOverviewActivityTestRule.launchActivity(intent);

        episodeListView = episodesOverviewActivityTestRule.getActivity().findViewById(R.id.listViewEpisodes);
        episodes = TestDataGenerator.getRandomEpisodeList();
        arrayAdapter = new EpisodeArrayAdapter(episodesOverviewActivityTestRule.getActivity(), episodes);
        episodesOverviewActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                episodeListView.setAdapter(arrayAdapter);
            }
        });

    }

    @Test
    public void titleSetTest() {
        assertEquals(course.getCourseTitle(), episodesOverviewActivityTestRule.getActivity().getTitle());
    }

    @Test
    public void imageViewIsShown() {
        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).atPosition(0)
                .onChildView(withId(R.id.imageViewThumbnailEpisode)).check(matches(isDisplayed()));
    }

    @Test
    public void imageViewHasChanged() throws InterruptedException {
        sleep(1000);

        ImageView firstImageView = episodesOverviewActivityTestRule.getActivity().findViewById(R.id.imageViewThumbnailEpisode);
        assertNotSame(firstImageView.getDrawable(), ContextCompat.getDrawable(episodesOverviewActivityTestRule.getActivity(), android.R.drawable.presence_video_busy));
    }

    @Test
    public void verifyAdapterIsEpisodeAdapter()
    {
        episodesOverviewActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertTrue(episodeListView.getAdapter() instanceof EpisodeArrayAdapter);
            }
        });
    }

    @Test
    public void verifyAdapterFilled()
    {
        episodesOverviewActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertTrue(episodeListView.getAdapter().getCount() == episodes.size());
            }
        });
    }

    @Test
    public void verifyAdapterSameItems()
    {
        episodesOverviewActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < episodes.size(); i++) {
                    assertEquals(episodes.get(i), episodeListView.getAdapter().getItem(i));
                }
            }
        });
    }

    @Test
    public void verifyAdapterItemTitleChanges()
    {
        for(Episode episode : episodes) {
            episode.setEpisodeTitle(UUID.randomUUID().toString());
        }
        episodesOverviewActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < episodes.size(); i++) {
                    assertEquals(episodes.get(i).getEpisodeTitle(), ((Episode)episodeListView.getAdapter().getItem(i)).getEpisodeTitle());
                }
            }
        });
    }

}