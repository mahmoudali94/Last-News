package com.trustorg.lastnews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;


public class MyLoader extends AsyncTaskLoader<List<News>> {
    private String mUrl;

    public MyLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl.length() < 1 || mUrl == null) {
            return null;
        }
        List<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}