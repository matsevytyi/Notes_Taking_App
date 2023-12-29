package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.notetakingapp.entities.NoteObject;

import java.util.ArrayList;
import java.util.HashSet;

public class DeleteNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        ListView notesList = findViewById(R.id.notesToBeDeletedList);

        notesList.setAdapter(MainActivity.adapter);

        notesList.setOnItemClickListener((parent, view, position, id) -> {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Do you really want to delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        MainActivity.notes.remove(position);
                        MainActivity.adapter.notifyDataSetChanged();

                        saveNotesToSharedPreferences();
                    })
                    .setNegativeButton("No", null)
                    .show();

        });
    }

    private void saveNotesToSharedPreferences() {
        SharedPreferences sharedTitlePreferences = getSharedPreferences("com.example.notetakingapp.noteTitles", MODE_PRIVATE);
        SharedPreferences sharedContentPreferences = getSharedPreferences("com.example.notetakingapp.noteContents", MODE_PRIVATE);

        HashSet<String> titleSet = new HashSet<>();
        HashSet<String> contentSet = new HashSet<>();

        for (NoteObject note : MainActivity.notes) {
            titleSet.add(note.getTitle());
            contentSet.add(note.getContent());
        }

        sharedTitlePreferences.edit().putStringSet("noteTitles", titleSet).apply();
        sharedContentPreferences.edit().putStringSet("noteContents", contentSet).apply();
    }
}
