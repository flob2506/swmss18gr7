package com.group.tube;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.group.tube.networking.NetworkConnector;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkConnector networkConnector = new NetworkConnector();
    }

    private class GetUrlContentTask extends AsyncTask<String, Integer, String> {
        protected NetworkConnector getNetworkConnector() {
            NetworkConnector newNetworkConnector = new NetworkConnector();
            return newNetworkConnector;
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
