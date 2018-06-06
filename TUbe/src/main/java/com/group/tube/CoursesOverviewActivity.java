package com.group.tube;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.design.widget.NavigationView;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.List.FavouriteList;
import com.group.tube.List.WatchLaterList;
import com.group.tube.Models.Course;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.Dialogs.CourseSemesterFilterDialogFragment;
import com.group.tube.utils.LocalStorageUtils;
import com.group.tube.utils.Utils;

import java.io.File;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

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
        initializeWatchLaterList();

        //needs to be a function to be overridable
        setContentViewOverride();


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
                        } else if (id == R.id.nav_myCourses){
                            //update drawer
                            navigationView.setCheckedItem(R.id.nav_myCourses);

                            intent = new Intent(that, FavouriteCoursesOverviewActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            that.startActivity(intent);
                        } else if (id == R.id.termsOfService){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tugraz.at/en/about-this-page/legal-notice/"));
                            startActivity(browserIntent);
                        } else if (id == R.id.nav_watchList){
                            intent = new Intent(that, EpisodeWatchlistActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            that.startActivity(intent);
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
                        filterCoursesList(null);
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

        this.chosenSemesterYear = currentSemester.first;
        this.chosenIsWs = currentSemester.second;

        setChosenSemester();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.getMenu().getItem(1).setChecked(false);
        navigationView.getMenu().getItem(2).setChecked(false);
    }

    public void setContentViewOverride() {
        setContentView(R.layout.courses_overview);
    }

    private void initializeFavoritesList() {
        File favourites = getFileStreamPath(LocalStorageUtils.FILE_NAME_COURSE_FAVORITES);
        if(favourites == null || !favourites.exists()) {
            LocalStorageUtils.writeCourseFavoriteListToFile(this);
        } else {
            LocalStorageUtils.readCourseFavoriteListFromFile(this);
        }
    }

    private void initializeWatchLaterList() {
        File watchLaterList = getFileStreamPath(LocalStorageUtils.FILE_NAME_WATCH_LATER_LIST);
        if(watchLaterList == null || !watchLaterList.exists()) {
            LocalStorageUtils.writeWatchLaterListToFile(this);
        } else {
            LocalStorageUtils.readWatchListFromFile(this);

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
                filterCoursesList(query);
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

    public void initializeFilterButton()
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

    public void initializeListView(final ArrayList<Course> courses) {
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
        this.chosenSemesterYear = semesterYear;
        this.chosenIsWs = isWs;
        filterCoursesList(null);
    }

    public void filterCoursesList(List<String> query) {
        setChosenSemester();
        CourseArrayAdapter courseAdapter = ((CourseArrayAdapter)listView.getAdapter());

        if (courseAdapter != null) {
            courseAdapter.clear();

            for (Course course : allCourses) {
                if (query == null) {
                    if (course.isWs() == this.chosenIsWs && course.getSemesterYear() == this.chosenSemesterYear) {
                        courseAdapter.add(course);
                    }
                } else {
                    if (course.isWs() == this.chosenIsWs && course.getSemesterYear() == this.chosenSemesterYear &&
                            Utils.matchesAll(course, query)) {
                        courseAdapter.add(course);
                    }
                }
            }
            courseAdapter.notifyDataSetChanged();
        }
    }

    private void setChosenSemester() {
        TextView textView = findViewById(R.id.textViewChosenSemester);
        textView.setText(Utils.getChosenSemesterText(this.chosenSemesterYear, this.chosenIsWs, this));
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
