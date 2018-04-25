package com.group.tube;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.group.tube.Models.Episodes;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;

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


                final Button btn = (Button)findViewById(R.id.BackButton);

                btn.setVisibility(View.INVISIBLE);
                mediaController.hide();

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // startActivity(new Intent(MainActivity.this, EpisodesOverviewActivity.class));
                        ma.finish();
                        //startActivity(new Intent(MainActivity.this, EpisodesOverviewActivity.class));
                    }
                });



                videoView.setOnTouchListener(new View.OnTouchListener() {
                    boolean bVideoIsBeingTouched = false;
                    Handler mHandler = new Handler();
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (!bVideoIsBeingTouched) {
                            bVideoIsBeingTouched = true;
                            if (mediaController.isShowing()) {
                                btn.setVisibility(View.INVISIBLE);
                                mediaController.hide();
                            } else {
                                btn.setVisibility(View.VISIBLE);
                                mediaController.show(0);
                            }
                            mHandler.postDelayed(new Runnable() {
                                public void run() {
                                    bVideoIsBeingTouched = false;
                                }
                            }, 100);
                        }
                        return true;
                    }
                });


            }
        }, episodeId);
    }
}
