package com.group.tube;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.DataInteraction;
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
import android.widget.VideoView;

import com.group.tube.Models.Course;
import com.group.tube.utils.Utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FilterCoursesBySemesterUITest {


    @Rule
    public ActivityTestRule<CoursesOverviewActivity> mCoursesOverviewActivity = new ActivityTestRule<>(
            CoursesOverviewActivity.class);


    @Test
    public void dialogOpensAfterClick() throws InterruptedException {
        onView(withId(R.id.imageViewFilterCourseList)).perform(click());
        onView(withText(R.string.filter_by_semester)).check(matches(isDisplayed()));
    }

    @Test
    public void listIsFiltered() throws InterruptedException {
        onView(withId(R.id.imageViewFilterCourseList)).perform(click());
        onView(withId(R.id.numberPickerIsWsSemesterFilterDialog)).perform(setNumber(0)); //WS
        onView(withId(R.id.numberPickerSemesterSemesterFilterDialog)).perform(setNumber(2017)); //year
        onView(withText("OK")).perform(click());
        onView(withId(R.id.listViewCourses)).check(matches(withListSemesters(2017, true)));
    }

    @Test
    public void listHasFilterMessage() throws InterruptedException {
        onView(withId(R.id.imageViewFilterCourseList)).perform(click());
        onView(withId(R.id.numberPickerIsWsSemesterFilterDialog)).perform(setNumber(0)); //WS
        onView(withId(R.id.numberPickerSemesterSemesterFilterDialog)).perform(setNumber(2017)); //year
        onView(withText("OK")).perform(click());
        onView(withId(R.id.textViewChosenSemester)).check(matches(withText(Utils.getChosenSemesterText(2017, true, InstrumentationRegistry.getTargetContext()))));
    }

    @Test
    public void listHasFilterMessageCurrentSemester() throws InterruptedException {
        Pair<Integer, Boolean> currentSemester = Utils.getCurrentSemester();
        onView(withId(R.id.imageViewFilterCourseList)).perform(click());
        onView(withId(R.id.numberPickerIsWsSemesterFilterDialog)).perform(setNumber(currentSemester.second ? 0 : 1)); //WS
        onView(withId(R.id.numberPickerSemesterSemesterFilterDialog)).perform(setNumber(currentSemester.first)); //year
        onView(withText("OK")).perform(click());
        onView(withId(R.id.textViewChosenSemester)).check(matches(withText(Utils.getChosenSemesterText(currentSemester.first, currentSemester.second, InstrumentationRegistry.getTargetContext()))));
    }

    // https://spin.atomicobject.com/2017/10/10/android-numberpicker-espresso/
    public static ViewAction setNumber(final int num) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                NumberPicker np = (NumberPicker) view;
                np.setValue(num);
            }

            @Override
            public String getDescription() {
                return "Set the passed number into the NumberPicker";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(NumberPicker.class);
            }
        };
    }

    public static Matcher<View> withListSemesters (final int semesterYear, final boolean isWs) {
        return new TypeSafeMatcher<View> () {
            @Override
            public boolean matchesSafely (final View view) {
                ListView list = ((ListView) view);
                boolean isMatch = true;
                for(int i = 0; i < list.getCount(); i++) {
                    Course course = (Course)list.getItemAtPosition(i);
                    isMatch &= course.getSemesterYear() == semesterYear && course.isWs() == isWs;
                }
                return isMatch;
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("List doesn't match semester " + semesterYear + (isWs ? "WS" : "SS"));
            }
        };
    }


}