package com.me.bui.mvpvsmvvp;

import android.app.Application;
import android.support.annotation.NonNull;

import com.me.bui.mvpvsmvvp.model.IModel;
import com.me.bui.mvpvsmvvp.model.Model;

/**
 * Created by mao.bui on 1/13/2018.
 */

public class MVApplication extends Application {

    @NonNull
    private IModel mIModel;

    public MVApplication() {
        this.mIModel = new Model();
    }

    @NonNull
    public IModel getModel() {
        return mIModel;
    }
}
