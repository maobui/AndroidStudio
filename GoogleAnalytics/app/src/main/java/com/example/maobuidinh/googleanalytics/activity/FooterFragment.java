package com.example.maobuidinh.googleanalytics.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maobuidinh.googleanalytics.R;
import com.example.maobuidinh.googleanalytics.app.MainApplication;

/**
 * Created by mao.buidinh on 7/20/2017.
 */

public class FooterFragment extends Fragment {

    public FooterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_footer, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MainApplication.getInstance().trackScreenView("Footer Fragment");
    }
}
