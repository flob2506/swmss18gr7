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

public class MainActivity extends Activity {

    private static final String JAVASCRIPT_INTERFACE = "JSInterface";

    private static final String JAVA_SCRIPT_CODE = "javascript:(function() { " +
            "document.getElementsByTagName('video')[0].play(); " +
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);

        this.networkConnector = new NetworkConnector();

        webView = findViewById(R.id.webview);

        JavaScriptInterface scriptInterface = new JavaScriptInterface(this);
        webView.addJavascriptInterface(scriptInterface, JAVASCRIPT_INTERFACE);
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedHttpAuthRequest(WebView view,
                                                  HttpAuthHandler handler,
                                                  String host,
                                                  String realm){
                handler.proceed(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
            }
            
            // autoplay when finished loading via javascript injection
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl(JAVA_SCRIPT_CODE);
                videoDidLoad = true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());

        // get video ID from EpisodesOverviewActivity
        Intent intent = getIntent();
        String id = intent.getStringExtra(EpisodesOverviewActivity.EXTRA_EPISODE_ID);
        this.viewEpisode(id, savedInstanceState);
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


    public void viewEpisode(final String episodeId, Bundle savedInstanceState) {
        videoDidLoad = false;

        if (savedInstanceState == null)
        {
            webView.loadUrl(NetworkConnector.TUBE_URL + "/paella/ui/frame_engage.html?id=" + episodeId);
        }
    }

    private class JavaScriptInterface {
        private Activity activity;

        public JavaScriptInterface(Activity activity) {
            this.activity = activity;
        }

        @JavascriptInterface
        public void currentTime(float seconds){
            // TODO: save time
            System.out.println("MainActivity time " + seconds);
            
        }
    }
}