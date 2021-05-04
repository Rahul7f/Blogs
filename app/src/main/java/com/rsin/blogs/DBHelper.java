package com.rsin.blogs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper( Context context) {
        super(context, "userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE Table UserDetails(Name TEXT ,Mobile TEXT NOT NULL UNIQUE, Email TEXT primary key, Password TEXT)");

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




}
