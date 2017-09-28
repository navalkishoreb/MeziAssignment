package com.mezi.meziassignment.base;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.LruCache;

/**
 * Created by navalkishoreb on 9/26/2017.
 */

public interface ThreadPool {
    void queueTask(Runnable runnable);


    LruCache<String, Bitmap> getImageCache();

    Handler getUiHandler();

    void shutDown();
}
