package com.example.maobuidinh.interview03;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getFragmentManager();
// Or: FragmentManager fragmentManager = getSupportFragmentManager()
            for (int i = 0; i < 5; i++) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FirstFragment fragment = new FirstFragment();
                fragmentTransaction.add(R.id.fragment_container, fragment, "" + i);
                fragmentTransaction.commit();
            }
        }
    }
}
