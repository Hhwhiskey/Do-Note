package com.kevinhodges.donote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.kevinhodges.donote.utils.Dialogs;

public class EditNoteActivity extends AppCompatActivity {

    private static final String TAG = "EditNoteActivity";
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
    private Firebase mCurrentNoteReference;
    private Toolbar toolbar;
    private String noteReference;
    private AlertDialog.Builder builder;
    private String updatedTitleString;
    private String updatedContentString;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        builder = new AlertDialog.Builder(EditNoteActivity.this);
        note = new Note();

        Intent intent = getIntent();
        noteAuthorExtra = intent.getStringExtra("noteAuthor");
        noteTitleExtra = intent.getStringExtra("noteTitle");
        noteContentExtra = intent.getStringExtra("noteContent");
        noteReference = intent.getStringExtra("noteReference");

        mFirebase = new Firebase(Constants.FIREBASE_URL);
        mUserId = mFirebase.getAuth().getUid();
        mCurrentUser = new Firebase(mFirebase + "/users/" + mUserId);
        mCurrentUserNotes = new Firebase(Constants.FIREBASE_URL + "/users/" + mUserId + "/notes/");
        mCurrentNoteReference = new Firebase(noteReference);

        builder = new AlertDialog.Builder(EditNoteActivity.this);

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
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {

            updatedTitleString = noteTitleAC.getText().toString();
            updatedContentString = noteContentAC.getText().toString();

            note.updateNote(EditNoteActivity.this, mCurrentNoteReference, noteAuthorExtra, updatedTitleString, updatedContentString);

            return true;
        }

        if (id == R.id.discard) {
            finish();
            return true;
        }

        if (id == R.id.invite) {
            Toast.makeText(EditNoteActivity.this, "Soon you will be able to invite friends to collaborate on your notes!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.delete) {

            Dialogs.deleteNoteDialog(EditNoteActivity.this, mCurrentNoteReference);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        updatedTitleString = noteTitleAC.getText().toString();
        updatedContentString = noteContentAC.getText().toString();

        if (!noteTitleExtra.equals(updatedTitleString) || (!noteContentExtra.equals(updatedContentString))) {

            Dialogs.discardOrSaveDialog(EditNoteActivity.this, mCurrentNoteReference, noteAuthorExtra, updatedTitleString, updatedContentString);

        } else {

            Intent viewNoteIntent = new Intent(EditNoteActivity.this, ViewNoteActivity.class);
            viewNoteIntent.putExtra("noteTitle", updatedTitleString);
            viewNoteIntent.putExtra("noteContent", updatedContentString);
            viewNoteIntent.putExtra("noteReference", noteReference);
            finish();
            startActivity(viewNoteIntent);
        }
    }




}
