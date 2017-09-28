package com.mezi.meziassignment.base;

/**
 * Created by navalkishoreb on 9/26/2017.
 */

public interface Task<T> {
    Runnable build(String link, OnTaskCompleteListener<T> onTaskCompleteListener, OnTaskErrorListener onTaskErrorListener);

    public interface OnTaskCompleteListener<T> {
        void onTaskComplete(T result, String link);
    }

    public interface OnTaskErrorListener {
        void onTaskError(String error, String link);
    }
}
