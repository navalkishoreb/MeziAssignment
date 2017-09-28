package com.mezi.meziassignment.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.mezi.meziassignment.base.Task;
import com.mezi.meziassignment.base.ThreadPoolFactory;

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
            if (link != null) {
                Bitmap bitmap = ThreadPoolFactory.getPool().getImageCache().get(link);
                if (bitmap == null) {
                    fetchBitmap(link, onTaskCompleteListener, onTaskErrorListener);
                } else {
                    Log.d("Mezi", "image fetched from cache");
                    postBitmap(onTaskCompleteListener, bitmap, link);
                }
            } else {
                postError(onTaskErrorListener, "Link provided was NULL", null);
            }
        };
    }

    private void postBitmap(OnTaskCompleteListener<Bitmap> onTaskCompleteListener, Bitmap bitmap, String link) {
        if (onTaskCompleteListener != null) {
            ThreadPoolFactory.getPool().getUiHandler().post(() -> onTaskCompleteListener.onTaskComplete(bitmap, link));
        }
    }

    private void postError(OnTaskErrorListener onTaskErrorListener, String error, String link) {
        if (onTaskErrorListener != null) {
            ThreadPoolFactory.getPool().getUiHandler().post(() -> onTaskErrorListener.onTaskError(error, link));
        }
    }

    private void fetchBitmap(String link, OnTaskCompleteListener<Bitmap> onTaskCompleteListener, OnTaskErrorListener onTaskErrorListener) {
        try {
            Log.i("Mezi", "Fetching bitmap from network... " + link);
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if (bitmap != null) {
                ThreadPoolFactory.getPool().getImageCache().put(link, bitmap);
                Log.i("Mezi", "Posting bitmap.. " + link);
                postBitmap(onTaskCompleteListener, bitmap, link);
            } else {
                Log.e("Mezi", "Posting error " + link);
                postError(onTaskErrorListener, "Link provided was NULL", "Bitmap received NULL");
            }
//                Thread.sleep(1000);

        } catch (IOException e) {
            e.printStackTrace();
            postError(onTaskErrorListener, "Link provided was NULL", e.getMessage());
        }
    }
}
