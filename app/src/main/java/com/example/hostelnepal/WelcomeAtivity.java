package com.example.hostelnepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeAtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_ativity);
    }


    public void hostelOwnerLogin(View view){
        startActivity(new Intent(WelcomeAtivity.this,LoginActivityHO.class));



    }
    public void guestLogin(View view) {
        startActivity(new Intent(WelcomeAtivity.this, LoginActivityG.class));
    }


}
