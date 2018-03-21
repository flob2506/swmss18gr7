package com.group.tube;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;


public class MainActivity extends AppCompatActivity {
    private NetworkConnector networkConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.networkConnector = new NetworkConnector();
        // viewEpisode would be called on view interaction event
        this.viewEpisode("91ff68f1-7a0d-4655-8cec-643c3cb8b0ae");
    }

    private void viewEpisode(String episodeId) {

        // TODO AsyncResponse<Episode> instead of AsyncResponse<String>
        this.networkConnector.loadEpisode(new AsyncResponse<String>(){
            @Override
            public void processFinish(String episode){
                // videoPlayer would be the video view
               // this.videoPlayer.showVideo(episode.getUrl());

                System.out.println("NetworkTask " + episode);
            }
        }, episodeId);

    }
}
