package com.me.bui.mvvm.datamodel;

import android.support.annotation.NonNull;

import com.me.bui.mvvm.model.Language;
import static  com.me.bui.mvvm.model.Language.LanguageCode;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by mao.bui on 1/17/2018.
 */

public interface IDataModel {

    @NonNull
    Observable<List<Language>> getSupportedLanguages();

    @NonNull
    Observable<String> getGreetingByLanguageCode(@NonNull final LanguageCode code);
}
