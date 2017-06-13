package com.example.maobuidinh.carviewrecyclerview.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import com.example.maobuidinh.carviewrecyclerview.R;
import com.example.maobuidinh.carviewrecyclerview.activity.SettingsActivity;

/**
 * Created by mao.buidinh on 6/13/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat  implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = SettingsFragment.class.getSimpleName();
    private static final String STAGGERED_GRID_LAYOUT_SUMMARY_DEFAULT = "Staggered Grid Layout is a doesn't align layout.";
    private static final String STAGGERED_GRID_LAYOUT_SUMMARY_DEACTIVE = "Grid Layout is a align layout.";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "onSharedPreferenceChanged key : " + key);
        if (key.equals(SettingsActivity.STAGGERED_GRID_LAYOUT)) {
            boolean value = sharedPreferences.getBoolean(key, false);
            Log.d(TAG, "onSharedPreferenceChanged value : " + value);
            Preference staggeredPref = findPreference(key);
            if (value) {
                // Set summary to be the user-description for the selected value
                staggeredPref.setSummary(STAGGERED_GRID_LAYOUT_SUMMARY_DEACTIVE);
            } else  {
                staggeredPref.setSummary(STAGGERED_GRID_LAYOUT_SUMMARY_DEFAULT);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
