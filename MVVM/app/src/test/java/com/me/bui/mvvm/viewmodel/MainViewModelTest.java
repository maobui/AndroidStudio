package com.me.bui.mvvm.viewmodel;

import com.me.bui.mvvm.datamodel.IDataModel;
import com.me.bui.mvvm.model.Language;
import com.me.bui.mvvm.model.Language.LanguageCode;
import com.me.bui.mvvm.schedulers.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Created by mao.bui on 1/24/2018.
 */
public class MainViewModelTest {

    @Mock
    private IDataModel mDataModel;

    private MainViewModel mMainViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mMainViewModel = new MainViewModel(mDataModel, new ImmediateSchedulerProvider());
    }

    @Test
    public void testGetSupportedLanguages_emitsCorrectLanguages() {
        Language de = new Language("German", LanguageCode.DE);
        Language en = new Language("English", LanguageCode.EN);
        List<Language> languages = Arrays.asList(de, en);
        Mockito.when(mDataModel.getSupportedLanguages()).thenReturn(Observable.just(languages));

        mMainViewModel.getSupportedLanguages().test().assertNoErrors().assertValue(languages);
    }

    @Test
    public void testGetGreeting_doesNotEmit_whenNoLanguageSet() {
        mMainViewModel.getGreeting().test().assertNoErrors().assertNoValues();
    }

    @Test
    public void testGetGreeting_emitsCorrectGreeting_whenLanguageSet() {
        String enGreeting = "Hello";
        Language en = new Language("English", LanguageCode.EN);
        Mockito.when(mDataModel.getGreetingByLanguageCode(LanguageCode.EN))
                .thenReturn(Observable.just(enGreeting));

        mMainViewModel.languageSelected(en);
        mMainViewModel.getGreeting().test().assertNoErrors().assertValue(enGreeting);
    }

}