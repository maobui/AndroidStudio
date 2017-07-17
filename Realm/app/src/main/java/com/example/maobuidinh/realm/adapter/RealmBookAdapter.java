package com.example.maobuidinh.realm.adapter;

import android.content.Context;

import com.example.maobuidinh.realm.model.Book;

import io.realm.RealmResults;

/**
 * Created by mao.buidinh on 7/17/2017.
 */

public class RealmBookAdapter extends RealmModelAdapter<Book> {
    public RealmBookAdapter(Context context, RealmResults<Book> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
