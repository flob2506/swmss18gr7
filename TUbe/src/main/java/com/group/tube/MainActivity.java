package com.group.tube;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;


public class MainActivity extends AppCompatActivity {


    VideoView videoView;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        videoView = (VideoView) findViewById(R.id.videoView);

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        Uri uri = Uri.parse("https://tube.tugraz.at/static/mh_default_org/engage-player/8556615e-a07f-43cc-b119-a54bf52cc63e/84a4b941-fb3d-4df9-a6fd-0c619252496b/track.mp4");


        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.start();



    }
}
