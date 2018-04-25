package com.group.tube.networking;
import com.group.tube.Models.Course;
import com.group.tube.Models.Episodes;
import com.group.tube.parser.Parser;
import android.util.Log;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NetworkConnector{
    private NetworkTask networkTask;

    public NetworkConnector(){
        this.networkTask = new NetworkTask();
    }

    // TODO: episodeId nullable -> then get all episodes

    public void loadEpisode(final AsyncResponse<Episodes> responseHandler, String episodeId) {
        this.networkTask.setResponseHandler(new AsyncResponse<String>(){
            @Override
            public void processFinish(String jsonResponse){
                // TODO utilize JSON parser and pass episode to processFinish
                //Episode episode = JSONParser.getParse(jsonResponse);
                //responseHandler.processFinish(episode);
                Episodes e = new Episodes();
                Course c = new Course();
                Parser p = new Parser();
                try {
                    p.parseJSON(jsonResponse,c);
                    Log.i("id",c.episodes.get(0).id);
                    Log.i("course_id", c.episodes.get(0).course_id);
                    Log.i("episode_title", c.episodes.get(0).episode_title);
                    Log.i("presenter_url", c.episodes.get(0).presenter_url);
                    Log.i("presentation_url", c.episodes.get(0).presentation_url);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                responseHandler.processFinish(c.episodes.get(0));
            }
        });
        this.networkTask.execute("https://tube.tugraz.at/search/episode.json?sid=" + episodeId);
    }

    //TODO: Rework this
    public void loadEpisodes(final AsyncResponse<ArrayList<Episodes>> responseHandler, String episodeId) {
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
                responseHandler.processFinish(c.episodes);
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
