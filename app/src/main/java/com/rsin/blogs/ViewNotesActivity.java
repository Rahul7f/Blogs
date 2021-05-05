package com.rsin.blogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewNotesActivity extends AppCompatActivity {
    String title,description,note_id;
    TextView title_tt,description_tt;
    RecyclerView recyclerView;
    DBHelper dbHelper;
    List<Bitmap> imagelist;
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        title_tt = findViewById(R.id.title_tt);
        description_tt = findViewById(R.id.dec_tt);
        recyclerView = findViewById(R.id.image_recycleview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        title = getIntent().getStringExtra("Title");
        description = getIntent().getStringExtra("Description");
        note_id = getIntent().getStringExtra("notes_id");
        title_tt.setText(title);
        description_tt.setText(description);
        dbHelper = new DBHelper(getApplicationContext());
        imagelist = dbHelper.getImagebyID(note_id);
        if (imagelist==null)
        {
            Toast.makeText(this, "no image available", Toast.LENGTH_SHORT).show();
        }
        else
        {
            imageAdapter = new ImageAdapter(getApplicationContext(),imagelist);
            recyclerView.setAdapter(imageAdapter);


        }
        




    }
}