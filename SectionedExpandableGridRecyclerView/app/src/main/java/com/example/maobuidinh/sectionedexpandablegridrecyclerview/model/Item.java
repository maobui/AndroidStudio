package com.example.maobuidinh.sectionedexpandablegridrecyclerview.model;

/**
 * Created by maobuidinh on 6/12/2017.
 */

public class Item {

    private int id;
    private String name;

    public Item(String name, int id ) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
