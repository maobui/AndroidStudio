package com.example.maobuidinh.firebasedatabase;
import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by maobuidinh on 6/3/2017.
 */

@IgnoreExtraProperties
public class User {
    public String name;
    public String email;

    public User (){
    }
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
