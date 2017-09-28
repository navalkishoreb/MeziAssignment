package com.mezi.meziassignment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.mezi.meziassignment.R;
import com.mezi.meziassignment.base.Task;
import com.mezi.meziassignment.base.ThreadPoolFactory;
import com.mezi.meziassignment.models.Api;
import com.mezi.meziassignment.models.FlickrRecent;
import com.mezi.meziassignment.tasks.RecentImages;

/**
 * Created by navalkishoreb on 9/26/2017.
 */

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ListAdapter adapter;
    private Task<FlickrRecent> recentImages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.home);
        setContentView(R.layout.list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        adapter = new ListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(Home.this));
        recyclerView.setAdapter(adapter);
        recentImages = new RecentImages();
        fetchImages(recentImages);


    }

    private void fetchImages(Task<FlickrRecent> recentImages) {
        showProgressBar();
        ThreadPoolFactory.getPool().queueTask(recentImages.build(Api.RECENT, result -> {
            adapter.setPhotos(result.getPhotos().getPhoto().subList(0, 15));
            hideProgressBar();
        }, error -> {
            Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show();
            hideProgressBar();
        }));
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                adapter.clear();
                fetchImages(recentImages);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
