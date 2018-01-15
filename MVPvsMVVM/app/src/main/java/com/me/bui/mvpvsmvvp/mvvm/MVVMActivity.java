package com.me.bui.mvpvsmvvp.mvvm;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.me.bui.mvpvsmvvp.MVApplication;
import com.me.bui.mvpvsmvvp.R;
import com.me.bui.mvpvsmvvp.model.IModel;

import io.reactivex.disposables.CompositeDisposable;

public class MVVMActivity extends AppCompatActivity {

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    @NonNull
    private ViewModel mViewModel;

    @Nullable
    private TextView m_txt_greeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = new ViewModel(getModel());
        setupViews();
    }

    private void setupViews() {
        m_txt_greeting = findViewById(R.id.txt_greeting);
    }

    public void setGreeting(@NonNull String greeting) {
        assert m_txt_greeting != null;
        m_txt_greeting.setText(greeting + "MVVM");
    }

    @NonNull
    private IModel getModel() {
        return ((MVApplication)getApplication()).getModel();
    }

    @Override
    protected void onPause() {
        unBind();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }

    private void bind() {
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(mViewModel.getGreeting().subscribe(this::setGreeting));
    }

    private void unBind() {
        mCompositeDisposable.clear();
    }
}
