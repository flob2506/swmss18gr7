package com.group.tube.Models;

/**
 * Created by cychu on 21/03/2018.
 */

public class Episodes {
    public String id;
    public String course_title;
    public String episode_title;
    public String presenter_url;
    public String presentation_url;

    public void setPresenterUrl (String url) {
        presenter_url = url;
    }
}