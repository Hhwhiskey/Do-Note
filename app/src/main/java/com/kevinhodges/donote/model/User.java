package com.kevinhodges.donote.model;

/**
 * Created by Kevin on 4/5/2016.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "notes" })
public class User {


    private String email;

    public User() {

    }

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
