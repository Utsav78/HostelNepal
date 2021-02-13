package com.example.hostelnepal.Owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.hostelnepal.Model.Booking;
import com.example.hostelnepal.Model.BookingOwner;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityBookingDetailsOwnerBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BookingDetailsOwner extends AppCompatActivity {
    ActivityBookingDetailsOwnerBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailsOwnerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String path = getIntent().getStringExtra("path");
        FirebaseFirestore.getInstance().document(path).addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                BookingOwner model = value.toObject(BookingOwner.class);
                binding.etEmail.setText(model.getEmail());
                binding.etGuestName.setText(model.getGuestName());
                binding.etHostelName.setText(model.getHostelName());
                binding.etPhone.setText(model.getPhoneNumber());
                binding.etPrice.setText(model.getBookingPrice());
                binding.etRoomType.setText(model.getRoomType());


            }
        });

    }
}