package com.group.tube;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.List.FavouriteList;
import com.group.tube.Models.Course;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.utils.Utils;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class CoursesOverviewActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.group.tube.coursesOverviewActivity.MESSAGE";

    ListView listView;
    ArrayList<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFavoritesList();
        setContentView(R.layout.courses_overview);
        setTitle("All Courses");

        listView  = findViewById(R.id.listViewCourses);

        final Activity that = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent intent = new Intent(that, EpisodesOverviewActivity.class);
                intent.putExtra(EXTRA_MESSAGE, courses.get(position).getId());
                startActivity(intent);
            }
        });

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.loadCourses(new AsyncResponse<ArrayList<Course>>() {
            @Override
            public void processFinish(ArrayList<Course> response) {
                courses = response;
                initializeListView(courses);
            }
        });
    }

    private void initializeFavoritesList() {
        File file = getFileStreamPath(Utils.FILE_NAME);
        if(file == null || !file.exists()) {
            Utils.writeListToFile(this);
        } else {
            Utils.readListFromFile(this);
        }
    }


    private void initializeListView(final ArrayList<Course> courses) {
        for (Course course : courses) {
            boolean isFavorite = false;
            for(String courseId : FavouriteList.getInstance()) {
                if(course.getId().equals(courseId)) {
                    isFavorite = true;
                    break;
                }
            }
            course.setFavorite(isFavorite);
        }
        CourseArrayAdapter arrayAdapter = new CourseArrayAdapter(this, courses);
        listView.setAdapter(arrayAdapter);
    }
}
