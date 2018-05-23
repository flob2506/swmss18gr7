package com.group.tube;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.Models.Course;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;

import java.util.ArrayList;

import static android.graphics.PorterDuff.*;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class CoursesOverviewActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.group.tube.coursesOverviewActivity.MESSAGE";

    ListView listView;
    ArrayList<Course> courses;
    public DrawerLayout mDrawerLayout;
    public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_overview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setHomeActionContentDescription("Home button");
        toolbar.setTitleTextColor(Color.WHITE);
        setTitle("All Courses");

        listView = findViewById(R.id.listViewCourses);

        final Activity that = this;

        mDrawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        int id = menuItem.getItemId();

                        if (id == R.id.nav_allCourses){
                            Intent intent = new Intent(that, CoursesOverviewActivity.class);
                            startActivity(intent);
                        } else if (id == R.id.nav_myCourses) {
                            //TODO
                        } else if (id == R.id.termsOfService){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tugraz.at/en/about-this-page/legal-notice/"));
                            startActivity(browserIntent);
                        }


                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeListView(final ArrayList<Course> courses) {
        CourseArrayAdapter arrayAdapter = new CourseArrayAdapter(this, courses);
        listView.setAdapter(arrayAdapter);
    }
}
