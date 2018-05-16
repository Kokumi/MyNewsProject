package com.debruyckere.florian.mynews.model;

import java.util.Date;

/**
 * Created by Debruyckère Florian on 10/05/2018.
 */
public class News {
    private String mTitle;
    private String mTheme;
    private String mDescription;
    private Date mDate;
    private String mUrl;
    private String mImage;


    public News(String titre, String theme, Date date, String url, String image) {
        mTitle = titre;
        mTheme = theme;
        mDate = date;
        mUrl = url;
        mImage = image;
    }

    public News(String titre, String theme, String description, Date date, String url, String image) {
        mTitle = titre;
        mTheme = theme;
        mDescription = description;
        mDate = date;
        mUrl = url;
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String titre) {
        mTitle = titre;
    }

    public String getTheme() {
        return mTheme;
    }

    public void setTheme(String theme) {
        mTheme = theme;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }
}
