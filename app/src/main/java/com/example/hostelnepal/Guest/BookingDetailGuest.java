package com.example.hostelnepal.Guest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.hostelnepal.Model.Booking;
import com.example.hostelnepal.databinding.ActivityBookingDetailGuestBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BookingDetailGuest extends AppCompatActivity {
    ActivityBookingDetailGuestBinding binding;
    String status;
    String ownerId;
    long timestamp;
    DocumentReference ownerBookingRef,guestBookingRef;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailGuestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        db= FirebaseFirestore.getInstance();
        String path = getIntent().getStringExtra("path");
        guestBookingRef = db.document(path);



        guestBookingRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Booking model = value.toObject(Booking.class);
                binding.etHostelName.setText(model.getHostelName());
                binding.etPrice.setText(model.getPrice());
                binding.etRoomType.setText(model.getRoomType());
                binding.etStatus.setText(model.getStatus());
                binding.etDate.setText(model.getDate());
                status = model.getStatus();
                ownerId = model.getOwnerId();
                timestamp = model.getTimestamp();

                if (status.equals("Cancelled") || status.equals("Confirmed")){
                    binding.btnCancel.setEnabled(false);
                }
                ownerBookingRef = db.document("HostelOwner/"+ownerId+"/"+"Booking/"+timestamp);

            }
        });

    }

    public void cancelBooking(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Booking")
                .setMessage("Are you sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        guestBookingRef.update("status","Cancelled");
                        ownerBookingRef.update("status","Cancelled");




                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .show();



    }

}