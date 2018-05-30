package com.group.tube;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.Models.Course;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CourseOverviewUnitTest {
    @Rule
    public ActivityTestRule<CoursesOverviewActivity> mCoursesOverviewActivity = new ActivityTestRule<>(
            CoursesOverviewActivity.class);
    private ListView listView;
    private CourseArrayAdapter courseArrayAdapter;
    private ArrayList<Course> courses;

    @Before
    public void setUp() {
        listView = mCoursesOverviewActivity.getActivity().findViewById(R.id.listViewCourses);
        courses = TestDataGenerator.getRandomCourseList();
        courseArrayAdapter = new CourseArrayAdapter(mCoursesOverviewActivity.getActivity(), courses);
        mCoursesOverviewActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(courseArrayAdapter);
            }
        });
    }

    @Test
    public void verifyAdapterIsCourseAdapter()
    {
        mCoursesOverviewActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertTrue(listView.getAdapter() instanceof CourseArrayAdapter);
            }
        });
    }

    @Test
    public void verifyAdapterFilled()
    {
        mCoursesOverviewActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertTrue(listView.getAdapter().getCount() == courses.size());
            }
        });
    }

    @Test
    public void verifyAdapterSameItems()
    {
        mCoursesOverviewActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < courses.size(); i++) {
                    assertEquals(courses.get(i), listView.getAdapter().getItem(i));
                }
            }
        });
    }

    @Test
    public void verifyAdapterItemIDChanges()
    {
        for(Course course : courses) {
            course.setId(UUID.randomUUID().toString());
        }
        mCoursesOverviewActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < courses.size(); i++) {
                    assertEquals(courses.get(i).getId(), ((Course)listView.getAdapter().getItem(i)).getId());
                }
            }
        });
    }

    @Test
    public void verifyDate()
    {
        Date date = Utils.getDate("20123.0230.3304e");
        assertNull(date);
    }

}