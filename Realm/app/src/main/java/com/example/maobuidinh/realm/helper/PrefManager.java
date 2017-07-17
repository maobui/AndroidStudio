package com.example.maobuidinh.realm.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mao.buidinh on 7/17/2017.
 */

public class PrefManager {

    private static final String PRE_LOAD = "preLoad";
    private static final String PREFS_NAME = "prefs";
    private static PrefManager instance;
    private final SharedPreferences sharedPreferences;

    public PrefManager(Context context) {

        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static PrefManager with(Context context) {

        if (instance == null) {
            instance = new PrefManager(context);
        }
        return instance;
    }

    public void setPreLoad(boolean isPreLoad) {

        sharedPreferences
                .edit()
                .putBoolean(PRE_LOAD, isPreLoad)
                .apply();
    }

    public boolean getPreLoad(){
        return sharedPreferences.getBoolean(PRE_LOAD, false);
    }

}
