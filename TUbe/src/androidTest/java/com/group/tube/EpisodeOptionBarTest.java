package com.group.tube;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.group.tube.CoursesOverviewActivity.EXTRA_COURSE_OBJECT;
import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EpisodeOptionBarTest {
    @Rule
    public IntentsTestRule<EpisodesOverviewActivity> episodesOverviewActivityTestRule =
            new IntentsTestRule<>(EpisodesOverviewActivity.class, false, false);


    @Before
    public void titleSetTest(){
        final Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Course course = new Course();
        // TODO set course id
        String courseTitle = "143.700 14S Architekturtheorie heute";
        course.setCourseTitle(courseTitle);
        bundle.putSerializable(EXTRA_COURSE_OBJECT, course);
        intent.putExtras(bundle);
        episodesOverviewActivityTestRule.launchActivity(intent);
    }

    @Test
    public void dialogOpensAfterClick() {
        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).onChildView(withId(R.id.imageViewEpisodeMore)).atPosition(0).perform(click());
        onView(withId(R.id.episodeOptionBarDialog)).check(matches(isDisplayed()));
    }

    @Test
    public void verifyClipboard() {
        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).onChildView(withId(R.id.imageViewEpisodeMore)).atPosition(0).perform(click());
        onView(withId(R.id.layoutEpisodeCopy)).perform(click());
        onView(withId(R.id.listViewEpisodes)).check(matches(withClipboard()));
    }

    @Test
    public void verifyShare() throws InterruptedException {
        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).onChildView(withId(R.id.imageViewEpisodeMore)).atPosition(0).perform(click());
        onView(withId(R.id.layoutEpisodeShare)).perform(click());
        intended(hasExtras(hasEntry(equalTo(Intent.EXTRA_TEXT), equalTo("franz strohmeier"))));
        //onView(withText("Copy to clipboard")).perform(click());
        //onView(withId(R.id.listViewEpisodes)).check(matches(withClipboard()));

    }

    public Matcher<View> withClipboard () {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely (final View view) {
                ClipboardManager clipboard = (ClipboardManager) episodesOverviewActivityTestRule.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                String clipContent = (String)clipboard.getPrimaryClip().getItemAt(0).coerceToText(episodesOverviewActivityTestRule.getActivity());
                String sharedContent = getShareContent(view);
                return clipContent.equals(sharedContent);
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("List doesn't have clicked indexes");
            }
        };
    }

    private String getShareContent(final View view)
    {
        ListView list = ((ListView) view);
        Episode episode = (Episode) list.getItemAtPosition(0);
        return episode.getPresenterUrl();
    }

}