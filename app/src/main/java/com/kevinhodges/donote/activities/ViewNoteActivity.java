package com.kevinhodges.donote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.kevinhodges.donote.R;
import com.kevinhodges.donote.model.Note;
import com.kevinhodges.donote.utils.Constants;

public class ViewNoteActivity extends AppCompatActivity {

    private static final String TAG = "ViewNoteActivity";
    private String noteAuthorExtra;
    private String noteTitleExtra;
    private String noteContentExtra;
    private AutoCompleteTextView noteTitleAC;
    private AutoCompleteTextView noteContentAC;
    private String noteIdString;
    private Firebase mFirebase;
    private String mUserId;
    private Firebase mCurrentUser;
    private Firebase mCurrentUserNotes;
    private Firebase mNoteBeingEdited;
    private Toolbar toolbar;
    private String noteReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        Intent intent = getIntent();
        noteAuthorExtra = intent.getStringExtra("noteAuthor");
        noteTitleExtra = intent.getStringExtra("noteTitle");
        noteContentExtra = intent.getStringExtra("noteContent");
        noteReference = intent.getStringExtra("noteReference");

        mFirebase = new Firebase(Constants.FIREBASE_URL);
        mUserId = mFirebase.getAuth().getUid();
        mCurrentUser = new Firebase(mFirebase + "/users/" + mUserId);
        mCurrentUserNotes = new Firebase(Constants.FIREBASE_URL + "/users/" + mUserId + "/notes/");
        mNoteBeingEdited = new Firebase(noteReference);


        //UI Declarations///////////////////////////////////////////////////////////
        noteTitleAC = (AutoCompleteTextView) findViewById(R.id.ac_view_note_title);
        noteContentAC = (AutoCompleteTextView) findViewById(R.id.ac_view_note_content);
        noteTitleAC.setText(noteTitleExtra);
        noteContentAC.setText(noteContentExtra);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ///////////////////////////////////////////////////////////////////////////



        Log.d(TAG, "author = " + noteAuthorExtra);
        Log.d(TAG, "title = " + noteTitleExtra);
        Log.d(TAG, "content = " + noteContentExtra);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {

            String noteTitleString = noteTitleAC.getText().toString();
            String noteContentString = noteContentAC.getText().toString();

            if (noteTitleString.equals("")) {
                Toast.makeText(ViewNoteActivity.this, "Make sure to enter a title first", Toast.LENGTH_LONG).show();

            } else if (noteContentString.equals("")) {
                Toast.makeText(ViewNoteActivity.this, "Oops, this note is empty!", Toast.LENGTH_LONG).show();

            } else {
                Note editedNote = new Note(noteAuthorExtra, noteTitleString, noteContentString);
                mNoteBeingEdited.setValue(editedNote);
                finish();
            }

            return true;
        }

        if (id == R.id.discard) {
            finish();
            return true;
        }

        if (id == R.id.invite) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
