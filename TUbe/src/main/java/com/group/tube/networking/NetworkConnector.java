package com.group.tube.networking;

import android.graphics.drawable.Drawable;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episode;
import com.group.tube.parser.Parser;

import java.text.ParseException;
import java.util.ArrayList;

public class NetworkConnector {
    public NetworkTask networkTask;
    private ThumbnailAsyncTask thumbnailAsyncTask = new ThumbnailAsyncTask();

    public static final String TUBE_URL = "https://tube-test.tugraz.at/";
    public static final String PAELLA_UI_URL = TUBE_URL + "paella/ui/";
    public static final String USERNAME = "tube-mobile";
    public static final String PASSWORD = "J8Mz4ftVNEZ54Wo6";

    public NetworkConnector() {
        this.networkTask = new NetworkTask();
    }

    public void loadAllCourses(final AsyncResponse<ArrayList<Course>> responseHandler) {
        this.networkTask.setResponseHandler(new AsyncResponse<String>() {
            @Override
            public void processFinish(String jsonResponse) {
                ArrayList<Course> courses = new ArrayList<>();
                Parser parser = new Parser();
                try {
                    parser.parseAllCourses(jsonResponse, courses);
                    responseHandler.processFinish(courses);
                } catch (ParseException e) {
                    responseHandler.handleProcessException(e);
                }
            }

            @Override
            public void handleProcessException(Exception e) {
                responseHandler.handleProcessException(e);
            }
        });

        this.networkTask.execute(TUBE_URL + "api/series");
    }


    public void loadEpisodesOfCourse(final AsyncResponse<ArrayList<Episode>> responseHandler, final String courseID) {
        this.networkTask.setResponseHandler(new AsyncResponse<String>() {
            @Override
            public void processFinish(String response) {
                ArrayList<Episode> episodes = new ArrayList<>();
                Parser parser = new Parser();
                try {
                    parser.parseEpisodes(response, episodes);
                    responseHandler.processFinish(episodes);
                                    } catch (ParseException e) {
                    responseHandler.handleProcessException(e);
                }
            }

            @Override
            public void handleProcessException(Exception e) {
                responseHandler.handleProcessException(e);
            }
        });
        this.networkTask.execute(TUBE_URL + "api/events/?filter=series:" + courseID);
    }

    public void loadAllEpisodes(final AsyncResponse<ArrayList<Episode>> responseHandler) {
        this.networkTask.setResponseHandler(new AsyncResponse<String>() {
            @Override
            public void processFinish(String response) {
                ArrayList<Episode> episodes = new ArrayList<>();
                Parser parser = new Parser();
                try {
                    parser.parseEpisodes(response, episodes);
                    responseHandler.processFinish(episodes);
                } catch (ParseException e) {
                    responseHandler.handleProcessException(e);
                }
            }

            @Override
            public void handleProcessException(Exception e) {
                responseHandler.handleProcessException(e);
            }
        });
        this.networkTask.execute(TUBE_URL + "api/events");
    }

    public void loadMediaOfEpisode(final AsyncResponse<String> responseHandler, String episodeID) {
        NetworkTask mediaURLTask = new NetworkTask();
        mediaURLTask.setLoginAndPassword(this.networkTask.getLogin(), this.networkTask.getPassword());
        mediaURLTask.setResponseHandler(new AsyncResponse<String>() {
            @Override
            public void processFinish(String jsonResponse) {
                responseHandler.processFinish(jsonResponse);
            }

            @Override
            public void handleProcessException(Exception e) {
                responseHandler.handleProcessException(e);
            }
        });

        mediaURLTask.execute(TUBE_URL + "api/events/" + episodeID + "/publications");
    }

    public void loadTimeOfEpisode(final AsyncResponse<String> responseHandler, String episodeID) {
        NetworkTask metadataTask = new NetworkTask();
        metadataTask.setLoginAndPassword(this.networkTask.getLogin(), this.networkTask.getPassword());
        metadataTask.setResponseHandler(new AsyncResponse<String>() {
            @Override
            public void processFinish(String jsonResponse) {
                responseHandler.processFinish(jsonResponse);
            }

            @Override
            public void handleProcessException(Exception e) {
                responseHandler.handleProcessException(e);
            }
        });

        metadataTask.execute(TUBE_URL + "api/events/" + episodeID + "/metadata");
    }

    public void downloadDrawable(final AsyncResponse<Drawable> responseHandler, final String thumbnailURL) {
        thumbnailAsyncTask.setResponseHandler(responseHandler);
        thumbnailAsyncTask.execute(thumbnailURL);
    }
}

