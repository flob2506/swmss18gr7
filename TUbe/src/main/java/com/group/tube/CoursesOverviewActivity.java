package com.group.tube;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import android.support.v4.util.Pair;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.List.FavouriteList;
import com.group.tube.Models.Course;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.Dialogs.CourseSemesterFilterDialogFragment;
import com.group.tube.Models.Course;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.utils.TestDataGenerator;
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
import java.util.LinkedHashSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.graphics.PorterDuff.*;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class CoursesOverviewActivity extends AppCompatActivity implements CourseSemesterFilterDialogFragment.CourseSemesterFilterDialogListener {
    Boolean courseListLoaded = false;

    public static final String EXTRA_COURSE_OBJECT = "com.group.tube.coursesOverviewActivity.EXTRA_COURSE_OBJECT";

    private boolean chosenIsWs;
    private int chosenSemesterYear;
    ProgressBar loadingBar;
    ListView listView;
    ArrayList<Course> courses;
    public DrawerLayout mDrawerLayout;
    public NavigationView navigationView;
    ArrayList<Course> allCourses;
    private final Pair<Integer, Boolean> currentSemester = Utils.getCurrentSemester();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFavoritesList();
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
        loadingBar = findViewById(R.id.loadingProgressBarCourses);

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

                        Intent intent;

                        if (id == R.id.nav_allCourses){
//                            Intent intent = new Intent(that, CoursesOverviewActivity.class);
//                            startActivity(intent);
                            intent = new Intent(that, CoursesOverviewActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            that.startActivity(intent);
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
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_COURSE_OBJECT, courses.get(position));
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        final NetworkConnector networkConnector = new NetworkConnector();

        networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
        networkConnector.loadAllCourses(new AsyncResponse<ArrayList<Course>>() {
            @Override
            public void processFinish(ArrayList<Course> response) {
                courses = response;
                allCourses = new ArrayList<Course>(courses);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initializeListView(courses);
                        filterCoursesList(currentSemester.first, currentSemester.second, null);
                        loadingBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void handleProcessException(Exception e) {
                //dialog.show("oops "); sleep(1000); kill();
            }
        });
        initializeFilterButton();

        setChosenSemester(currentSemester.first, currentSemester.second);
    }

    private void initializeFavoritesList() {
        File file = getFileStreamPath(Utils.FILE_NAME);
        if(file == null || !file.exists()) {
            Utils.writeListToFile(this);
        } else {
            Utils.readListFromFile(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String s) {
                List<String> query = Arrays.asList(s.split("\\s+"));
                filterCoursesList(chosenSemesterYear, chosenIsWs, query);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
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

    private void initializeFilterButton()
    {
        ImageView filterButton = findViewById(R.id.imageViewFilterCourseList);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog()
    {
        CourseSemesterFilterDialogFragment dialog = new CourseSemesterFilterDialogFragment();
        dialog.setChosenIsWs(chosenIsWs);
        dialog.setChosenSemesterYear(chosenSemesterYear);
        dialog.show(getFragmentManager(), "");
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
        courseListLoaded = true;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int semesterYear, boolean isWs) {
        filterCoursesList(semesterYear, isWs, null);
    }

    private void filterCoursesList(int semesterYear, boolean isWs, List<String> query) {
        setChosenSemester(semesterYear, isWs);
        CourseArrayAdapter courseAdapter = ((CourseArrayAdapter)listView.getAdapter());
        courseAdapter.clear();

        for(Course course : allCourses) {
            if (query == null) {
                if (course.isWs() == isWs && course.getSemesterYear() == semesterYear) {
                    courseAdapter.add(course);
                }
            } else {
                if (course.isWs() == isWs && course.getSemesterYear() == semesterYear &&
                        Utils.matchesAll(course, query)) {
                    courseAdapter.add(course);
                }
            }
        }
        courseAdapter.notifyDataSetChanged();
    }

    private void setChosenSemester(int semesterYear, boolean isWs) {
        TextView textView = findViewById(R.id.textViewChosenSemester);
        textView.setText(Utils.getChosenSemesterText(semesterYear, isWs, this));
        this.chosenSemesterYear = semesterYear;
        this.chosenIsWs = isWs;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
