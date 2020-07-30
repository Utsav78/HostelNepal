package com.example.hostelnepal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

public class PropertyDetails extends AppCompatActivity {
    TextView tvNameOfHostel,tvLocality,tvTypeOfHostel,tvCity,tvPrice1,tvPrice2,tvPrice3,tvPrice4;
    ImageView ivRoom1,ivRoom2,ivRoom3,ivRoom4,ivDocument,ivWashroom,ivBuilding,ivKitchen,ivSurrounding;
    DocumentReference docRef;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        firebaseAuth = FirebaseAuth.getInstance();

        tvNameOfHostel = findViewById(R.id.text_name_of_hostel);
        tvTypeOfHostel = findViewById(R.id.text_type_of_hostel);
        tvLocality= findViewById(R.id.text_locality);
        tvCity = findViewById(R.id.text_location);
        tvPrice1 = findViewById(R.id.text_price1);
        tvPrice2 = findViewById(R.id.text_price2);
        tvPrice3 = findViewById(R.id.text_price3);
        tvPrice4 = findViewById(R.id.price4);

        ivRoom1 = findViewById(R.id.image_view_1);
        ivRoom2 = findViewById(R.id.image_view_2);
        ivRoom3 = findViewById(R.id.image_view_3);
        ivRoom4 = findViewById(R.id.image_view_4);
        ivDocument = findViewById(R.id.image_view_document);
        ivWashroom = findViewById(R.id.image_view_washroom);
        ivBuilding = findViewById(R.id.image_view_building);
        ivKitchen = findViewById(R.id.image_view_kitchen);
        ivSurrounding = findViewById(R.id.image_view_surrounding);

        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        docRef = FirebaseFirestore.getInstance().document(path);
        docRef.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                tvNameOfHostel.setText(documentSnapshot.getString("nameOfHostel"));
                tvTypeOfHostel.setText(documentSnapshot.getString("hostelType"));
                tvLocality.setText(documentSnapshot.getString("locality"));
                tvCity.setText(documentSnapshot.getString("city"));

                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfRoom1"))).into(ivRoom1);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfRoom2"))).into(ivRoom2);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfRoom3"))).into(ivRoom3);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfRoom4"))).into(ivRoom4);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfDocument"))).into(ivDocument);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfWashroom"))).into(ivWashroom);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfBuilding"))).into(ivBuilding);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfKitchen"))).into(ivKitchen);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfEnvironment"))).into(ivSurrounding);

            }
        });





    }
}