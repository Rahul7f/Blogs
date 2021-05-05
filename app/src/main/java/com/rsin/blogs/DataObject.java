package com.rsin.blogs;

import android.graphics.Bitmap;

import java.util.List;

public class DataObject {
    public String heading;
    public String description;
    public Bitmap bitmap;
    public String note_id;


    public DataObject(String heading, String description, Bitmap bitmap, String note_id) {
        this.heading = heading;
        this.description = description;
        this.bitmap = bitmap;
        this.note_id = note_id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
    }
}
