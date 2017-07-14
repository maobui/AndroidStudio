package com.example.maobuidinh.sliderintro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.maobuidinh.sliderintro.R;
import com.example.maobuidinh.sliderintro.helper.PrefManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private PrefManager mPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefManager = PrefManager.getInstance(this);

        if (mPrefManager.isFirstTimeLaunch()) {
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            mPrefManager.setFirstTimeLaunch(false);
            finish();
        }
        setContentView(R.layout.activity_main);

    }
}
