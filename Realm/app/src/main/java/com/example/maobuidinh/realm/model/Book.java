package com.example.maobuidinh.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mao.buidinh on 7/17/2017.
 */

public class Book extends RealmObject {

    @PrimaryKey
    private int id;

    private String title;

    private String description;

    private String author;

    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

//    public Book(int id, String title, String description, String author, String imageUrl) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.author = author;
//        this.imageUrl = imageUrl;
//    }
//
//    public Book() {
//    }
//
//    public Book(Book book) {
//        this.id = book.getId();
//        this.title = book.getTitle();
//        this.description = book.getDescription();
//        this.author = book.getAuthor();
//        this.imageUrl = book.getImageUrl();
//    }
}
