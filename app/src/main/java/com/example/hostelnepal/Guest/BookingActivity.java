package com.example.hostelnepal.Guest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hostelnepal.Adapter.BookingViewPagerAdapter;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityBookingBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class BookingActivity extends AppCompatActivity {

    private ActivityBookingBinding binding;
    private String[] imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        imageUrls = new String[8];

        //getting the path of documents from SearchActivity
        String path = getIntent().getStringExtra("path");
        fillImages(path);
        BookingViewPagerAdapter adapter= new BookingViewPagerAdapter(this,imageUrls);
        binding.hostelImageViewPager.setAdapter(adapter);




    }

    private void fillImages(String path) {
        DocumentReference docRef = FirebaseFirestore.getInstance().document(path);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                imageUrls[0]=value.getString("uriOfBuilding");
                imageUrls[1]=value.getString("uriOfEnvironment");
                imageUrls[2]=value.getString("uriOfKitchen");
                imageUrls[3]=value.getString("uriOfRoom1");
                imageUrls[4]=value.getString("uriOfRoom2");
                imageUrls[5]=value.getString("uriOfRoom3");
                imageUrls[6]=value.getString("uriOfRoom4");
                imageUrls[7]=value.getString("uriOfWashroom");

            }
        });
    }
}