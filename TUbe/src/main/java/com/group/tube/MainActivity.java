package com.group.tube;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.group.tube.Models.Episode;

import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;

public class MainActivity extends AppCompatActivity {
    private NetworkConnector networkConnector;
    public boolean videoDidLoad = false;
    
    public void setNetworkConnector(NetworkConnector new_networkConnector) {
        this.networkConnector = new_networkConnector;
    }

    VideoView videoView;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.networkConnector = new NetworkConnector();
        final Episode e = new Episode();

        // get video ID from EpisodesOverviewActivity
        Intent intent = getIntent();
        String id = intent.getStringExtra(EpisodesOverviewActivity.EXTRA_MESSAGE_EPISODE);
        this.viewEpisode("91ff68f1-7a0d-4655-8cec-643c3cb8b0ae", this);
    }

    public void viewEpisode(final String episodeId, final MainActivity ma) {
        // TODO AsyncResponse<Episode> instead of AsyncResponse<String>
        this.networkConnector.loadEpisode(new AsyncResponse<Episode>(){
            @Override
            public void processFinish(Episode episode){
                // videoPlayer would be the video view
                // this.videoPlayer.showVideo(episode.getUrl();
                Log.d("afkjsd", "here3");
                videoView = (VideoView) findViewById(R.id.videoView);
                Log.d("afkjsd", "here4");
                mediaController = new MediaController(ma);
                mediaController.setAnchorView(videoView);

                Uri uri = Uri.parse(episode.getPresentationUrl());

                videoView.setMediaController(mediaController);
                videoView.setVideoURI(uri);
                videoView.start();
                Log.d("afkjsd", "here5");
            }
        }, episodeId);
    }
}