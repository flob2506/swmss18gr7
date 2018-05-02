package com.group.tube.networking;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.parser.Parser;
import android.util.Log;
import org.json.JSONException;

import java.util.ArrayList;

public class NetworkConnector{
    private NetworkTask networkTask;

    public NetworkConnector(){
        this.networkTask = new NetworkTask();
    }

    // TODO: episodeId nullable -> then get all episodes

    public void loadEpisode(final AsyncResponse<Episode> responseHandler, String episodeId) {
        this.networkTask.setResponseHandler(new AsyncResponse<String>(){
            @Override
            public void processFinish(String jsonResponse){
                // TODO utilize JSON parser and pass episode to processFinish
                //Episode episode = JSONParser.getParse(jsonResponse);
                //responseHandler.processFinish(episode);
                Episode e = new Episode();
                Course c = new Course();
                Parser p = new Parser();
                try {
                    p.parseJSON(jsonResponse,c);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                responseHandler.processFinish(c.getEpisode(0));
            }
        });
        this.networkTask.execute("https://tube.tugraz.at/search/episode.json?sid=" + episodeId);
    }

    //TODO: Rework this
    public void loadEpisodes(final AsyncResponse<ArrayList<Episode>> responseHandler, String episodeId) {
        this.networkTask.setResponseHandler(new AsyncResponse<String>(){
            @Override
            public void processFinish(String jsonResponse){
                // TODO utilize JSON parser and pass episode to processFinish
                //Episode episode = JSONParser.getParse(jsonResponse);
                //responseHandler.processFinish(episode);
                Course c = new Course();
                Parser p = new Parser();
                try {
                    p.parseJSON(jsonResponse,c);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                responseHandler.processFinish(c.getEpisodes());
            }
        });
        this.networkTask.execute("https://tube.tugraz.at/search/episode.json?sid=" + episodeId);
    }

    public void loadCourses(final AsyncResponse<ArrayList<Course>> responseHandler) {
        this.networkTask.setResponseHandler(new AsyncResponse<String>(){
            @Override
            public void processFinish(String jsonResponse){
                ArrayList<Course> courses = new ArrayList<>();
                Parser p = new Parser();
                try {
                    p.parseJSON(jsonResponse, courses);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                responseHandler.processFinish(courses);
            }
        });
        this.networkTask.execute("https://tube.tugraz.at/series/series.json");
    }
}
