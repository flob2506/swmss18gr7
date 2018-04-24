package com.group.tube;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.ArrayAdapter.EpisodeArrayAdapter;
import com.group.tube.Comparators.DateSortComparator;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episodes;
import com.group.tube.utils.TestDataGenerator;

import java.util.ArrayList;
import java.util.Collections;

public class CoursesOverviewActivity extends AppCompatActivity {
    ArrayList<Course> courses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_overview);
        initializeListView();
    }


    private void initializeListView() {
        final ListView listView = findViewById(R.id.listViewCourses);

        courses = TestDataGenerator.getRandomCourseList();

        CourseArrayAdapter arrayAdapter = new CourseArrayAdapter(this, courses);
        listView.setAdapter(arrayAdapter);
    }

}
