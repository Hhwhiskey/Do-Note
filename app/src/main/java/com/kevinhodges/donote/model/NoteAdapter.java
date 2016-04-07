package com.kevinhodges.donote.model;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Kevin on 3/20/2016.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Note> notes;
    private ValueEventListener valueEventListener;
    private LayoutInflater inflater;
    private Context mContext;


    public NoteAdapter(ArrayList<Note> notes) {
        this.notes = notes;
//        this.mContext = context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    ///////////////////////////////////////////////////////////////////////////
    // ViewHolder
    ///////////////////////////////////////////////////////////////////////////
    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView noteTitle;
        public TextView noteContent;

        public NoteViewHolder(View itemView) {
            super(itemView);

            noteTitle = (TextView)itemView.findViewById(android.R.id.text1);
            noteTitle.setTypeface(null, Typeface.BOLD);

            noteContent = (TextView)itemView.findViewById(android.R.id.text2);
            noteContent.setMaxLines(5);

        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}