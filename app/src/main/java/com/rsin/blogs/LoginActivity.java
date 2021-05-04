package com.rsin.blogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView register;
    Button login_btn;
    EditText email_et,password_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = findViewById(R.id.reg_tv);
        login_btn = findViewById(R.id.login_btn);
        email_et = findViewById(R.id.login_email);
        password_et = findViewById(R.id.login_password);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Signup_Activity.class));
            }
        });
        //test

    }
}