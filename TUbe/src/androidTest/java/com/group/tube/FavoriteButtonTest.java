package com.group.tube;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.group.tube.List.FavouriteList;
import com.group.tube.Models.Course;
import com.group.tube.utils.Utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FavoriteButtonTest {


    @Rule
    public ActivityTestRule<CoursesOverviewActivity> mActivityRule = new ActivityTestRule<>(CoursesOverviewActivity.class);

    @Test
    public void verifyToggledFavoritesAreSaved() {
        // don't change the order, since both listitems need to be in view so they are inflated
        // and thus can be checked if their toggle button is toggled
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.toggleButton)).atPosition(2).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listViewCourses)).onChildView(withId(R.id.toggleButton)).atPosition(1).perform(click());
        onView(withId(R.id.listViewCourses)).check(matches(withFavorites()));

    }

    @Test
    public void verifyFileCreated() throws InterruptedException {
        Utils.writeListToFile(mActivityRule.getActivity());
        File file = mActivityRule.getActivity().getFileStreamPath(Utils.FILE_NAME);
        assertTrue(file.exists());
    }

    public static Matcher<View> withFavorites () {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely (final View view) {
                ListView list = ((ListView) view);
                ArrayList<String> checkedCourses = new ArrayList<>();
                for(int i = 0; i < list.getCount(); i++) {
                    Course course = (Course)list.getItemAtPosition(i);
                    View child = Utils.getChildViewOfListView(list, i);
                    if(child == null) {
                        // views not yet inflated (not scrolled to)
                        continue;
                    }
                    ToggleButton btn = child.findViewById(R.id.toggleButton);
                    if(btn.isChecked()) {
                        checkedCourses.add(course.getId());
                    }
                }
                return FavouriteList.getInstance().containsAll(checkedCourses) && checkedCourses.containsAll(FavouriteList.getInstance());
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("List doesn't have clicked indexes");
            }
        };
    }


}