package com.kevinhodges.donote.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.kevinhodges.donote.model.Note;

/**
 * Created by Kevin on 4/6/2016.
 */
public class Dialogs {

    private static int deleteNoteResult = 0;

    // Verify that the user wants to delete the note
    public static int deleteNoteDialog(final Activity activity, final Firebase noteBeingEdited) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Delete Note");
        builder.setMessage("Are you sure you would like to delete this note? This cannot be reversed.");

        builder.setNegativeButton("Keep", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                deleteNoteResult = 0;
            }
        });

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                noteBeingEdited.removeValue();
                Toast.makeText(activity, "Note has been deleted", Toast.LENGTH_SHORT).show();
//                deleteNoteResult = 1;
                activity.finish();
            }
        });
        builder.show();

        return deleteNoteResult;
    }

    // Ask the user if they would like to discard or save the note, if save, call the update note method
    public static void discardOrSaveDialog(final Activity activity,
                                           final Firebase editedNote,
                                           final String noteAuthor,
                                           final String updatedTitle,
                                           final String updatedContent) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Discard changes?");
        builder.setMessage("Changes have been made to this note. Would you like to save them?");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Note note = new Note();
                note.updateNote(activity, editedNote, noteAuthor, updatedTitle, updatedContent);
                activity.finish();
            }
        });

        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                }
        );

        builder.show();
    }

}
