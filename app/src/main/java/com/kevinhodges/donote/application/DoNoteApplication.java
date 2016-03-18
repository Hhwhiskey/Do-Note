package com.kevinhodges.donote.application;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Kevin on 3/17/2016.
 */
public class DoNoteApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
