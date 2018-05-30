package com.group.tube.Models;

import com.group.tube.networking.NetworkConnector;

import java.util.Date;

public class Episode {
    private String id;
    private String courseId;
    private String courseTitle;
    private String episodeTitle;
    private String presenterUrl;
    private String presentationUrl;
    private String thumbnailURL;
    private Date date;
    //private Drawable thumbnailDrawable;


    public Episode() {

    }

    public Episode(String id, String courseTitle, String episodeTitle, String presenterUrl, String presentationUrl, Date date) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.episodeTitle = episodeTitle;
        this.presenterUrl = presenterUrl;
        this.presentationUrl = presentationUrl;
        this.date = date;
        //this.thumbnailDrawable = null;
    }

    public String getSharedContent()
    {
        return NetworkConnector.PAELLA_UI_URL + "browse.html?episode=" + id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public String getPresenterUrl() {
        return presenterUrl;
    }

    public void setPresenterUrl(String presenterUrl) {
        this.presenterUrl = presenterUrl;
    }

    public String getPresentationUrl() {
        return presentationUrl;
    }

    public void setPresentationUrl(String presentationUrl) {
        this.presentationUrl = presentationUrl;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    /*public Drawable getThumbnailDrawable() {
        return thumbnailDrawable;
    }

    public void setThumbnailDrawable(Drawable thumbnailDrawable) {
        this.thumbnailDrawable = thumbnailDrawable;
    }*/
}