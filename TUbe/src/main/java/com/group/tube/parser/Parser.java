package com.group.tube.parser;
import com.group.tube.Models.Episodes;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;

/**
 * Created by cychu on 21/03/2018.
 */

public class Parser {
    public void parseJSON (String json, Episodes episode) throws JSONException {
        JSONObject firstString = new JSONObject(json);
        JSONObject searchResults = firstString.getJSONObject("search-results");
        JSONArray resultArray = searchResults.getJSONArray("result");
        JSONObject episodesObject = new JSONObject();
        for (int i = 0; i < resultArray.length(); i++) {
            //get the first Episode
            if (i == 0)
                episodesObject = resultArray.getJSONObject(i);
        }
        JSONObject episodeMediapackage = episodesObject.getJSONObject("mediapackage");
        String seriesTitle = episodeMediapackage.getString("seriestitle");
        String episodeID = episodeMediapackage.getString("id");
        String episodeTitle = episodeMediapackage.getString("title");
        JSONObject mediaObject = episodeMediapackage.getJSONObject("media");
        JSONArray trackArray = mediaObject.getJSONArray("track");
        JSONObject presenterObject = new JSONObject();
        JSONObject presentationObject = new JSONObject();
        for (int i = 0; i < trackArray.length(); i++)
        {
            if (i == 0)
                presentationObject = trackArray.getJSONObject(i);
            if (i == 1)
                presenterObject = trackArray.getJSONObject(i);
        }
        String presenterURL = presenterObject.getString("url");
        String presentationURL = presentationObject.getString("url");

        episode.id = episodeID;
        episode.course_title = seriesTitle;
        episode.episode_title = episodeTitle;
        episode.presenter_url = presenterURL;
        episode.presentation_url = presentationURL;
    }
}