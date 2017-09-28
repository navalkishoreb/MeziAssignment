package com.mezi.meziassignment.base;

/**
 * Created by navalkishoreb on 9/28/2017.
 */

public class CacheFactory {

    private static ImageCache cache;

    public static ImageCache getCache() {
        if (cache == null) {
            synchronized (ImageCache.class) {
                if (cache == null) {
                    cache = new ImageCache();
                }
            }
        }
        return cache;
    }
}
