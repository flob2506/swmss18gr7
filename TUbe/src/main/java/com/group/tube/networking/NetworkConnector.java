package com.group.tube.networking;
import com.group.tube.Models.Episodes;
import com.group.tube.parser.Parser;
import android.util.Log;
import org.json.JSONException;

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

                Parser p = new Parser();
                try {
                    p.parseJSON(jsonResponse,e);
                    Log.i("id",e.id);
                    Log.i("course_title", e.course_title);
                    Log.i("episode_title", e.episode_title);
                    Log.i("presenter_url", e.presenter_url);
                    Log.i("presentation_url", e.presentation_url);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                responseHandler.processFinish(e);
            }
        });
        this.networkTask.execute("https://tube.tugraz.at/search/episode.json?sid=" + episodeId);

    }
}
