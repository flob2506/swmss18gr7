package com.group.tube.Models;

import java.util.Date;
public class Episodes {
    public String id;
    public String course_id;
    public String course_title;
    public String episode_title;
    public String presenter_url;
    public String presentation_url;
    private Date date;

    public void setPresenterUrl (String url) {
        presenter_url = url;
    }

    public Episodes() {

    }
    public Episodes(String id, String course_title, String episode_title, String presenter_url, String presentation_url, Date date) {
        this.id = id;
        this.course_title = course_title;
        this.episode_title = episode_title;
        this.presenter_url = presenter_url;
        this.presentation_url = presentation_url;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getEpisode_title() {
        return episode_title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEpisode_title(String episode_title) {
        this.episode_title = episode_title;
    }

    public String getPresenter_url() {
        return presenter_url;
    }

    public void setPresenter_url(String presenter_url) {
        this.presenter_url = presenter_url;
    }

    public String getPresentation_url() {
        return presentation_url;
    }

    public void setPresentation_url(String presentation_url) {
        this.presentation_url = presentation_url;
    }
}