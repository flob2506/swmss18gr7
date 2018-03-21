package com.group.tube.networking;
import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnector extends AsyncTask{
    public NetworkConnector(){
        this.httpClient = new OkHttpClient();
        try {
            System.out.println("NetworkConnector "+ this.run("https://tube.tugraz.at/search/episode.json?sid=91ff68f1-7a0d-4655-8cec-643c3cb8b0ae"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    private OkHttpClient httpClient;

    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = this.httpClient.newCall(request).execute();
        return response.body().string();
    }
}
