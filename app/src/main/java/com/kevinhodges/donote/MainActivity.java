package com.kevinhodges.donote;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.*;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.kevinhodges.donote.activities.LoginActivity;
import com.kevinhodges.donote.activities.NewNoteActivity;
import com.kevinhodges.donote.activities.ViewNoteActivity;
import com.kevinhodges.donote.model.Note;
import com.kevinhodges.donote.model.NoteAdapter;
import com.kevinhodges.donote.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Firebase mFirebase;
    public Firebase mCurrentUserNotes;
    private FloatingActionButton myFAB;
    private String mUserId;
    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    private String userEmail;
    private Firebase mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebase = new Firebase(Constants.FIREBASE_URL);

        try {
            mUserId = mFirebase.getAuth().getUid();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Please sign in first", Toast.LENGTH_LONG).show();
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }

        mCurrentUser = new Firebase(mFirebase + "/users/" + mUserId);
        mCurrentUserNotes = new Firebase(Constants.FIREBASE_URL + "/users/" + mUserId + "/notes/");
        mCurrentUserNotes.orderByKey();

        Intent extra = getIntent();
        userEmail = extra.getStringExtra("userEmail");

        if (userEmail != null) {
            mCurrentUser.child("email").setValue(userEmail);
        }




        //UI//////////////////////////////////////////////////////////
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_main);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        myFAB = (FloatingActionButton) findViewById(R.id.my_fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ////////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////////////////////////
        // Listeners
        ///////////////////////////////////////////////////////////////////////////
        myFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewNote = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivity(intentNewNote);
            }
        });


        FirebaseRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> adapter =
                new FirebaseRecyclerAdapter<Note, NoteAdapter.NoteViewHolder>(
                        Note.class,
                        android.R.layout.simple_list_item_2,
                        NoteAdapter.NoteViewHolder.class, mCurrentUserNotes
                ) {
                    @Override
                    protected void populateViewHolder(NoteAdapter.NoteViewHolder viewHolder, final Note note, final int i) {

                        viewHolder.noteTitle.setText(note.getTitle());
                        viewHolder.noteTitle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Firebase noteReference = getRef(i);
                                onNoteClicked(note, noteReference);
                            }
                        });
                        viewHolder.noteContent.setText(note.getContent());
                        viewHolder.noteContent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Firebase noteReference = getRef(i);
                                onNoteClicked(note, noteReference);
                            }
                        });
                    }
                };

        mRecyclerView.setAdapter(adapter);
    }

    public void onNoteClicked(Note note, Firebase ref) {

        String firebaseRefString = ref.toString();

        Intent intentViewNote = new Intent(MainActivity.this, ViewNoteActivity.class);
        intentViewNote.putExtra("noteAuthor", note.getAuthor());
        intentViewNote.putExtra("noteTitle", note.getTitle());
        intentViewNote.putExtra("noteContent", note.getContent());
        intentViewNote.putExtra("noteReference", firebaseRefString);
        startActivity(intentViewNote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.settings) {
//            Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
//            startActivity(intentSettings);
//            return true;
//        }

        if (id == R.id.logout) {
            mFirebase.unauth();
            Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intentLogin);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you would like to exit DoNote now?");
        builder.setNegativeButton("Stay Here", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        builder.show();


    }
}
