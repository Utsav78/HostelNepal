package com.example.hostelnepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivityG extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_g);
    }
    public void studentRegister(View view){

        Intent intent=new Intent(LoginActivityG.this, GuestRegister.class);
        startActivity(intent);
    }

}
