package com.me.bui.mvvm.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mao.bui on 1/17/2018.
 */

public class SchedulerProvider implements ISchedulerProvider {

    @NonNull
    private static SchedulerProvider INSTANCE;

    public SchedulerProvider() {
    }

    @NonNull
    public static SchedulerProvider getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SchedulerProvider();
        return INSTANCE;
    }

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
