package com.example.newstoday;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsAdapter extends ArrayAdapter<News> {

    private final static String LOG_TAG = NewsAdapter.class.getSimpleName();
    private int mBackgroundColor;
    private int mContextColor;
    private int mTitleColor;

    public NewsAdapter(Activity context, ArrayList<News> news, int backgoundColor,
                       int contextColor, int titleColor) {
        super(context, 0, news);
        this.mBackgroundColor = backgoundColor;
        this.mContextColor = contextColor;
        this.mTitleColor = titleColor;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        final News news = getItem(position);

        RelativeLayout newsItem = (RelativeLayout) convertView.findViewById(R.id.news_item);
        newsItem.setBackgroundColor(ContextCompat.getColor(getContext(), this.mBackgroundColor));

        TextView newsType = (TextView) convertView.findViewById(R.id.news_type);
        newsType.setText(news.getType());
        newsType.setTextColor(ContextCompat.getColor(getContext(), this.mContextColor));

        TextView newsTitle = (TextView) convertView.findViewById(R.id.news_title);
        newsTitle.setText(news.getTitle());
        newsTitle.setTextColor(ContextCompat.getColor(getContext(), this.mTitleColor));

        TextView newsTime = (TextView) convertView.findViewById(R.id.news_time);
        Date dateObject = news.getDate();
        String dateTime = formatDate(dateObject) + " " + formatTime(dateObject);
        newsTime.setText(dateTime);
        newsTime.setTextColor(ContextCompat.getColor(getContext(), this.mContextColor));

        return convertView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

}
