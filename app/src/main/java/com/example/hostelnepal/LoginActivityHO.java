package com.example.hostelnepal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivityHO extends AppCompatActivity {

    FirebaseAuth fAuth;
    EditText hoEmail,hoPassword;
    Button hoLoginBtn;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ho);
        hoLoginBtn = findViewById(R.id.loginG);


    }


}
