package com.group.tube;

import android.content.Context;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.ListView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.List.FavouriteList;
import com.group.tube.Models.Course;
import com.group.tube.utils.TestDataGenerator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class FavouriteButtonTest {

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
    public void checkIfElementIsTrue(){
        for(int i = 0; i < courses.size(); i++){
           // System.out.println(FavouriteList.Instance().list.get(i));
            System.out.println(courses.get(i).getId());

            FavouriteList j = FavouriteList.getInstance();
            System.out.println(j);
          j.list.add(i,"test");
            FavouriteList.getInstance().list.add(courses.get(i).getId());

        }
        for(int i = 0; i < FavouriteList.getInstance().list.size(); i++) {
            assertEquals(courses.get(i).getId(), FavouriteList.getInstance().list.get(i));
        }
       // assertTrue(map.getElement == elementToSave);
    }
}
