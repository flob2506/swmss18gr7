package com.group.tube;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.core.internal.deps.guava.collect.Iterables;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.util.Log;
import android.widget.ListView;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;
import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityNavigationUITest {


    @Rule
    public IntentsTestRule<CoursesOverviewActivity> intentsTestRule =
            new IntentsTestRule<>(CoursesOverviewActivity.class);

    @Test
    public void validateIntentSentToPackage() throws Throwable {
        //Construct to wait on loaded list
        long startTime = System.currentTimeMillis();
        long maximum_waiting_time = 2000;
        while (!intentsTestRule.getActivity().courseListLoaded) {
            sleep(50);
            if (System.currentTimeMillis() - startTime >= maximum_waiting_time) {
                fail();
            }
        }

        ListView courseListView = intentsTestRule.getActivity().findViewById(R.id.listViewCourses);
        Course course = (Course) courseListView.getItemAtPosition(0);

        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).atPosition(0).perform(click());

        intended(hasExtras(hasEntry(equalTo(CoursesOverviewActivity.EXTRA_COURSE_OBJECT), equalTo((Serializable) course))));


        Activity newActivity = getActivityInstance();

        ListView episodeListView = newActivity.findViewById(R.id.listViewEpisodes);
        Episode episode = (Episode) episodeListView.getItemAtPosition(0);

        onData(anything()).inAdapterView(withId(R.id.listViewEpisodes)).atPosition(0).perform(click());

        intended(hasExtras(hasEntry(equalTo(EpisodesOverviewActivity.EXTRA_EPISODE_ID), equalTo(episode.getId()))));
    }

    private Activity getActivityInstance() {
        final Activity[] currentActivity = {null};

        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                Iterator<Activity> it = resumedActivity.iterator();
                currentActivity[0] = it.next();
            }
        });

        return currentActivity[0];
    }
}