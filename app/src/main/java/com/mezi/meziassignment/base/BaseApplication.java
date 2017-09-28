package com.mezi.meziassignment.base;

import android.app.Application;

/**
 * Created by navalkishoreb on 9/28/2017.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ThreadPoolFactory.getPool();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ThreadPoolFactory.getPool().shutDown();
    }
}
