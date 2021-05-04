package com.rsin.blogs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;

public class AddNotesActivity extends AppCompatActivity {
    GridView gridLayout;
    PhotoAdpater photoAdpater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        gridLayout = findViewById(R.id.gridlayout);
        photoAdpater = new PhotoAdpater(getApplicationContext());

        gridLayout.setAdapter(photoAdpater);


    }


}