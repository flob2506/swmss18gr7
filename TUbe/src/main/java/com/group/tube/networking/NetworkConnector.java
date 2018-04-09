package com.group.tube.networking;

public class NetworkConnector{
    private NetworkTask networkTask;

    public NetworkConnector(){
        this.networkTask = new NetworkTask();
    }

    // TODO: episodeId nullable -> then get all episodes
    public void loadEpisode(final AsyncResponse<String> responseHandler, String episodeId) {
        this.networkTask.setResponseHandler(new AsyncResponse<String>(){
            @Override
            public void processFinish(String jsonResponse){
                // TODO utilize JSON parser and pass episode to processFinish
                //Episode episode = JSONParser.getParse(jsonResponse);
                //responseHandler.processFinish(episode);
                responseHandler.processFinish(jsonResponse);
            }
        });
        this.networkTask.execute("https://tube.tugraz.at/search/episode.json?sid=" + episodeId);

    }
}
