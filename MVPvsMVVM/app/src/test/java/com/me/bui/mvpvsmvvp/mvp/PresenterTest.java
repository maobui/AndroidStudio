package com.me.bui.mvpvsmvvp.mvp;

import com.me.bui.mvpvsmvvp.model.IModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.junit.Assert.*;

/**
 * Created by mao.bui on 1/25/2018.
 */
public class PresenterTest {

    @Mock
    private IModel mDataModel;

    @Mock
    private IView mView;

    private Presenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mPresenter = new Presenter(mDataModel, mView);
    }

    @Test
    public void testGetGreeting_set_whenViewBinded() {
        String greeting = "Hello!";
        Mockito.when(mDataModel.getGreeting()).thenReturn(Observable.just(greeting));

        mPresenter.bind();

        Mockito.verify(mView).setGreeting(greeting);
    }

}