package com.example.hostelnepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);


    }


    public void hostelOwnerLogin(View view){
        startActivity(new Intent(WelcomeActivity.this,LoginActivityHO.class));



    }
    public void guestLogin(View view) {
        startActivity(new Intent(WelcomeActivity.this, LoginActivityG.class));
    }


}
