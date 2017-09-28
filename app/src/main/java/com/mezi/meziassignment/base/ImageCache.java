package com.mezi.meziassignment.base;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by navalkishoreb on 9/28/2017.
 */

public class ImageCache {

    private final static int CACHE_SIZE = 20 * 1024 * 1024;
    private final LruCache<String, Bitmap> cache;

    /*
        This is about LRUCache.
    *  * <p>This class is thread-safe. Perform multiple cache operations atomically by
    * synchronizing on the cache: <pre>   {@code
    *   synchronized (cache) {
    *     if (cache.get(key) == null) {
    *         cache.put(key, value);
    *     }
    *   }}</pre>
    *
    *
    *   So no need to put synchronized methods here.
    *   */


    ImageCache() {
        cache = new LruCache<>(CACHE_SIZE);
    }

    public void put(String key, Bitmap bitmap) {
        cache.put(key, bitmap);
    }


    public Bitmap get(String key) {
        return cache.get(key);
    }
}
