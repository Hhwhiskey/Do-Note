package com.kevinhodges.donote.model;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.kevinhodges.donote.activities.ViewNoteActivity;

/**
 * Created by Kevin on 3/20/2016.
 */
public class Note {

    private String author;
    private String title;
    private String content;
    private Firebase editedNoteReference;

    public Note() {

    }

    public Note(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    // Update the current note on Firebase and launch ViewNoteActivity with updated values
    public Firebase updateNote(Activity activity,
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
            editedNoteReference = mNoteBeingEdited.getRef();

            Intent viewNoteIntent = new Intent(activity, ViewNoteActivity.class);
            viewNoteIntent.putExtra("noteTitle", updatedTitleString);
            viewNoteIntent.putExtra("noteContent", updatedContentString);
            viewNoteIntent.putExtra("noteReference", mNoteBeingEdited.toString());
            activity.finish();
            activity.startActivity(viewNoteIntent);
        }

        return editedNoteReference;
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


}
