package com.kevinhodges.donote.model;

import android.app.Activity;
import android.widget.Toast;

import com.firebase.client.Firebase;

/**
 * Created by Kevin on 3/20/2016.
 */
public class Note {

    private String author;
    private String title;
    private String content;
    private String id;

    public Note() {

    }

    public Note(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

//    public Note(String author, String title, String content, String id) {
//        this.author = author;
//        this.title = title;
//        this.content = content;
//        this.id = id;
//    }



    public void updateNote(Activity activity,
                           Firebase mNoteBeingEdited,
                           String noteAuthorExtra,
                           String updatedTitleString,
                           String updatedContentString) {

        if (updatedTitleString.equals("")) {
            Toast.makeText(activity, "Make sure to enter a title first", Toast.LENGTH_LONG).show();

        } else if (updatedContentString.equals("")) {
            Toast.makeText(activity, "Oops, this note is empty!", Toast.LENGTH_LONG).show();

        } else {
            Note editedNote = new Note(noteAuthorExtra, updatedTitleString, updatedContentString);
            mNoteBeingEdited.setValue(editedNote);
            activity.finish();
        }
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
