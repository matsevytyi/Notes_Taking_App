package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.notetakingapp.entities.NoteObject;

import java.util.HashSet;

public class EditNoteActivity extends AppCompatActivity {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        EditText editContent = findViewById(R.id.editContent);
        EditText editTitle = findViewById(R.id.editTitle);

        noteId = getIntent().getIntExtra("noteId", -1);

        if (noteId != -1) {
            NoteObject selectedNote = MainActivity.notes.get(noteId);
            editTitle.setText(selectedNote.getTitle());
            editContent.setText(selectedNote.getContent());
        } else {
            MainActivity.notes.add(new NoteObject("", ""));
            noteId = MainActivity.notes.size() - 1;
            MainActivity.adapter.notifyDataSetChanged();
        }

        editTitle.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NoteObject editedNote = MainActivity.notes.get(noteId);
                editedNote.setTitle(s.toString());
                MainActivity.adapter.notifyDataSetChanged();

                saveNotesToSharedPreferences();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editContent.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NoteObject editedNote = MainActivity.notes.get(noteId);
                editedNote.setContent(s.toString());
                MainActivity.adapter.notifyDataSetChanged();

                saveNotesToSharedPreferences();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
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
