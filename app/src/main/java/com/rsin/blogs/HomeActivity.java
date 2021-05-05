package com.rsin.blogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {
    RecyclerView notes_Recycleview;
    NotesAdapter notesAdapter;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        notesAdapter = new NotesAdapter(getApplicationContext());

        notes_Recycleview = findViewById(R.id.notes_recycleview);
        notes_Recycleview.setAdapter(notesAdapter);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor= pref.edit();
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
            case R.id.logout:
                //cdc
                editor.clear();
                editor.apply();
                Intent intent = new Intent(this, LoginActivity.class);// New activity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}