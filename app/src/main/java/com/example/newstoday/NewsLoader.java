package com.example.newstoday;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String HTTP_URL;

    public NewsLoader(Context context, String url) {
        super(context);
        HTTP_URL = url;
    }
    @Override
    public List<News> loadInBackground() {
        if (HTTP_URL.length() < 1 || HTTP_URL == null) {
            return null;
        }
        ArrayList<News> news = QueryUtils.extractNews(HTTP_URL);
        return news;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
