package com.kevinhodges.donote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.kevinhodges.donote.R;
import com.kevinhodges.donote.utils.Dialogs;

public class ViewNoteActivity extends AppCompatActivity {

    private String noteAuthorExtra;
    private String noteTitleExtra;
    private String noteContentExtra;
    private String noteReference;
    private TextView noteTitle;
    private TextView noteContent;
    private FloatingActionButton viewNoteFab;
    private Firebase mCurrentNoteReference;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        final Intent intent = getIntent();
        noteAuthorExtra = intent.getStringExtra("noteAuthor");
        noteTitleExtra = intent.getStringExtra("noteTitle");
        noteContentExtra = intent.getStringExtra("noteContent");
        noteReference = intent.getStringExtra("noteReference");

        mCurrentNoteReference = new Firebase(noteReference);


        //UI Declarations///////////////////////////////////////////////////////////
        noteTitle = (TextView) findViewById(R.id.tv_view_note_title);
        noteContent = (TextView) findViewById(R.id.tv_view_note_content);
        viewNoteFab = (FloatingActionButton) findViewById(R.id.fab_view_note);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ///////////////////////////////////////////////////////////////////////////

        noteTitle.setText(noteTitleExtra);
        noteContent.setText(noteContentExtra);

        viewNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editNoteIntent = new Intent(ViewNoteActivity.this, EditNoteActivity.class);
                editNoteIntent.putExtra("noteAuthor", noteAuthorExtra);
                editNoteIntent.putExtra("noteTitle", noteTitleExtra);
                editNoteIntent.putExtra("noteContent", noteContentExtra);
                editNoteIntent.putExtra("noteReference", noteReference);
                startActivity(editNoteIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.invite) {
            Toast.makeText(ViewNoteActivity.this, "Soon you will be able to invite friends to collaborate on your notes!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.delete) {

            Dialogs.deleteNoteDialog(ViewNoteActivity.this, mCurrentNoteReference);
        }

        return super.onOptionsItemSelected(item);
    }
}
