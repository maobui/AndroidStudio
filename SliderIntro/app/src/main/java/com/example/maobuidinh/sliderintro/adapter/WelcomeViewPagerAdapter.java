package com.example.maobuidinh.sliderintro.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

/**
 * Created by mao.buidinh on 7/14/2017.
 */

public class WelcomeViewPagerAdapter extends PagerAdapter {

    private Context context;
    private int layouts[];

    public WelcomeViewPagerAdapter(Context context, int[] layouts) {
        this.context = context;
        this.layouts = Arrays.copyOf(layouts,layouts.length);
    }

    @Override
    public int getCount() {
        return this.layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(layouts[position], container, false);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
