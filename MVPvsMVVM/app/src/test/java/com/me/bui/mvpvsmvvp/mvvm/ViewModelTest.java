package com.me.bui.mvpvsmvvp.mvvm;

import com.me.bui.mvpvsmvvp.model.IModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Created by mao.bui on 1/25/2018.
 */
public class ViewModelTest {
    @Mock
    private IModel mDataModel;

    private ViewModel mViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mViewModel = new ViewModel(mDataModel);
    }

    @Test
    public void testGetGreeting_emitsCorrectGreeting() {
        String greeting = "Hello!";
        Mockito.when(mDataModel.getGreeting()).thenReturn(Observable.just(greeting));

        mViewModel.getGreeting().test().assertValue(greeting);
    }

}