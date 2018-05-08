package com.group.tube;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.group.tube.networking.NetworkConnector;

public class MainActivity extends AppCompatActivity {
    private NetworkConnector networkConnector;
    public boolean videoDidLoad = false;
    public void setNetworkConnector(NetworkConnector new_networkConnector) {
        this.networkConnector = new_networkConnector;
    }

    WebView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.networkConnector = new NetworkConnector();

        videoView = (WebView) findViewById(R.id.webview);

        final WebSettings settings = videoView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);

        videoView.setWebViewClient(new WebViewClient() {
            // autoplay when finished loading via javascript injection
            public void onPageFinished(WebView view, String url) {
                videoView.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()");
                videoDidLoad = true;
            }
        });
        videoView.setWebChromeClient(new WebChromeClient());
        // get video ID from EpisodesOverviewActivity
        Intent intent = getIntent();
        String id = intent.getStringExtra(EpisodesOverviewActivity.EXTRA_MESSAGE_EPISODE);
        //episodeId to be replaced with id
        this.viewEpisode("541e5b99-4225-496c-9b11-4c6e438f5c15");
    }

    public void viewEpisode(final String episodeId) {
        videoDidLoad = false;
        videoView.loadUrl("https://tube.tugraz.at/paella/ui/frame_engage.html?id=" + episodeId);
    }
}