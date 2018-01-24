package com.me.bui.mvvm;

import android.app.Application;
import android.support.annotation.NonNull;

import com.me.bui.mvvm.datamodel.DataModel;
import com.me.bui.mvvm.datamodel.IDataModel;
import com.me.bui.mvvm.schedulers.SchedulerProvider;
import com.me.bui.mvvm.viewmodel.MainViewModel;

/**
 * Created by mao.bui on 1/17/2018.
 */

public class MVVMApplication extends Application{

    @NonNull
    private IDataModel mDataModel;

    public MVVMApplication() {
        this.mDataModel = new DataModel();
    }

    @NonNull
    public IDataModel getDataModel() {
        return this.mDataModel;
    }

    @NonNull
    public SchedulerProvider getSchedulerProvider(){
        return SchedulerProvider.getInstance();
    }

    public MainViewModel getViewModel() {
        return new MainViewModel(getDataModel(), getSchedulerProvider());
    }
}
