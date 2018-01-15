package com.me.bui.mvpvsmvvp.mvvm;

import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.me.bui.mvpvsmvvp.model.IModel;

import io.reactivex.Observable;


/**
 * Created by mao.bui on 1/15/2018.
 */

public class ViewModel {

    @NonNull
    public final IModel mModel;

    public ViewModel(@NonNull final IModel mModel) {
        this.mModel = mModel;
    }

    @NonNull
    public Observable<String> getGreeting() {
        return mModel.getGreeting();
    }
}
