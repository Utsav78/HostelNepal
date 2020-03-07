package com.example.hostelnepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardHO extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_ho);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (!user.isEmailVerified()){
            Toast.makeText(this, "Email isnot verified", Toast.LENGTH_SHORT).show();
        }
    }
    public void ownerLogout(View view){
        firebaseAuth.signOut();
        startActivity(new Intent(DashboardHO.this, WelcomeActivity.class));
        finish();



    }
    public void hoViewProperty(View view){
        startActivity(new Intent(DashboardHO.this,ViewProperty.class));
    }
    public void hoAddProperty(View view){
        startActivity(new Intent(DashboardHO.this,AddProperty.class));
    }
    public void hoAllUsers(View view){
        startActivity(new Intent(DashboardHO.this,AllUsers.class));
    }
    public void hoNotification(View view){
        startActivity(new Intent(DashboardHO.this,HoNotification.class));
    }
    public void hoReview(View view){
        startActivity(new Intent(DashboardHO.this,HoReviews.class));
    }
}
