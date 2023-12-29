package com.example.notetakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.notetakingapp.entities.NoteObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
public class MainActivity extends AppCompatActivity {

    static ArrayList<NoteObject> notes = new ArrayList<>();
    static ArrayAdapter adapter;
    static HashSet<String> titleSet;
    static HashSet<String> contentSet;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_note) {
            startActivity(new Intent(this, EditNoteActivity.class));

            return true;
        }
        if(item.getItemId() == R.id.delete_node) {
            startActivity(new Intent(this, DeleteNoteActivity.class));
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView notesList = findViewById(R.id.notesList);

        SharedPreferences sharedTitlePreferences = getSharedPreferences("com.example.notetakingapp.noteTitles", MODE_PRIVATE);
        SharedPreferences sharedContentPreferences = getSharedPreferences("com.example.notetakingapp.noteContents", MODE_PRIVATE);
        titleSet = (HashSet<String>) sharedTitlePreferences.getStringSet("noteTitles", null);
        contentSet = (HashSet<String>) sharedContentPreferences.getStringSet("noteContents", null);

        if(titleSet == null || contentSet == null) {
            notes.add(new NoteObject("Sample Note", "Sample Content"));
        }
        else {
            notes = convertToNoteList(titleSet, contentSet);
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        notesList.setAdapter(adapter);

        notesList.setOnItemClickListener((parent, view, position, id) -> {

            Intent noteIntent = new Intent(this, EditNoteActivity.class);
            noteIntent.putExtra("noteId", position);
            startActivity(noteIntent);

        });

    }

    ArrayList<NoteObject> convertToNoteList(HashSet<String> titleSet, HashSet<String> contentSet) {

        ArrayList<NoteObject> noteList = new ArrayList<>();

        //iterators
        Iterator<String> titleIterator = titleSet.iterator();
        Iterator<String> contentIterator = contentSet.iterator();

        while (titleIterator.hasNext() && contentIterator.hasNext()) {
            String title = titleIterator.next();
            String content = contentIterator.next();
            NoteObject note = new NoteObject(title, content);
            noteList.add(note);
        }

        return noteList;
    }
}