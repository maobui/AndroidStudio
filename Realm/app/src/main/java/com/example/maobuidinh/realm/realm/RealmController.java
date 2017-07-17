package com.example.maobuidinh.realm.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.maobuidinh.realm.helper.BookKey;
import com.example.maobuidinh.realm.model.Book;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mao.buidinh on 7/17/2017.
 */

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

//    public Realm getRealm() {
//
//        return realm;
//    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(Book.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<Book> getBooks() {

        return realm.where(Book.class).findAll();
    }

    //query a single item with the given id
    public Book getBook(String id) {

        return realm.where(Book.class).equalTo(BookKey.ID, id).findFirst();
    }

    //query a single item with the given location
    public Book getBookLocation(int postion) {

        RealmResults<Book>  results = this.getBooks();
        return results.get(postion);
    }

    //check if Book.class is empty
    public boolean hasBooks() {

        return !realm.allObjects(Book.class).isEmpty();
    }

    // remove a book with the given location
    public void removeBookLocation(int position) {
        RealmResults<Book> results = this.getBooks();
        realm.beginTransaction();
        // remove single match
        //results.remove(position);

        //other way.
        Book book = results.get(position);
        book.removeFromRealm();

        realm.commitTransaction();
    }

    // update a book with the given location
    public void updateBookLocation(int position, String author, String title, String imageUrl) {
        RealmResults<Book> results = this.getBooks();
        realm.beginTransaction();
        results.get(position).setAuthor(author);
        results.get(position).setTitle(title);
        results.get(position).setImageUrl(imageUrl);
        realm.commitTransaction();
    }

    // add a book
    public void addBook( Book book){
        realm.beginTransaction();
        realm.copyToRealm(book);
        realm.commitTransaction();
    }

    //query example
    public RealmResults<Book> queryedBooks() {

        return realm.where(Book.class)
                .contains(BookKey.AUTHOR, "Author 0")
                .or()
                .contains(BookKey.TITLE, "Realm")
                .findAll();

    }

    public void close(){
        if (realm != null)
            realm.close();
    }
}
