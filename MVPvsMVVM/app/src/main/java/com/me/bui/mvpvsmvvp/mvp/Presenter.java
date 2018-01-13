package com.me.bui.mvpvsmvvp.mvp;

import android.support.annotation.NonNull;

import com.me.bui.mvpvsmvvp.model.IModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.subscriptions.ArrayCompositeSubscription;

/**
 * Created by mao.bui on 1/13/2018.
 */

public class Presenter implements IPresenter {

    @NonNull
    private IModel mModel;

    @NonNull
    private IView mIView;

    private CompositeDisposable mCompositeDisposable;

    public Presenter(@NonNull IModel mModel, @NonNull IView mIView) {
        this.mModel = mModel;
        this.mIView = mIView;
    }

    @Override
    public void bind() {
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(mModel.getGreeting().subscribe(this::setGreeting));
    }

    @Override
    public void unBind() {
        mCompositeDisposable.clear();
    }

    private void setGreeting(@NonNull final String greeting) {
        mIView.setGreeting(greeting);
    }
}
