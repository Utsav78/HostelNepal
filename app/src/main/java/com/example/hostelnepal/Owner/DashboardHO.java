package com.example.hostelnepal.Owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelnepal.Common.WelcomeActivity;
import com.example.hostelnepal.R;
import com.example.hostelnepal.Services.ListenBooking;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardHO extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String userID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView mFullNameOfOwner;
    CircleImageView mProfilePictureOfOwner;
    DocumentReference documentReference;
    StorageReference storageReference;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_h_o);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        scrollView = findViewById(R.id.scrollView);

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
        StorageReference docRef = storageReference.child("HostelOwner/"+userID+"/"+"ProfilePicture");
        docRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mProfilePictureOfOwner);
                scrollView.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DashboardHO.this, "Please Add Your Profile Image", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void logout(View view) {
        firebaseAuth.signOut();
        startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
        finish();
    }

    public void openEditProfileActivity(View view) {
        Intent intent = new Intent(getApplicationContext(),EditProfileActivity.class);
        startActivity(intent);

    }

    public void addProperty(View view) {
        startActivity(new Intent(this,AddProperty.class));
    }

    public void viewProperty(View view) {
        startActivity(new Intent(this,ViewProperty.class));
    }

    public void notification(View view) {
        startActivity(new Intent(this, HoBooking.class));
    }
}
