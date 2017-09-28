package com.mezi.meziassignment.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.LruCache;

import com.mezi.meziassignment.base.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by navalkishoreb on 9/27/2017.
 */

public class ImageDownload implements Task<Bitmap> {
    private final static LruCache<String, Bitmap> cache = new LruCache<>(5);
    private final static Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public Runnable build(String link, OnTaskCompleteListener<Bitmap> onTaskCompleteListener, OnTaskErrorListener onTaskErrorListener) {
        return () -> {
            if (link != null) {
                Bitmap bitmap = cache.get(link);
                if (bitmap == null) {
                    fetchBitmap(link, onTaskCompleteListener, onTaskErrorListener);
                } else {
                    Log.d("Mezi", "image fetched from cache");
                    postBitmap(onTaskCompleteListener, bitmap);
                }
            } else {
                postError(onTaskErrorListener, "Link provided was NULL");
            }
        };
    }

    private void postBitmap(OnTaskCompleteListener<Bitmap> onTaskCompleteListener, Bitmap bitmap) {
        if (onTaskCompleteListener != null) {
            handler.post(() -> onTaskCompleteListener.onTaskComplete(bitmap));
        }
    }

    private void postError(OnTaskErrorListener onTaskErrorListener, String error) {
        if (onTaskErrorListener != null) {
            handler.post(() -> onTaskErrorListener.onTaskError(error));
        }
    }

    private void fetchBitmap(String link, OnTaskCompleteListener<Bitmap> onTaskCompleteListener, OnTaskErrorListener onTaskErrorListener) {
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if (bitmap != null) {
                cache.put(link, bitmap);
                postBitmap(onTaskCompleteListener, bitmap);
            } else {
                postError(onTaskErrorListener, "Bitmap received NULL");
            }
//                Thread.sleep(1000);

        } catch (IOException e) {
            e.printStackTrace();
            postError(onTaskErrorListener, e.getMessage());
        }
    }
}
