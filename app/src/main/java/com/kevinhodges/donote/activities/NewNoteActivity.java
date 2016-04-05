package com.kevinhodges.donote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.kevinhodges.donote.R;
import com.kevinhodges.donote.model.Note;
import com.kevinhodges.donote.utils.Constants;

public class NewNoteActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteNewNote;
    private String newNoteString;
    private Firebase firebase;
    private String userId;
    private Firebase currentUserFirebase;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        firebase = new Firebase(Constants.FIREBASE_URL);

        userId = firebase.getAuth().getUid();

        if (userId == null) {
            Toast.makeText(NewNoteActivity.this, "Please sign in first", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(NewNoteActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            currentUserFirebase = new Firebase(Constants.FIREBASE_URL + "/users/" + userId + "/notes/");
        }

        //UI////////////////////////////////////////////////////
        autoCompleteNewNote = (AutoCompleteTextView) findViewById(R.id.ac_new_note_text);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ///////////////////////////////////////////////////////

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
            newNoteString = autoCompleteNewNote.getText().toString();
            String time = String.valueOf(ServerValue.TIMESTAMP);
            Note note = new Note(userId, newNoteString, time);
            currentUserFirebase.push().setValue(note);

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
        super.onBackPressed();
    }
}
