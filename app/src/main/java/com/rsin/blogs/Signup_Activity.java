package com.rsin.blogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup_Activity extends AppCompatActivity {
    Button signup;
    TextInputLayout name_et,phone_et,email_et,password_et;
    String name,phone,email,password ,hashpassword ;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);
        signup = findViewById(R.id.signup_btn);
        name_et = findViewById(R.id.name_et);
        phone_et = findViewById(R.id.phone_et);
        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        dbHelper = new DBHelper(getApplicationContext());


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_et.getEditText().getText().toString();
                phone = phone_et.getEditText().getText().toString();
                email = email_et.getEditText().getText().toString();
                password = password_et.getEditText().getText().toString();


                if (name.isEmpty() && phone.isEmpty() && email.isEmpty() && password.isEmpty())
                {
                    name_et.setError("Enter Name");
                    phone_et.setError("Enter Phone");
                    email_et.setError("Enter Email");
                    password_et.setError("Enter Password");

                }
                else {

                    boolean vemail = email_validation(email);
                    boolean vphone = phone_validation(phone);
                    boolean vpassword = password_validation(password);
                    boolean minpassword = minpassword(password);
                    if (vemail!=true)
                    {
                        email_et.setError("Invalid Email");

                    }

                    else if (vphone!=true)
                    {
                        phone_et.setError("Invalid Phone no.");

                    }

                    else if (vpassword!=true)
                    {
                        password_et.setError("Invalid password");

                    }

                    else {
                        hashpassword= MD5(password);
                        boolean i =  dbHelper.insertuserdata(name,phone,email,hashpassword);
                        if (i==true)
                        {
                            Toast.makeText(Signup_Activity.this, "signup successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);// New activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                        else {
                            email_et.setError("Email already exists");

                        }
                    }
                }




            }
        });
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

    boolean password_validation(String password)
    {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher m = p.matcher(password);

        return m.matches();

    }

    boolean not_empty_validation(String name, String mobile, String email, String password)
    {
       if (name.isEmpty() && mobile.isEmpty() && email.isEmpty() && password.isEmpty())
       {

           return false;
       }
       else {
           return true;
       }
    }

    boolean minpassword(String password)
    {
        if (password.length()>8)
        {
            return true;
        }
        else {
            return false;
        }
    }
}

