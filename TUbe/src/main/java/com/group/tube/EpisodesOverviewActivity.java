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
import com.group.tube.Models.Episode;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;

import java.util.ArrayList;
import java.util.Collections;

import android.widget.AdapterView.OnItemClickListener;


public class EpisodesOverviewActivity extends AppCompatActivity

{
    ArrayList<Episode> episodes = new ArrayList<>();
    public static final String EXTRA_MESSAGE_EPISODE = "com.group.tube.coursesOverviewActivity.MESSAGE";

    ListView listView;

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
        listView = findViewById(R.id.listViewEpisodes);

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
                intent.putExtra(EXTRA_MESSAGE_EPISODE, episodes.get(position).getId().toString());
                Log.d("msg",episodes.get(position).getId().toString() );
                startActivity(intent);
            }
        });
        listView = this.findViewById(R.id.listViewEpisodes);

        Intent get_intent = getIntent();
        String course_id = get_intent.getStringExtra(CoursesOverviewActivity.EXTRA_MESSAGE);
        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.loadEpisodes(new AsyncResponse<ArrayList<Episode>>() {
            @Override
            public void processFinish(ArrayList<Episode> response) {
                episodes = response;
                initializeListView(episodes);
            }
        }, course_id);
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