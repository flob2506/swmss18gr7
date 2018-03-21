package com.group.tube.networking;
import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnector implements AsyncResponse{
    private NetworkTask networkTask;

    public NetworkConnector(){
        this.networkTask = new NetworkTask();
        //this to set delegate/listener back to this class
        networkTask.delegate = this;

        networkTask.execute("https://tube.tugraz.at/search/episode.json?sid=91ff68f1-7a0d-4655-8cec-643c3cb8b0ae");
    }


    @Override
    public void processFinish(String jsonResponse) {
        System.out.println("NetworkTask " + jsonResponse);
        //Episode episode = maxseijsonparser.parse(output);
        //videoPlayer.startVideo(episode.getUrl());
    }
}
