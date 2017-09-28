package com.mezi.meziassignment.tasks;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.mezi.meziassignment.base.Task;
import com.mezi.meziassignment.models.FlickrRecent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by navalkishoreb on 9/27/2017.
 */

public class RecentImages implements Task<FlickrRecent> {
    @Override
    public Runnable build(String link, OnTaskCompleteListener<FlickrRecent> onTaskCompleteListener, OnTaskErrorListener onTaskErrorListener) {
        return () -> {
            Handler handler = new Handler(Looper.getMainLooper());
            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder json = new StringBuilder();
                String data = "";

                while ((data = reader.readLine()) != null) {
                    json.append(data).append("\n");
                }

                Gson gson = new Gson();
                FlickrRecent flickrRecent = gson.fromJson(json.toString(), FlickrRecent.class);
                if (onTaskCompleteListener != null) {
                    handler.post(() -> onTaskCompleteListener.onTaskComplete(flickrRecent));
                }

            } catch (IOException e) {
                e.printStackTrace();
                if (onTaskErrorListener != null) {
                    handler.post(() -> onTaskErrorListener.onTaskError(e.getMessage()));
                }
            }
        };
    }
}
