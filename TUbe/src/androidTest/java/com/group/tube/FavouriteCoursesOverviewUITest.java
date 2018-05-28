package com.group.tube;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.core.internal.deps.guava.collect.Iterables;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.View;
import android.widget.ListView;

import com.group.tube.List.FavouriteList;
import com.group.tube.Models.Course;
import com.group.tube.utils.Utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.anything;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class FavouriteCoursesOverviewUITest {

    @Rule
    public ActivityTestRule<CoursesOverviewActivity> mActivityRule = new ActivityTestRule<>(CoursesOverviewActivity.class);

    private ActivityTestRule<FavouriteCoursesOverviewActivity> favouriteActivityRule =
            new ActivityTestRule<>(FavouriteCoursesOverviewActivity.class, false, false);

    @Before
    public void setup() {
        // clear favourite list
        Utils.writeListToFile(mActivityRule.getActivity(), new LinkedHashSet<String>());
        FavouriteList.getInstance().overwrite(new LinkedHashSet<String>());
    }

    @After
    public void tearDown() {
        // clear favourite list again
        mActivityRule.launchActivity(null);
        setup();
    }

    @Test
    public void verifyOnlyFavouritesShown() {
        // toggling 3 courses
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.toggleButton)).atPosition(2).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.toggleButton)).atPosition(1).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.toggleButton)).atPosition(0).perform(click());
        Intent intent = mActivityRule.getActivity().getIntent();

        // this should save them
        mActivityRule.finishActivity();

        // launching My Courses (should only display 3 courses)
        favouriteActivityRule.launchActivity(null);
        onView(withId(R.id.listViewCourses)).check(matches(withFavorites()));
    }

    @Test
    public void verifyTogglingFavouritesWorks() {
        // toggling 3 courses
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.toggleButton)).atPosition(2).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.toggleButton)).atPosition(1).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.toggleButton)).atPosition(0).perform(click());

        // this should save them
        mActivityRule.finishActivity();

        // launching My Courses (should display 3 courses)
        favouriteActivityRule.launchActivity(null);

        // un-toggling one course
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.toggleButton)).atPosition(0).perform(click());
        Intent intent = favouriteActivityRule.getActivity().getIntent();

        // this should save them again (only 2 courses)
        favouriteActivityRule.finishActivity();

        // opening activity (should only show 2 courses)
        favouriteActivityRule.launchActivity(intent);

        // we expect it to have 2 elements since 1 got removed
        onView(withId(R.id.listViewCourses)).check(matches(withFavorites()));
        assertEquals(2, ((ListView) favouriteActivityRule.getActivity().findViewById(R.id.listViewCourses)).getCount());
    }

    @Test
    public void checkActivityNavigationFromFavouriteCourses() {
        // toggling 3 courses
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.toggleButton)).atPosition(2).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.toggleButton)).atPosition(1).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.toggleButton)).atPosition(0).perform(click());

        // this should save them
        mActivityRule.finishActivity();

        // launching My Courses (should display 3 courses)
        favouriteActivityRule.launchActivity(null);

        // click on first element
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.textViewCourseOverviewItemCourseTitle)).atPosition(0).perform(click());
        assertEquals(getActivityInstance().getClass(), EpisodesOverviewActivity.class);
    }


    // adapted from FavoriteButtonTest
    public static Matcher<View> withFavorites() {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(final View view) {
                ListView list = ((ListView) view);
                ArrayList<String> checkedCourses = new ArrayList<>();
                for (int i = 0; i < list.getCount(); i++) {
                    Course course = (Course) list.getItemAtPosition(i);
                    View child = Utils.getChildViewOfListView(list, i);
                    if (child == null) {
                        // views not yet inflated (not scrolled to)
                        continue;
                    }
                    checkedCourses.add(course.getId());
                }
                return FavouriteList.getInstance().containsAll(checkedCourses) && checkedCourses.containsAll(FavouriteList.getInstance());
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("List doesn't have clicked indexes");
            }
        };
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

