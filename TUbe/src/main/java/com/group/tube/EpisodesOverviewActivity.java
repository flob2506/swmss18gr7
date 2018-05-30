package com.group.tube;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.group.tube.ArrayAdapter.EpisodeArrayAdapter;
import com.group.tube.Comparators.DateSortComparator;
import com.group.tube.Dialogs.CourseSemesterFilterDialogFragment;
import com.group.tube.Dialogs.EpisodeOptionBarDialogFragment;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.utils.TestDataGenerator;

import java.util.ArrayList;
import java.util.Collections;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;


public class EpisodesOverviewActivity extends AppCompatActivity

{
    ArrayList<Episode> episodes = new ArrayList<>();
    public static final String EXTRA_EPISODE_ID = "com.group.tube.coursesOverviewActivity.EXTRA_EPISODE_ID";

    ListView listView;
    RelativeLayout loadingBar;

    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episodes_overview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        toolbar.setTitleTextColor(Color.WHITE);

        final Intent intent = new Intent(this, MainActivity.class);

        Intent get_intent = getIntent();
        Bundle bundle = get_intent.getExtras();
        Course course = (Course) bundle.getSerializable(CoursesOverviewActivity.EXTRA_COURSE_OBJECT);

        this.setTitle(course.getCourseTitle());



        listView = findViewById(R.id.listViewEpisodes);
        loadingBar = findViewById(R.id.loadingIconEpisodes);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        final Activity that = this;

        NavigationView navigationView = findViewById(R.id.nav_view);
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

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                intent.putExtra(EXTRA_EPISODE_ID, episodes.get(position).getId());
                Log.d("msg",episodes.get(position).getId());
                startActivity(intent);
            }
        });
        listView = this.findViewById(R.id.listViewEpisodes);

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
        networkConnector.loadEpisodesOfCourse(new AsyncResponse<ArrayList<Episode>>() {
            @Override
            public void processFinish(ArrayList<Episode> response) {
                episodes = response;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initializeListView(episodes);
                        loadingBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void handleProcessException(Exception e) {
                //TODO show error message instead
                e.printStackTrace();
                throw new RuntimeException("Couldn't load data");
            }
        }, course.getId());
    }

    private void initializeListView(ArrayList<Episode> episodes) {
        listView = findViewById(R.id.listViewEpisodes);
        this.episodes = episodes;
        Collections.sort(episodes, new DateSortComparator());
        EpisodeArrayAdapter arrayAdapter = new EpisodeArrayAdapter(this, episodes);
        listView.setAdapter(arrayAdapter);
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

    /*

        ArrayList<Episode> episodes = new ArrayList<Episode>();
        episodes.add(new Episode("i bims die ID1","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at"));
        episodes.add(new Episode("i bims die ID2","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at"));
        episodes.add(new Episode("i bims die ID3","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at"));
        episodes.add(new Episode("i bims die ID4","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at"));
        episodes.add(new Episode("i bims die ID5","Wissenschaftliches Arbeiten","Teil 1","franz strohmeier","elektropepi.at"));
        ArrayAdapter<Episode> arrayAdapter = new ArrayAdapter<Episode>(this, R.layout.episodes_overview_item, episodes);
     */
}