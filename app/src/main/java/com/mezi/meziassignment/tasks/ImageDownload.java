package com.mezi.meziassignment.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.mezi.meziassignment.base.CacheFactory;
import com.mezi.meziassignment.base.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by navalkishoreb on 9/27/2017.
 */

public class ImageDownload implements Task<Bitmap> {

    @Override
    public Runnable build(String link, OnTaskCompleteListener<Bitmap> onTaskCompleteListener, OnTaskErrorListener onTaskErrorListener) {
        return () -> {
            Handler handler = new Handler(Looper.getMainLooper());
            if (link != null) {
                Bitmap bitmap = CacheFactory.getCache().get(link);
                if (bitmap == null) {
                    fetchBitmap(link, onTaskCompleteListener, onTaskErrorListener,handler);
                } else {
                    Log.d("Mezi", "image fetched from cache");
                    postBitmap(onTaskCompleteListener, bitmap, link, handler);
                }
            } else {
                postError(onTaskErrorListener, "Link provided was NULL", null, handler);
            }
        };
    }

    private void postBitmap(OnTaskCompleteListener<Bitmap> onTaskCompleteListener, Bitmap bitmap, String link, Handler handler) {
        if (onTaskCompleteListener != null) {
            handler.post(() -> onTaskCompleteListener.onTaskComplete(bitmap, link));
        }
    }

    private void postError(OnTaskErrorListener onTaskErrorListener, String error, String link, Handler handler) {
        if (onTaskErrorListener != null) {
            handler.post(() -> onTaskErrorListener.onTaskError(error, link));
        }
    }

    private void fetchBitmap(String link, OnTaskCompleteListener<Bitmap> onTaskCompleteListener, OnTaskErrorListener onTaskErrorListener, Handler handler) {
        try {
            Log.i("Mezi", "Fetching bitmap from network... " + link);
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if (bitmap != null) {
                CacheFactory.getCache().put(link, bitmap);
                Log.i("Mezi", "Posting bitmap.. " + link);
                postBitmap(onTaskCompleteListener, bitmap, link, handler);
            } else {
                Log.e("Mezi", "Posting error " + link);
                postError(onTaskErrorListener, "Link provided was NULL", "Bitmap received NULL", handler);
            }
//                Thread.sleep(1000);

        } catch (IOException e) {
            e.printStackTrace();
            postError(onTaskErrorListener, "Link provided was NULL", e.getMessage(), handler);
        }
    }
}
