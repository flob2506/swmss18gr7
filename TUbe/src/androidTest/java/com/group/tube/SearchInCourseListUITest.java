package com.group.tube;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.Models.Course;
import com.group.tube.utils.Utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void checkIfSearchNoItemInListView() throws InterruptedException {
        onView(withId(R.id.item_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("This is a name which never is going to be used as title!"), pressKey(KeyEvent.KEYCODE_ENTER));

        assertEquals(mCoursesOverviewActivity.getActivity().listView.getAdapter().getCount(), 0);
    }

    @Test
    public void checkIfSearchHasRightElementInListView() throws InterruptedException {
        List<String> query = new ArrayList<String>();
        query.add("strukturierte");
        onView(withId(R.id.item_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("strukturierte"), pressKey(KeyEvent.KEYCODE_ENTER));

        onView(withId(R.id.listViewCourses)).check(matches(withStringInTitle(query)));
    }

    @Test
    public void checkIfSearchHasRightElementsInListView() throws InterruptedException {
        List<String> query = new ArrayList<String>();
        query.add("strukturierte");
        query.add("Prog");
        onView(withId(R.id.item_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("strukturierte Prog"), pressKey(KeyEvent.KEYCODE_ENTER));

        onView(withId(R.id.listViewCourses)).check(matches(withStringInTitle(query)));
    }

    @Test
    public void checkIfSearchHasRightSeriesInListView() throws InterruptedException {
        List<String> query = new ArrayList<String>();
        query.add("New");
        query.add("Series");
        onView(withId(R.id.item_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("new Series"), pressKey(KeyEvent.KEYCODE_ENTER));

        onView(withId(R.id.listViewCourses)).check(matches(withStringInTitle(query)));
    }

    @Test
    public void checkIfSearchLogic() throws InterruptedException {
        List<String> query = new ArrayList<String>();
        query.add("New");
        query.add("Series");
        onView(withId(R.id.item_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("new Series"), pressKey(KeyEvent.KEYCODE_ENTER));

        ListView listView = mCoursesOverviewActivity.getActivity().listView;
        boolean isMatch = true;
        CourseArrayAdapter courses = ((CourseArrayAdapter)listView.getAdapter());
        for(int i = 0; i < courses.getCount(); i++) {
            Course course = (Course)listView.getItemAtPosition(i);
            isMatch &= course.getCourseTitle().toLowerCase().contains(query.get(0).toLowerCase());
            isMatch &= course.getCourseTitle().toLowerCase().contains(query.get(1).toLowerCase());
        }
        assertTrue(isMatch);
    }


    public static Matcher<View> withStringInTitle (final List<String> query) {
        return new TypeSafeMatcher<View> () {
            @Override
            public boolean matchesSafely (final View view) {
                ListView list = ((ListView) view);
                boolean isMatch = true;
                for(int i = 0; i < list.getCount(); i++) {
                    Course course = (Course)list.getItemAtPosition(i);
                    isMatch &= Utils.matchesAll(course, query);
                }
                return isMatch;
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("List doesn't match query");
            }
        };
    }




}