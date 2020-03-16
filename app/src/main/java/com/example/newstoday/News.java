package com.example.newstoday;

import java.util.Date;

public class News {

    private String mType;
    private String mTitle;
    private String mUrl;
    private Date mDate;

    public News(String type, String title, String url, Date date) {
        this.mDate = date;
        this.mTitle = title;
        this.mType = type;
        this.mUrl = url;
    }

    public String getType() {
        return this.mType;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public Date getDate() {
        return this.mDate;
    }

}
