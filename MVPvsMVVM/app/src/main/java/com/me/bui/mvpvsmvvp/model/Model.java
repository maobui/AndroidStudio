package com.me.bui.mvpvsmvvp.model;

import android.support.annotation.NonNull;

import io.reactivex.Observable;

/**
 * Created by mao.bui on 1/13/2018.
 */

public class Model implements IModel{
    @NonNull
    @Override
    public Observable<String> getGreeting() {
        return Observable.just("Hello Greeting ...");
    }
}
