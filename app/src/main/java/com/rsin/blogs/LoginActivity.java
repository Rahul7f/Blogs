package com.rsin.blogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {
    TextView register;
    Button login_btn;
    EditText email_et,password_et;
    DBHelper dbHelper;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = findViewById(R.id.reg_tv);
        login_btn = findViewById(R.id.login_btn);
        email_et = findViewById(R.id.login_email);
        password_et = findViewById(R.id.login_password);
        dbHelper = new DBHelper(getApplicationContext());
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Signup_Activity.class));
            }
        });
        String emailpref=pref.getString("email", null);
        String passwordpref=pref.getString("password", null);
        if (emailpref==null&&passwordpref==null)
        {
            Toast.makeText(this, "login please", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);// New activity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString();
                String password = password_et.getText().toString();
                if (email.isEmpty() && password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "enter email and password", Toast.LENGTH_SHORT).show();
                }
                else {
                    String value = check_phone_email(email);
                    if (value.equals("phone"))
                    {
                        String hash_password = MD5(password);
                        Boolean cursor = dbHelper.loginforphone(email,hash_password);
                        if (cursor == true)
                        {
                            //shared preference

                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("phone",email );
                            editor.putString("password", password);
                            editor.apply();
                            // shared preference end

                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);// New activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "check your Phone and Password", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else if (value.equals("email"))
                    {
                        String hash_password = MD5(password);
                        Boolean cursor = dbHelper.loginforemail(email,hash_password);
                        if (cursor == true)
                        {

                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("phone",email );
                            editor.putString("password", password);
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "check your email and Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(LoginActivity.this, "invalid email/phone", Toast.LENGTH_SHORT).show();
                    }
                }


                  String hash_password = MD5(password);
//                Toast.makeText(LoginActivity.this, pass, Toast.LENGTH_SHORT).show();
//                Boolean cursor = dbHelper.login("rahul@gmail.com","123456");
//                if (cursor == true)
//                {
//                    Toast.makeText(LoginActivity.this, "value exist", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(LoginActivity.this, "not exist", Toast.LENGTH_SHORT).show();
//                }
                
//                if (cursor.moveToFirst()){
//                    do{
//                        String data = cursor.getString(cursor.getColumnIndex("Name"));
//                        // do what ever you want here
//                        Toast.makeText(LoginActivity.this, data, Toast.LENGTH_SHORT).show();
//                    }while(cursor.moveToNext());
//                }
//                cursor.close();
            }
        });
        //test

    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch(UnsupportedEncodingException ex){
        }
        return null;
    }

    boolean email_validation(String email)
    {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (email.matches(emailPattern))
            {
                return  true; //
            }
            else
            {
               return false;
            }

    }

    boolean phone_validation(String phone)
    {
            String phonePattern = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
            if (phone.matches(phonePattern))
            {
                return  true;
            }
            else
            {
                return false;
            }
            
    }

    String check_phone_email(String value)
    {
        boolean email = email_validation(value);
        boolean phone = phone_validation(value);
        if (email==true)
        {
            // this is email
            return "email";
        } else if (phone==true) {
            // phone
            return "phone";
        }
        else {
            return "invalid";
        }
    }





}
