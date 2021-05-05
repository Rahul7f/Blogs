package com.rsin.blogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView notes_Recycleview;
    NotesAdapter notesAdapter;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    DBHelper dbHelper;
    List<DataObject> notedata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dbHelper = new DBHelper(getApplicationContext());
        notes_Recycleview = findViewById(R.id.notes_recycleview);
        notedata = new ArrayList<DataObject>();
        notedata = displaydata();
        if (notedata==null)
        {
            Toast.makeText(this, "not data yet", Toast.LENGTH_SHORT).show();
        }
        else {
            notesAdapter = new NotesAdapter(getApplicationContext(),notedata);
            notes_Recycleview.setAdapter(notesAdapter);
        }
       
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


    List<DataObject> displaydata()
    {
        Cursor cursor = dbHelper.getallnotes();
        if (cursor.getCount()==0)
        {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext())
            {
                String heading = cursor.getString(cursor.getColumnIndex("Title"));
                String description = cursor.getString(cursor.getColumnIndex("Description"));
                byte[] byte_array = cursor.getBlob(cursor.getColumnIndex("Image"));
                String note_id = cursor.getString(cursor.getColumnIndex("NoteID"));
                Bitmap img_bitmap = getImagebitmap(byte_array);

                DataObject data = new DataObject(heading,description,img_bitmap,note_id);
                notedata.add(data);

            }
            return notedata;
        }
        return null;
    }

    public static Bitmap getImagebitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}