package com.group.tube;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.Models.Course;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;

import java.util.ArrayList;

public class CoursesOverviewActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.group.tube.coursesOverviewActivity.MESSAGE";

    ListView listView;
    ArrayList<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_overview);
        setTitle("All Courses");

        listView = findViewById(R.id.listViewCourses);

        final Activity that = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent intent = new Intent(that, EpisodesOverviewActivity.class);
                intent.putExtra(EXTRA_MESSAGE, courses.get(position).getId());
                startActivity(intent);
            }
        });

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword("tube-mobile", "J8Mz4ftVNEZ54Wo6");
        networkConnector.loadAllCourses(new AsyncResponse<ArrayList<Course>>() {
            @Override
            public void processFinish(ArrayList<Course> response) {
                courses = response;
                initializeListView(courses);
            }

            @Override
            public void handleProcessException(Exception e) {
                //dialog.show("oops "); sleep(1000); kill();
            }
        });
    }


    private void initializeListView(final ArrayList<Course> courses) {
        CourseArrayAdapter arrayAdapter = new CourseArrayAdapter(this, courses);
        listView.setAdapter(arrayAdapter);
    }
}
