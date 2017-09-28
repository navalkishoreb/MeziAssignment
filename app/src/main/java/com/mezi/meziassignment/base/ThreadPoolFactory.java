package com.mezi.meziassignment.base;

/**
 * Created by navalkishoreb on 9/26/2017.
 */

public abstract class ThreadPoolFactory implements ThreadPool {

    private static ThreadPool threadPool;

    public static ThreadPool getPool() {
        if (threadPool == null) {
            synchronized (ThreadPool.class) {
                if (threadPool == null) {
                    threadPool = new FifoThreadPool();
                }
            }
        }
        return threadPool;
    }
}
