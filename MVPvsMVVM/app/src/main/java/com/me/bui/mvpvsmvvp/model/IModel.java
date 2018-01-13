package com.me.bui.mvpvsmvvp.model;

import android.support.annotation.NonNull;

import io.reactivex.Observable;

/**
 * Created by mao.bui on 1/13/2018.
 */

public interface IModel {

    @NonNull
    Observable<String> getGreeting();
}
