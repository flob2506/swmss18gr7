package com.group.tube.networking;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkTask extends AsyncTask {
    private OkHttpClient httpClient;
    private AsyncResponse<String> responseHandler = null;

    public NetworkTask() {
        this.httpClient = new OkHttpClient();
    }

    private String run(String url) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = this.httpClient.newCall(request).execute();

        return response.body().string();
    }

    @Override
    protected Object doInBackground(Object[] objects){
        try {
            return this.run((String)objects[0]);
        } catch (IOException e) {
            //TODO: handle exceptions in controlled manner
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object object){
        responseHandler.processFinish((String)object);
    }

    public void setResponseHandler(AsyncResponse<String> responseHandler) {
        this.responseHandler = responseHandler;
    }

}

