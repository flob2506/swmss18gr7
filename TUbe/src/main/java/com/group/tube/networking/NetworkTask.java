package com.group.tube.networking;

import android.os.AsyncTask;
import android.security.keystore.UserNotAuthenticatedException;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkTask extends AsyncTask {
    private OkHttpClient httpClient;
    private AsyncResponse<String> responseHandler = null;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    private String login;
    private String password;

    public NetworkTask() {
        this.httpClient = new OkHttpClient();
    }

    private String run(String url) throws IOException, UserNotAuthenticatedException {
        String credential = Credentials.basic(this.login, this.password);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", credential)
                .build();
        Response response = this.httpClient.newCall(request).execute();

        if (response.message().equals("Bad credentials")) {
            throw new UserNotAuthenticatedException();
        }


        return response.body().string();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            return this.run((String) objects[0]);
        } catch (Exception e) {
            e.printStackTrace();
            responseHandler.handleProcessException(e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object object) {
        if(object == null) {
            return;
        }
        responseHandler.processFinish((String) object);
    }

    public void setResponseHandler(AsyncResponse<String> responseHandler) {
        this.responseHandler = responseHandler;
    }

    public void setLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }
}

