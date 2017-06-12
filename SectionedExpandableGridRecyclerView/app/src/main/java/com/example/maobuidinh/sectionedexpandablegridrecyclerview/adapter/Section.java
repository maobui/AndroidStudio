package com.example.maobuidinh.sectionedexpandablegridrecyclerview.adapter;

/**
 * Created by maobuidinh on 6/12/2017.
 */

public class Section {

    private  String name;
    public boolean isExpanded;

    public Section(String name) {
        this.name = name;
        isExpanded = true;
    }

    public String getName() {
        return name;
    }
}
