package com.group.tube;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;

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
import static com.group.tube.CoursesOverviewActivity.EXTRA_COURSE_OBJECT;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EpisodeOverviewUITest {
    @Rule
    public ActivityTestRule<EpisodesOverviewActivity> episodesOverviewActivityTestRule =
            new ActivityTestRule<>(EpisodesOverviewActivity.class, false, false);

    @Test
    public void titleSetTest(){
        final Intent intent = new Intent();
        Bundle bundle = new Bundle();

        Course course = new Course();
        String courseTitle = "143.700 14S Architekturtheorie heute";
        course.setCourseTitle(courseTitle);

        bundle.putSerializable(EXTRA_COURSE_OBJECT, course);
        intent.putExtras(bundle);

        episodesOverviewActivityTestRule.launchActivity(intent);

        assertEquals(courseTitle, episodesOverviewActivityTestRule.getActivity().getTitle());
    }
}