package com.rsin.blogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {
    RecyclerView notes_Recycleview;
    NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        notesAdapter = new NotesAdapter(getApplicationContext());

        notes_Recycleview = findViewById(R.id.notes_recycleview);
        notes_Recycleview.setAdapter(notesAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addnotes:
                startActivity(new Intent(getApplicationContext(),AddNotesActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}