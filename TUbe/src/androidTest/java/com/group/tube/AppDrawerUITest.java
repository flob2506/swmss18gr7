package com.group.tube;

import android.app.Instrumentation;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.GravityCompat;
import android.support.test.espresso.contrib.*;
import android.view.Gravity;


import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class AppDrawerUITest {

    @Rule
    public ActivityTestRule<CoursesOverviewActivity> mActivityRule = new ActivityTestRule<>(
            CoursesOverviewActivity.class);
    public ActivityTestRule<EpisodesOverviewActivity> mEpisodesActivityRule = new ActivityTestRule<>(
            EpisodesOverviewActivity.class);

    @Test
    public void checkIfDrawerOpensOnButtonClick(){
        onView(withContentDescription("Home button")).perform(click());
        assertTrue(mActivityRule.getActivity().mDrawerLayout.isDrawerOpen(GravityCompat.START));
    }

    @Test
    public void checkIfDrawerOpensThroughSwiping(){
        onView(withId(R.id.drawer_layout)).perform(swipe(GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER_RIGHT));
        assertTrue(mActivityRule.getActivity().mDrawerLayout.isDrawerOpen(GravityCompat.START));
    }

    @Test
    public void checkIfDrawerClosesThroughSwiping(){
        onView(withId(R.id.drawer_layout)).perform(swipe(GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER_RIGHT));

        onView(withId(R.id.drawer_layout)).perform(swipe(GeneralLocation.CENTER_RIGHT, GeneralLocation.CENTER_LEFT));
        assertFalse(mActivityRule.getActivity().mDrawerLayout.isDrawerOpen (GravityCompat.START));
    }

    @Test
    public void checkIfTermsOfServiceIsWorking(){
        onView(withId(R.id.drawer_layout)).perform(swipe(GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER_RIGHT));
        onView(withText("Terms of Service")).perform(click());
    }

    private static ViewAction swipe(CoordinatesProvider startOfSwipe, CoordinatesProvider endOfSwipe) {
        return new GeneralSwipeAction(Swipe.FAST, startOfSwipe,
                endOfSwipe, Press.FINGER);
    }

}
