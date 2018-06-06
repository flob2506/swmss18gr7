package com.group.tube;

import android.os.Bundle;
import android.view.View;

import com.group.tube.List.WatchLaterList;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class EpisodeWatchlistActivity extends EpisodesOverviewActivity {

    private Map<String, String> IDToCourseName = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        readWatchlistInformation();

        super.onCreate(savedInstanceState);

        setTitle("Watchlist");
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(false);
        navigationView.getMenu().getItem(1).setChecked(false);
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public void loadEpisodes() {
        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
        networkConnector.loadAllEpisodes(new AsyncResponse<ArrayList<Episode>>() {
            @Override
            public void processFinish(ArrayList<Episode> response) {
                Utils.addToEpisodeListIfInSet(response, episodes, WatchLaterList.getInstance());
                for(Episode episode : episodes){
                    episode.setCourseTitle(IDToCourseName.get(episode.getId()));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initializeWatchLaterEpisodes(episodes);
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
        });
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
