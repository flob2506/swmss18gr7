package com.group.tube;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.group.tube.Models.Course;
import com.group.tube.utils.Utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchInCourseListUITest {


    @Rule
    public ActivityTestRule<CoursesOverviewActivity> mCoursesOverviewActivity = new ActivityTestRule<>(
            CoursesOverviewActivity.class);

    @Test
    public void searchViewIsDisplayed() throws InterruptedException {
        onView(withId(R.id.item_search)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfSearchIsWorking() throws InterruptedException {
        onView(withId(R.id.item_search)).perform(click());
       // onView(withId(R.id.item_search)).perform(typeText("Test search"));
    }
}