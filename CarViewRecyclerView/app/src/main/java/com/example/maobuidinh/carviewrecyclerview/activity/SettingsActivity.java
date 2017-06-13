package com.example.maobuidinh.carviewrecyclerview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.maobuidinh.carviewrecyclerview.fragment.SettingsFragment;

/**
 * Created by mao.buidinh on 6/13/2017.
 */

public class SettingsActivity  extends AppCompatActivity {

    public static final String STAGGERED_GRID_LAYOUT = "staggered_grid_layout";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, new SettingsFragment());
        ft.commit();
    }
}
