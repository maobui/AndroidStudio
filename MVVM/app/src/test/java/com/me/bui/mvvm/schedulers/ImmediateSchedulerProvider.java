package com.me.bui.mvvm.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mao.bui on 1/24/2018.
 */

public class ImmediateSchedulerProvider implements ISchedulerProvider {

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();//.immediate();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();//.immediate();
    }

}
