package com.group.tube;
import android.app.ActivityManager;
import android.os.AsyncTask;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.group.tube.Models.Episodes;
import com.group.tube.parser.Parser;
import com.loopj.android.http.*;
import android.widget.MediaController;
import android.widget.VideoView;

import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private NetworkConnector networkConnector;

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
        // viewEpisode would be called on view interaction event

        final Episodes e = new Episodes();

        this.viewEpisode("91ff68f1-7a0d-4655-8cec-643c3cb8b0ae", this);

    }

    public void viewEpisode(final String episodeId, final MainActivity ma) {

        // TODO AsyncResponse<Episode> instead of AsyncResponse<String>
        this.networkConnector.loadEpisode(new AsyncResponse<Episodes>(){
            @Override
            public void processFinish(Episodes episode){
                // videoPlayer would be the video view
               // this.videoPlayer.showVideo(episode.getUrl();

                videoView = (VideoView) findViewById(R.id.videoView);

                mediaController = new MediaController(ma);
                mediaController.setAnchorView(videoView);

                Uri uri = Uri.parse(episode.presentation_url);

                videoView.setMediaController(mediaController);
                videoView.setVideoURI(uri);
                videoView.start();

            }
        }, episodeId);
    }
}
