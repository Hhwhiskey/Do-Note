package com.kevinhodges.donote.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.kevinhodges.donote.R;
import com.kevinhodges.donote.model.Note;
import com.kevinhodges.donote.utils.Constants;

public class NewNoteActivity extends AppCompatActivity {

    private AutoCompleteTextView noteContentAC;
    private String noteTitleString;
    private String noteContentString;
    private Firebase firebase;
    private String userIdString;
    private Firebase currentUserNotesFirebase;
    private Toolbar toolbar;
    private AutoCompleteTextView noteTitleAC;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        firebase = new Firebase(Constants.FIREBASE_URL);
        userIdString = firebase.getAuth().getUid();
        currentUserNotesFirebase = new Firebase(Constants.FIREBASE_URL + "/users/" + userIdString + "/notes/");

        builder = new AlertDialog.Builder(NewNoteActivity.this);

        //UI////////////////////////////////////////////////////
        noteTitleAC = (AutoCompleteTextView) findViewById(R.id.ac_new_note_title);
        noteContentAC = (AutoCompleteTextView) findViewById(R.id.ac_new_note_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ///////////////////////////////////////////////////////

    }

    public void backDiscardWarningDialog() {

        builder.setTitle("Discard changes?");
        builder.setMessage("Changes have been made to this note. Would you like to save them?");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Note note = new Note(userIdString, noteTitleString, noteContentString);
                currentUserNotesFirebase.push().setValue(note);
                finish();
            }
        });

        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }
        );

        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            noteTitleString = noteTitleAC.getText().toString();
            noteContentString = noteContentAC.getText().toString();

            if (noteTitleString.equals("")) {
                Toast.makeText(NewNoteActivity.this, "Make sure to enter a title first", Toast.LENGTH_LONG).show();

            } else if (noteContentString.equals("")) {
                Toast.makeText(NewNoteActivity.this, "Oops, this note is empty!", Toast.LENGTH_LONG).show();

            } else {
                Note note = new Note(userIdString, noteTitleString, noteContentString);
                currentUserNotesFirebase.push().setValue(note);
                finish();
            }

            return true;
        }

        if (id == R.id.discard) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        noteTitleString = noteTitleAC.getText().toString();
        noteContentString = noteContentAC.getText().toString();

        if (!noteTitleString.equals("") || (!noteContentString.equals(""))) {

            backDiscardWarningDialog();

        } else {
            finish();
        }
    }
}
