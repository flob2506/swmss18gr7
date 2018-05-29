package com.group.tube;


import android.widget.ListView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.Models.Course;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class CourseOverviewUnitTest {
    private CoursesOverviewActivity coursesOverviewActivity;
    private ListView listView;
    private CourseArrayAdapter courseArrayAdapter;
    private ArrayList<Course> courses;

    @Before
    public void setUp() {
        coursesOverviewActivity = Robolectric.setupActivity(CoursesOverviewActivity.class);
        listView = coursesOverviewActivity.findViewById(R.id.listViewCourses);
        courses = TestDataGenerator.getRandomCourseList();
        courseArrayAdapter = new CourseArrayAdapter(coursesOverviewActivity, courses);
        listView.setAdapter(courseArrayAdapter);
    }

    @Test
    public void verifyAdapterIsCourseAdapter()
    {
        assertTrue(listView.getAdapter() instanceof CourseArrayAdapter);
    }

    @Test
    public void verifyAdapterFilled()
    {
        assertTrue(listView.getAdapter().getCount() == courses.size());
    }

    @Test
    public void verifyAdapterSameItems()
    {
        for(int i = 0; i < courses.size(); i++) {
            assertEquals(courses.get(i), listView.getAdapter().getItem(i));
        }
    }

    @Test
    public void verifyAdapterItemIDChanges()
    {
        for(Course course : courses) {
            course.setId(UUID.randomUUID().toString());
        }
        for(int i = 0; i < courses.size(); i++) {
            assertEquals(courses.get(i).getId(), ((Course)listView.getAdapter().getItem(i)).getId());
        }
    }

    @Test
    public void verifyDate()
    {
        Date date = Utils.getDate("20123.0230.3304e");
        assertNull(date);
    }

}