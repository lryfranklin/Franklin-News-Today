package com.example.newstoday;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BusinessFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<News>> {

    private Context mContext;
    private NewsAdapter adapter;
    private TextView empty_view;
    private ProgressBar mProgressBar;
    private static final long mDayInMs = 1000 * 60 * 60 * 24;

    @NonNull
    @Override
    public androidx.loader.content.Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        long today = Calendar.getInstance().getTimeInMillis();
        long weekBefore = today - (7 * mDayInMs);
        String todayString = changeDateInStringForm(today);
        String weekBeforeString = changeDateInStringForm(weekBefore);
        String requestUrl = getResources().getString(R.string.base_url) +
                getResources().getString(R.string.business) + "&" +
                getResources().getString(R.string.from_date) + weekBeforeString + "&" +
                getResources().getString(R.string.to_date) + todayString + "&" +
                getResources().getString(R.string.api_key);
        Uri baseUri = Uri.parse(requestUrl);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        return new NewsLoader(mContext, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<News>> loader, List<News> news) {
        empty_view.setText(R.string.no_news_found);
        mProgressBar.setVisibility(View.INVISIBLE);
        // Clear the adapter of previous earthquake data
        adapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<News>> loader) {
        adapter.clear();
    }

    public BusinessFragment(Context context) {
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.news_list, container, false);
        rootView.setBackgroundColor(getResources().getColor(R.color.background_white));

        ListView listView = (ListView) rootView.findViewById(R.id.news_list);

        empty_view = (TextView) rootView.findViewById(R.id.empty_list);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        listView.setEmptyView(empty_view);

        ConnectivityManager cm = (ConnectivityManager) getActivity().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            empty_view.setText(R.string.no_internet);
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {
            adapter = new NewsAdapter(getActivity(), new ArrayList<News>(), R.color.background_business,
                    R.color.context_business, R.color.title_business);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    News news = adapter.getItem(position);
                    String url = news.getUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });

            LoaderManager loaderManager = LoaderManager.getInstance(this);
            loaderManager.initLoader(1, null, this);
        }

        return rootView;
    }

    public String changeDateInStringForm(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
