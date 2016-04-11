package com.kevinhodges.donote.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.kevinhodges.donote.R;
import com.kevinhodges.donote.model.Note;
import com.kevinhodges.donote.model.User;
import com.kevinhodges.donote.utils.Constants;

public class InviteToNoteActivity extends AppCompatActivity {

    private static final String TAG = "InviteToNoteActivity";
    private Firebase userSearchRef;
    private Query userQuery;
    private String email;
    private AutoCompleteTextView userSearchAC;
    private TextView searchResultTV;
    private Button emailSearchButton;
    private String emailString;
    private User userFound;
    private Firebase invitedUserReference;
    private String userSearchEmail;
    private Firebase userReference;
    private String noteStringExtra;
    private Firebase noteReference;
    private String noteAuthorExtra;
    private String noteTitleExtra;
    private String noteContentExtra;
    private String noteReferenceExtra;
    private Note sharedNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_to_note);

        Intent intentExtra = getIntent();
        noteAuthorExtra = intentExtra.getStringExtra("noteAuthor");
        noteTitleExtra = intentExtra.getStringExtra("noteTitle");
        noteContentExtra = intentExtra.getStringExtra("noteContent");
        noteReferenceExtra = intentExtra.getStringExtra("noteReference");

        // Search here for users to invite
        userSearchRef = new Firebase(Constants.FIREBASE_URL + "/users/");

        //UI Declarations///////////////////////////////////////////////////////////
        userSearchAC = (AutoCompleteTextView) findViewById(R.id.ac_user_search);
        searchResultTV = (TextView) findViewById(R.id.tv_search_result);
        emailSearchButton = (Button) findViewById(R.id.button_search);
        ///////////////////////////////////////////////////////////////////////////

        emailSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailString = userSearchAC.getText().toString();
                userQuery = userSearchRef.orderByChild("email").equalTo(emailString);

                userQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                        userFound = snapshot.getValue(User.class);

                        if (userFound.getEmail() == null || userFound.getEmail().equals("")) {
                            searchResultTV.setText("No user found");
                        } else {
                            searchResultTV.setText(userFound.getEmail());
                            userReference = snapshot.getRef();
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        searchResultTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(InviteToNoteActivity.this);
                builder.setTitle("Invite");
                builder.setMessage("Are you sure you would like to allow " + emailString + " to view and edit this note?");
                builder.setPositiveButton("Invite", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Save the shared note to the users "notes" root on firebase
                        Note note = new Note(noteAuthorExtra, noteTitleExtra, noteContentExtra);
                        userReference = new Firebase(userReference + "/notes/");
                        userReference.push().setValue(note);

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });
    }
}