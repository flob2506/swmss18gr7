package com.group.tube.Models;

public class Episodes {
    public String id;
    public String course_id;
    public String episode_title;
    public String presenter_url;
    public String presentation_url;
    public String episode_date;

    public void setPresenterUrl (String url) {
        presenter_url = url;
    }
}