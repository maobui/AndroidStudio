package com.example.maobuidinh.sectionedexpandablegridrecyclerview.adapter;

import com.example.maobuidinh.sectionedexpandablegridrecyclerview.model.Item;

/**
 * Created by maobuidinh on 6/12/2017.
 */

public interface ItemClickListener {
    void itemClicked(Item item);
    void itemClicked(Section section);
}
