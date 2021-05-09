package com.rsin.blogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddNotesActivity extends AppCompatActivity {
    GridView gridLayout;
    PhotoAdpater photoAdpater;
    Button add_images,savenotes_btn;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    TextInputLayout title_et,dis_et;
    List<Bitmap> imagesEncodedList;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    Uri imageUri;
    String title,description;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        gridLayout = findViewById(R.id.gridlayout);
        add_images = findViewById(R.id.add_images);
        savenotes_btn = findViewById(R.id.save_notes_btn);
        title_et = findViewById(R.id.notes_title_et);
        dis_et = findViewById(R.id.note_dis_et);
        imagesEncodedList = new ArrayList<Bitmap>();
        dbHelper = new DBHelper(getApplicationContext());

        add_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
            }
        });

        savenotes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = title_et.getEditText().getText().toString();
                description = dis_et.getEditText().getText().toString();
                if (title.isEmpty()&&description.isEmpty())
                {
                    Toast.makeText(AddNotesActivity.this, "enter title/description", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (title.length()<5)
                    {
                        title_et.setError("text too small");
                    }
                    else if (description.length()<100)
                    {
                        dis_et.setError("text too small");
                    }
                    else {
                        if (imagesEncodedList.isEmpty()&& imagesEncodedList.size()==0)
                        {
                            Toast.makeText(AddNotesActivity.this, "choose image", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //save data here
                            String uniqueID = UUID.randomUUID().toString();
                            byte[] mainimg = getBytesFromBitmap(imagesEncodedList.get(0));

                            boolean data =  dbHelper.addNotes(uniqueID,title,description,mainimg);

                            if (data == true)
                            {
                                //data inserted
                                Toast.makeText(AddNotesActivity.this, "Note Added Successfully", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i<imagesEncodedList.size();i++)
                                {

                                    byte[] imgbyte = getBytesFromBitmap(imagesEncodedList.get(i));
                                    boolean image_data =dbHelper.addImages(uniqueID,i+1,imgbyte);
                                    if (image_data==true)
                                    {
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);// New activity
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else {
                                        Toast.makeText(AddNotesActivity.this, "Something wrong ", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            else {
                                //something wrong
                                Toast.makeText(AddNotesActivity.this, "failed to save Note", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                }

            }
        });

    }
    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(AddNotesActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(AddNotesActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
            Toast.makeText(AddNotesActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AddNotesActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(AddNotesActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AddNotesActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddNotesActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if(requestCode == PICK_IMAGE_MULTIPLE) {
                if(resultCode == Activity.RESULT_OK) {
                    if(data.getClipData() != null) {
                        int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                        if (count>10)
                        {
                            Toast.makeText(this, "max 10 image are allow", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            for(int i = 0; i < count; i++)
                            {
                                imageUri = data.getClipData().getItemAt(i).getUri();
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                                    imagesEncodedList.add(bitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        photoAdpater = new PhotoAdpater(getApplicationContext(),imagesEncodedList);
                        gridLayout.setAdapter(photoAdpater);

                        //do something with the image (save it to some directory or whatever you need to do with it here)
                    }
                } else if(data.getData() != null) {
                    String imagePath = data.getData().getPath();
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
                else {
                    Toast.makeText(this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "You haven't picked Image ", Toast.LENGTH_LONG)
                    .show();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }





}