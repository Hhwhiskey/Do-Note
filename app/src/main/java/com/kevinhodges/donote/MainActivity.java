package com.kevinhodges.donote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.kevinhodges.donote.activities.LoginActivity;
import com.kevinhodges.donote.activities.NewNoteActivity;
import com.kevinhodges.donote.activities.SettingsActivity;
import com.kevinhodges.donote.model.Note;
import com.kevinhodges.donote.model.NoteAdapter;
import com.kevinhodges.donote.utils.Constants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Firebase mFirebase;
    public Firebase mCurrentUserNotes;
    private FloatingActionButton myFAB;
    private String mUserId;
    private RecyclerView mRecyclerView;
    private NoteAdapter mNoteAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Object mUserNotes;
    private Note note;
    private ArrayList notes = new ArrayList();
    private RecyclerView mListView;
    private FirebaseRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebase = new Firebase(Constants.FIREBASE_URL);
        mUserId = mFirebase.getAuth().getUid();
        mCurrentUserNotes = new Firebase(Constants.FIREBASE_URL + "/users/" + mUserId + "/notes/");


        //UI//////////////////////////////////////////////////////////
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_main);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        myFAB = (FloatingActionButton) findViewById(R.id.my_fab);
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


         mAdapter = new FirebaseRecyclerAdapter<Note, NoteAdapter.NoteViewHolder>(Note.class, android.R.layout.simple_list_item_1, NoteAdapter.NoteViewHolder.class, mCurrentUserNotes) {
             @Override
             protected void populateViewHolder(NoteAdapter.NoteViewHolder viewHolder, Note note, int i) {
                 viewHolder.noteContent.setText(note.getContent());
             }
         };

        mRecyclerView.setAdapter(mAdapter);

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intentSettings);
            return true;
        }

        if (id == R.id.logout) {
            mFirebase.unauth();
            Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intentLogin);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
