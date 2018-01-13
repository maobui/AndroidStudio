package com.me.bui.mvpvsmvvp.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.me.bui.mvpvsmvvp.MVApplication;
import com.me.bui.mvpvsmvvp.R;
import com.me.bui.mvpvsmvvp.model.IModel;

public class MainActivity extends AppCompatActivity implements IView{

    @NonNull
    private IPresenter mIPresenter;

    @Nullable
    private TextView m_txt_greeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIPresenter = new Presenter(getModel(), this);
        setupViews();
    }

    private void setupViews() {
        m_txt_greeting = findViewById(R.id.txt_greeting);
    }

    @Override
    public void setGreeting(@NonNull String greeting) {
        assert m_txt_greeting != null;
        m_txt_greeting.setText(greeting);
    }

    @NonNull
    private IModel getModel() {
        return ((MVApplication)getApplication()).getModel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIPresenter.unBind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIPresenter.bind();
    }
}
