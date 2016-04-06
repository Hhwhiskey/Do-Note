package com.kevinhodges.donote.model;

/**
 * Created by Kevin on 4/5/2016.
 */
public class User {

    private String email;


    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
