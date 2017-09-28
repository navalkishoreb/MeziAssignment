package com.mezi.meziassignment.tasks;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.mezi.meziassignment.base.Task;
import com.mezi.meziassignment.models.FlickrRecent;
import com.mezi.meziassignment.models.Photo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by navalkishoreb on 9/27/2017.
 */

public class RecentImages implements Task<FlickrRecent> {
    //    private final static Handler handler = new Handler(Looper.getMainLooper());
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
                List<Photo> photos = flickrRecent.getPhotos().getPhoto();
                List<Photo> photoList = new ArrayList<>(photos);
                for (Photo photo : photos) {
                    if (photo.getUrlN() == null) {
                        photoList.remove(photo);
                    }
                }
                flickrRecent.getPhotos().setPhoto(photoList);
//                flickrRecent.getPhotos().getPhoto().stream().filter(photo -> photo.getUrlN() == null).forEach(photo -> {
//                    flickrRecent.getPhotos().getPhoto().remove(photo);
//                });
                if (onTaskCompleteListener != null) {
                    handler.post(() -> onTaskCompleteListener.onTaskComplete(flickrRecent, link));
                }

            } catch (IOException e) {
                e.printStackTrace();
                if (onTaskErrorListener != null) {
                    handler.post(() -> onTaskErrorListener.onTaskError(e.getMessage(), link));
                }
            }
        };
    }
}
