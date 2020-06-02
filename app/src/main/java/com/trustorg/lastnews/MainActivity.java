package com.trustorg.lastnews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<List<News>> {
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mSearchQuery;
    RecyclerView mRecyclerView;
    TextView mEmptyStateTextView;
    View mLoadingIndicator;
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String NEWS_REQUEST_URL = "https:/content.guardianapis.com/search?api-key=3b6f1823-9d33-46b3-aea0-b19456d6545e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mEmptyStateTextView = findViewById(R.id.empty_view);
        mLoadingIndicator = findViewById(R.id.loading_indicator);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(new ArrayList<News>(), new MyAdapter.OnItemClickListener() {
            @Override public void onItemClick(News news) {
                String url = news.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

        return new MyLoader(this, NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        mLoadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setVisibility(View.VISIBLE);
        mEmptyStateTextView.setText(R.string.no_news);

        mAdapter.clear();

        if (newsList != null && !newsList.isEmpty()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setVisibility(View.GONE);
            mAdapter.addAll(newsList);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchQuery = query;
        getLoaderManager().restartLoader(0, null, this);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.btn_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }


}