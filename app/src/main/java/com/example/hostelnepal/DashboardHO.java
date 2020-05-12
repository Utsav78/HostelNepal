package com.example.hostelnepal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardHO extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_h_o);




    }

    public void logout(View view) {
        firebaseAuth.signOut();
        startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
        finish();




    }
}
