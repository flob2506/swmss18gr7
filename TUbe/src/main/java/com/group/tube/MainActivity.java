package com.group.tube;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.group.tube.Models.Episodes;
import com.group.tube.parser.Parser;
import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ------------------- code for testing parser -------------------
        /* final Episodes e = new Episodes();
        final Parser p = new Parser();
        AsyncHttpClient client = new AsyncHttpClient();


        client.get("https://tube.tugraz.at/search/episode.json?sid=1ace56be-eb47-4150-97c1-9e285f34e5de&limit=12", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("msg", responseString);
                //JSONObject json=new JSONObject(responseString);
                try {
                    p.parseJSON(responseString,e);
                    Log.i("id",e.id);
                    Log.i("course_title", e.course_title);
                    Log.i("episode_title", e.episode_title);
                    Log.i("presenter_url", e.presenter_url);
                    Log.i("presentation_url", e.presentation_url);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }); */
        // ------------------- code for testing parser -------------------
    }
}
