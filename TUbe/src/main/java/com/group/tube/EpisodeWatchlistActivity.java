package com.group.tube;

import android.os.Bundle;
import android.view.View;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EpisodeWatchlistActivity extends EpisodesOverviewActivity {

    private Map<String, String> IDToCourseName = new HashMap<>();
    private List<String> episodeIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        readWatchlistInformation();

        super.onCreate(savedInstanceState);

        navigationView.getMenu().getItem(0).setChecked(false);
        navigationView.getMenu().getItem(1).setChecked(false);
        navigationView.getMenu().getItem(2).setChecked(true);

        setTitle("Watchlist");
    }

    @Override
    public void loadEpisodes(final Course course) {
        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
        networkConnector.loadEpisodesOfCourse(new AsyncResponse<ArrayList<Episode>>() {
            @Override
            public void processFinish(ArrayList<Episode> response) {
                episodes = response;
                for(Episode episode : episodes){
                    episode.setCourseTitle(IDToCourseName.get(episode.getId()));
                }
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

    @Override
    public void evaluateIntent() {
        course = new Course();

        //Just for testing
        course.setId("b69911cc-9c04-4e21-9053-93c068283d5f");
    }

    void readWatchlistInformation(){
        IDToCourseName.put("hello", "yolo");
    }
}
