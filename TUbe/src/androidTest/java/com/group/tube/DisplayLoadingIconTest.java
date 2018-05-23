package com.group.tube;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DisplayLoadingIconTest {


    @Rule
    public ActivityTestRule<CoursesOverviewActivity> mCoursesOverviewActivity = new ActivityTestRule<>(
            CoursesOverviewActivity.class);

    @Test
    public void checkIfLoadingIconCoursesAppears() {
        onView(withId(R.id.loadingIconCourses)).check(matches(not(isDisplayed())));
    }

    @Test
    public void checkIfLoadingIconEpisodesAppears() {
        onView(withId(R.id.loadingIconCourses)).check(matches(not(isDisplayed())));
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).atPosition(0).perform(click());
        onView(withId(R.id.loadingIconEpisodes)).check(matches(not(isDisplayed())));
    }
}