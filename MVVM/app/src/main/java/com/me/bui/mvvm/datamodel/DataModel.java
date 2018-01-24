package com.me.bui.mvvm.datamodel;

import android.support.annotation.NonNull;

import com.me.bui.mvvm.model.Language;
import static  com.me.bui.mvvm.model.Language.LanguageCode;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by mao.bui on 1/17/2018.
 */

public class DataModel implements IDataModel {
    @NonNull
    @Override
    public Observable<List<Language>> getSupportedLanguages() {
        return Observable.fromCallable(this::getLanguages);
    }

    @NonNull
    @Override
    public Observable<String> getGreetingByLanguageCode(@NonNull Language.LanguageCode code) {
        switch (code) {
            case DE:
                return Observable.just("Guten Tag!");
            case EN:
                return Observable.just("Hello!");
            case HR:
                return Observable.just("Zdravo!");
            default:
                return Observable.empty();
        }
    }

    @NonNull
    private List<Language> getLanguages() {
        return Arrays.asList(new Language("English", LanguageCode.EN),
                            new Language("German", LanguageCode.DE),
                            new Language("Slovakian", LanguageCode.HR));
    }
}
