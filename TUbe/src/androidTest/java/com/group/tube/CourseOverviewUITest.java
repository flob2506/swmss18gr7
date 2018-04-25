package com.group.tube;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CourseOverviewUITest {


    @Rule
    public IntentsTestRule<CoursesOverviewActivity> intentsTestRule =
            new IntentsTestRule<>(CoursesOverviewActivity.class);

    @Test
    public void validateIntentSentToPackage() throws InterruptedException {
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).atPosition(0).perform(click());

        intended(hasExtras(hasEntry(equalTo(CoursesOverviewActivity.EXTRA_MESSAGE), equalTo("a8b53a40-ca6d-42d5-a018-a1c3a445c0b6"))));
    }
}