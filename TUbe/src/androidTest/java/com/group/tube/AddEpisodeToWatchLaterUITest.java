package com.group.tube;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.Models.Course;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import java.util.ArrayList;
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
    public IntentsTestRule<EpisodesOverviewActivity> episodesOverviewActivityTestRule =
            new IntentsTestRule<>(EpisodesOverviewActivity.class, false, false);

    @Before
    public void titleSetTest(){
        final Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Course course = TestDataGenerator.getCourse();
        bundle.putSerializable(EXTRA_COURSE_OBJECT, course);
        intent.putExtras(bundle);
        episodesOverviewActivityTestRule.launchActivity(intent);
        episodeListView = episodesOverviewActivityTestRule.getActivity().findViewById(R.id.listViewEpisodes);
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
    }
}