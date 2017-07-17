package com.example.maobuidinh.realm.adapter;

import android.support.v7.widget.RecyclerView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;

/**
 * Created by mao.buidinh on 7/17/2017.
 */

public abstract class RealmRecyclerViewAdapter<T extends RealmObject> extends RecyclerView.Adapter {

    private RealmBaseAdapter<T> mRealmBaseAdapter;

    public T getItem(int position){
        return mRealmBaseAdapter.getItem(position);
    }

    public RealmBaseAdapter<T> getRealmAdapter(){
        return mRealmBaseAdapter;
    }

    public void setRealmAdapter( RealmBaseAdapter<T> adapter){
        mRealmBaseAdapter = adapter;
    }
}
