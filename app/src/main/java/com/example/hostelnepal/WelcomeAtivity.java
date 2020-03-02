package com.example.hostelnepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeAtivity extends AppCompatActivity {

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_ativity);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() !=null){

            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();

        }
    }


    public void hostelOwnerLogin(View view){
        startActivity(new Intent(WelcomeAtivity.this,LoginActivityHO.class));



    }
    public void guestLogin(View view) {
        startActivity(new Intent(WelcomeAtivity.this, LoginActivityG.class));
    }


}
