package com.rsin.blogs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper( Context context) {
        super(context, "userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE Table UserDetails(Name TEXT ,Mobile TEXT NOT NULL UNIQUE, Email TEXT primary key, Password TEXT)");

        db.execSQL("CREATE Table AllNotes(NoteID TEXT primary key,Title TEXT, Description TEXT)");

        db.execSQL("CREATE Table Note_Images(NoteID TEXT, Image_no INTEGER, Image BLOB)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists UserDetails");
        onCreate(db);

    }

    public  Boolean insertuserdata(String name, String mobile,String email,String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Mobile", mobile);
        contentValues.put("Email", email);
        contentValues.put("Password", password);
        long result = DB.insert("UserDetails",null,contentValues);
        if (result == -1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getdata(){
        SQLiteDatabase DB = this.getReadableDatabase();
       Cursor contentValues = DB.rawQuery("Select * from UserDetails",null);

        return contentValues;
    }


    public Boolean loginforphone(String phone, String password){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserDetails WHERE Mobile = ? and Password = ?",new String[]{phone,password});
        if (cursor.getCount()>0)
        {
            return true;
        }
        else {
            return false;
        }


    }

    public Boolean loginforemail(String email, String password){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserDetails WHERE Email = ? and Password = ?",new String[]{email,password});
        if (cursor.getCount()>0)
        {
            return true;
        }
        else {
            return false;
        }


    }

    // table NOTES
    public boolean addNotes( String note_id,String title,String description)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put("NoteID", note_id);
        cv.put("Title", title);
        cv.put("Description", description);
        long result = DB.insert( "AllNotes", null, cv );

        if (result == -1)
        {

            return false;
        }
        else {
            return true;
        }
    }
    //table NOTES END

    //TABLE NOTES_IMAGES
    public boolean addImages( String note_id, int imgno, byte[] image)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put("NoteID", note_id);
        cv.put("Image_no", imgno);
        cv.put("Image", image);
        long result = DB.insert( "Note_Images", null, cv );
        if (result == -1)
        {
            return false;
        }
        else {
            return true;
        }
    }
    //TABLE NOTES_IMAGES END




}
