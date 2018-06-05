package com.group.tube;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.group.tube.List.EpisodeTimeList;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.utils.LocalStorageUtils;
import com.group.tube.utils.Utils;

import java.io.File;
import java.util.Map;

public class MainActivity extends Activity {

    private static final String JAVASCRIPT_INTERFACE = "JSInterface";

    private static final String JAVA_SCRIPT_CODE = "javascript:(function() { " +
                "function startVideo() {" +
                    "paella.player.play().then(() => {" +
                        "window." + JAVASCRIPT_INTERFACE + ".videoStarted(); " +
                    "});" +
                "} " +
                "paella.events.bind(paella.events.loadComplete, startVideo);"+
            "})()";

    private static final String JAVA_GET_TIMESTAMP = "javascript:(function() { " +
            "paella.player.videoContainer.currentTime().then((time) => {" +
                    "window." + JAVASCRIPT_INTERFACE + ".currentTime(time); " +
                "}); " +
            "})()";

    private NetworkConnector networkConnector;
    public boolean videoDidLoad = false;

    public void setNetworkConnector(NetworkConnector new_networkConnector) {
        this.networkConnector = new_networkConnector;
    }

    WebView webView;
    String episodeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get video ID from EpisodesOverviewActivity
        Intent intent = getIntent();
        episodeId = intent.getStringExtra(EpisodesOverviewActivity.EXTRA_EPISODE_ID);
        initializeEpisodesList();

        this.setContentView(R.layout.activity_main);

        this.networkConnector = new NetworkConnector();

        webView = findViewById(R.id.webview);

        JavaScriptInterface scriptInterface = new JavaScriptInterface(this);
        webView.addJavascriptInterface(scriptInterface, JAVASCRIPT_INTERFACE);
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedHttpAuthRequest(WebView view,
                                                  HttpAuthHandler handler,
                                                  String host,
                                                  String realm){
                handler.proceed(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
            }
            
            // autoplay when finished loading via javascript injection
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl(JAVA_SCRIPT_CODE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                System.out.println("MainActivity hold aus " + description);
            }
        });

        this.viewEpisode(episodeId, savedInstanceState);
    }

    @Override
    protected void onPause() {
        webView.loadUrl(JAVA_GET_TIMESTAMP);
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState )
    {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

    private void initializeEpisodesList() {
        File file = getFileStreamPath(LocalStorageUtils.FILE_NAME_EPISODE_TIMES);
        if(file == null || !file.exists()) {
            LocalStorageUtils.writeEpisodeListToFile(this);
        } else {
            LocalStorageUtils.readEpisodeListFromFile(this);
        }
    }

    public void viewEpisode(final String episodeId, Bundle savedInstanceState) {
        videoDidLoad = false;

        if (savedInstanceState == null)
        {
            webView.loadUrl(NetworkConnector.PAELLA_UI_URL + "frame_engage.html?id=" + episodeId);
        }
    }

    private class JavaScriptInterface {
        private Activity activity;

        public JavaScriptInterface(Activity activity) {
            this.activity = activity;
        }

        @JavascriptInterface
        public void currentTime(float seconds){
            Map<String, Float> favorites = EpisodeTimeList.getInstance();
            favorites.put(episodeId, seconds);
            LocalStorageUtils.writeEpisodeListToFile(activity);
        }

        @JavascriptInterface
        public void videoStarted(){
            videoDidLoad = true;
        }
    }
}