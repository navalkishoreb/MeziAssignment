package com.mezi.meziassignment.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by navalkishoreb on 9/26/2017.
 */

final class FifoThreadPool implements ThreadPool {

    /**
     * - Ability to queue a task which will get executed asynchronously in a different thread
     * - Say we are scheduling 3 tasks (Task1, Task2, Task3).
     * - The thread pool should execute them in order meaning Task2 should executed after the Task1 is done and Task3 should get executed after Task2 is done and so on.
     * (above statement justifies that execution is occurring in single thread. Otherwise task should have been independent)
     * - Make sure that the Threadpool uses absolute minimum number of threads.
     */


    private final ExecutorService executor;


    FifoThreadPool() {
        //single thread executor since demand for sequential execution of tasks;
//        executor = Executors.newSingleThreadExecutor();
        executor = Executors.newFixedThreadPool(5);
    }

    @Override
    public void queueTask(Runnable runnable) {
        executor.execute(runnable);
    }


    @Override
    public void shutDown() {
        executor.shutdownNow();
    }
}
