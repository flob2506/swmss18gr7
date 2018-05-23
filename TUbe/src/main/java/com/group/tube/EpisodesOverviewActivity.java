package com.group.tube;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episodes_overview);
        final Intent intent = new Intent(this, MainActivity.class);

        Intent get_intent = getIntent();
        Bundle bundle = get_intent.getExtras();
        Course course = (Course) bundle.getSerializable(CoursesOverviewActivity.EXTRA_COURSE_OBJECT);

        this.setTitle(course.getCourseTitle());



        listView = findViewById(R.id.listViewEpisodes);
        loadingBar = findViewById(R.id.loadingIconEpisodes);

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
                // TODO dialog("ooops");
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