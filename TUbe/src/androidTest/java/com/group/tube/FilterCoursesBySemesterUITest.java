package com.group.tube;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.VideoView;

import com.group.tube.Models.Course;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static java.util.EnumSet.allOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FilterCoursesBySemesterUITest {


    @Rule
    public ActivityTestRule<CoursesOverviewActivity> mCoursesOverviewActivity = new ActivityTestRule<>(
            CoursesOverviewActivity.class);


    @Test
    public void videoStartsAfterTimeOut() throws InterruptedException {
        onView(withId(R.id.imageViewFilterCourseList)).perform(click());
        onView(withText(R.string.filter_by_semester)).check(matches(isDisplayed()));
    }


}