package com.example.maobuidinh.sliderintro.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mao.buidinh on 7/13/2017.
 */

public class PrefManager {

    private static final String TAG = PrefManager.class.getSimpleName();

    protected static PrefManager instance = null;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "PrefSliderIntro";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public static PrefManager getInstance(Context context) {
        if (instance == null){
            instance = new PrefManager(context.getApplicationContext()); // !!! always getApplicationContext().
        }
        return instance;
    }

//    public static PrefManager getInstance(){
//        if (instance != null)
//            return instance;
//
//        // option 1:
//        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
//
//        // option 2:
//        return getInstance(MainActivity.getApplicationContext());
//    }

    private PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
