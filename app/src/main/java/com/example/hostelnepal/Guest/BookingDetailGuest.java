package com.example.hostelnepal.Guest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hostelnepal.Model.Booking;
import com.example.hostelnepal.Model.BookingOwner;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityBookingDetailGuestBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BookingDetailGuest extends AppCompatActivity {
    ActivityBookingDetailGuestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailGuestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String path = getIntent().getStringExtra("path");
        FirebaseFirestore.getInstance().document(path).addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Booking model = value.toObject(Booking.class);
                binding.etHostelName.setText(model.getHostelName());
                binding.etPrice.setText(model.getPrice());
                binding.etRoomType.setText(model.getRoomType());
            }
        });

    }
}