package com.example.hostelnepal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardHO extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String userID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView mFullNameOfOwner;
    CircleImageView mProfilePictureOfOwner;
    DocumentReference documentReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_h_o);



        mFullNameOfOwner = findViewById(R.id.fullNameOfOwner);
        mProfilePictureOfOwner = findViewById(R.id.profilePictureOfOwner);


        userID= firebaseAuth.getCurrentUser().getUid();

        documentReference  = db.collection("HostelOwner").document(userID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mFullNameOfOwner.setText(documentSnapshot.getString("FullName"));
            }
        });







    }

    public void logout(View view) {
        firebaseAuth.signOut();
        startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
        finish();




    }

    public void openEditProfileActivity(View view) {
        Intent intent = new Intent(getApplicationContext(),EditProfileActivity.class);
        startActivity(intent);

    }
}
