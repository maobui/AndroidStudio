package com.me.bui.mvvm.viewmodel;

import android.support.annotation.NonNull;

import com.me.bui.mvvm.datamodel.IDataModel;
import com.me.bui.mvvm.model.Language;
import com.me.bui.mvvm.schedulers.ISchedulerProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by mao.bui on 1/17/2018.
 */

public class MainViewModel {

    @NonNull
    private final IDataModel mDataModel;

    @NonNull
    private final BehaviorSubject<Language> mSelectedLanguage = BehaviorSubject.create();

    @NonNull
    private final ISchedulerProvider mSchedulerProvider;

    public MainViewModel(@NonNull IDataModel dataModel, @NonNull ISchedulerProvider schedulerProvider) {
        mDataModel = dataModel;
        mSchedulerProvider = schedulerProvider;
    }

    @NonNull
    public Observable<String> getGreeting() {
        return mSelectedLanguage
                .observeOn(mSchedulerProvider.computation())
                .map(Language::getCode)
                .flatMap(mDataModel::getGreetingByLanguageCode);
    }

    @NonNull
    public Observable<List<Language>> getSupportedLanguages() {
        return mDataModel.getSupportedLanguages();
    }

    public void languageSelected(@NonNull final Language language) {
        mSelectedLanguage.onNext(language);
    }
}
