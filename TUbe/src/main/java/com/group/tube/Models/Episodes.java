package com.group.tube.Models;

import java.util.Date;

public class Episodes {
    public String id;
    public String course_id;
    public String episode_title;
    public String presenter_url;
    public String presentation_url;
    public Date episode_date;

    public void setPresenterUrl (String url) {
        presenter_url = url;
    }
}