package com.group.tube;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityNavigationUITest {


    @Rule
    public IntentsTestRule<CoursesOverviewActivity> intentsTestRule =
            new IntentsTestRule<>(CoursesOverviewActivity.class);

    @Test
    public void validateIntentSentToPackage() throws InterruptedException {
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).atPosition(0).perform(click());

        intended(hasExtras(hasEntry(equalTo(CoursesOverviewActivity.EXTRA_COURSE_OBJECT), equalTo("a8b53a40-ca6d-42d5-a018-a1c3a445c0b6"))));

        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).atPosition(0).perform(click());

        intended(hasExtras(hasEntry(equalTo(EpisodesOverviewActivity.EXTRA_EPISODE_ID), equalTo("a8b53a40-ca6d-42d5-a018-a1c3a445c0b6"))));
    }
}