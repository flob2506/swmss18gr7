package com.group.tube;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.ArrayAdapter.EpisodeArrayAdapter;
import com.group.tube.List.FavouriteList;
import com.group.tube.List.WatchLaterList;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.utils.LocalStorageUtils;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;

import junit.framework.Assert;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.group.tube.CoursesOverviewActivity.EXTRA_COURSE_OBJECT;
import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddEpisodeToWatchLaterUITest {

    private ListView episodeListView;

    @Rule
    public ActivityTestRule<CoursesOverviewActivity> mActivityRule = new ActivityTestRule<>(CoursesOverviewActivity.class);
    public IntentsTestRule<EpisodesOverviewActivity> episodesOverviewActivityTestRule =
            new IntentsTestRule<>(EpisodesOverviewActivity.class, false, false);

    @Before
    public void setup(){
        final Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Course course = TestDataGenerator.getCourse();
        bundle.putSerializable(EXTRA_COURSE_OBJECT, course);
        intent.putExtras(bundle);
        episodesOverviewActivityTestRule.launchActivity(intent);
        episodeListView = episodesOverviewActivityTestRule.getActivity().findViewById(R.id.listViewEpisodes);

        LocalStorageUtils.writeListToFile(mActivityRule.getActivity(), new LinkedHashSet<String>(), LocalStorageUtils.FILE_NAME_WATCH_LATER_LIST);
        WatchLaterList.getInstance().overwrite(new LinkedHashSet<String>());
    }

    @After
    public  void teardown() {
        Intents.release();
    }


    @Test
    public void checkIfItemDisplaysCorrectText() throws InterruptedException{
        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).onChildView(withId(R.id.imageViewEpisodeMore)).atPosition(0).perform(click());
        onView(withId(R.id.episodeOptionBarDialog)).check(matches(isDisplayed()));
        onView(withText("Add to Watch later list")).check(matches(isDisplayed()));
        onView(withId(R.id.episodeOptionBarDialog)).check(matches(isDisplayed()));
        onView(withId(R.id.watchLaterLinearLayout)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).onChildView(withId(R.id.imageViewEpisodeMore)).atPosition(0).perform(click());
        onView(withId(R.id.episodeOptionBarDialog)).check(matches(isDisplayed()));
        onView(withText("Remove from Watch later list")).check(matches(isDisplayed()));

        onView(withId(R.id.watchLaterLinearLayout)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).onChildView(withId(R.id.imageViewEpisodeMore)).atPosition(0).perform(click());
        onView(withId(R.id.episodeOptionBarDialog)).check(matches(isDisplayed()));
        onView(withText("Add to Watch later list")).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfEpisodeIsSaved() throws InterruptedException{
        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).onChildView(withId(R.id.imageViewEpisodeMore)).atPosition(0).perform(click());
        onView(withId(R.id.episodeOptionBarDialog)).check(matches(isDisplayed()));
        onView(withText("Add to Watch later list")).check(matches(isDisplayed()));
        onView(withId(R.id.episodeOptionBarDialog)).check(matches(isDisplayed()));
        onView(withId(R.id.watchLaterLinearLayout)).perform(click());

        Intent intent = episodesOverviewActivityTestRule.getActivity().getIntent();
        episodesOverviewActivityTestRule.finishActivity();
        Intent intentMActivity = mActivityRule.getActivity().getIntent();
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(intentMActivity);

        episodesOverviewActivityTestRule.launchActivity(intent);

        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).onChildView(withId(R.id.imageViewEpisodeMore)).atPosition(0).perform(click());
        onView(withId(R.id.episodeOptionBarDialog)).check(matches(isDisplayed()));
        onView(withText("Remove from Watch later list")).check(matches(isDisplayed()));
        onView(withId(R.id.watchLaterLinearLayout)).perform(click());

    }

    @Test
    public void verifyFileCreated() throws InterruptedException {
        LocalStorageUtils.writeWatchLaterListToFile(mActivityRule.getActivity());
        File file = mActivityRule.getActivity().getFileStreamPath(LocalStorageUtils.FILE_NAME_WATCH_LATER_LIST);
        Assert.assertTrue(file.exists());
    }
}