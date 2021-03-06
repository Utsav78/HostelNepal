package com.example.hostelnepal.Guest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hostelnepal.Model.Booking;
import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.Owner.BookingDetailsOwner;
import com.example.hostelnepal.databinding.ActivityBookingDetailGuestBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BookingDetailGuest extends AppCompatActivity {
    private static final String TAG = "BookingDetailGuest";
    ActivityBookingDetailGuestBinding binding;
    String status;
    String ownerId;
    long timestamp;
    DocumentReference ownerBookingRef,guestBookingRef;
    DocumentReference allHostelRef,ownerRef;
    DocumentReference ownerInfoRef;
    FirebaseFirestore db;
    private String hostelId;
    DocumentSnapshot snapshot;
    private double availableBed;
    private String bookedBed;
    private String roomType;
    private String phoneNumber;
    private String ownerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailGuestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        db= FirebaseFirestore.getInstance();
        String path = getIntent().getStringExtra("path");
        hostelId = getIntent().getStringExtra("hostelId");
        guestBookingRef = db.document(path);

        allHostelRef = db.document("All Hostels/"+hostelId);
        allHostelRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                snapshot = value;
            }
        });





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
                roomType =  model.getRoomType();


                timestamp = model.getTimestamp();

                if (status.equals("Cancelled") || status.equals("Confirmed")){
                    binding.btnCancel.setEnabled(false);
                }
                ownerBookingRef = db.document("HostelOwner/"+ownerId+"/"+"Booking/"+timestamp);

                ownerRef = db.document("HostelOwner/"+ownerId+"/"+
                        "Property Details/"+hostelId);

                ownerInfoRef = db.document("HostelOwner/"+ownerId);
                addPersonalInfoOfOwner();
            }
        });

    }

    private void addPersonalInfoOfOwner() {
        ownerInfoRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error !=null){
                    Log.d(TAG, "onEvent: FirebaseFireStoreException :"+error.getMessage());
                    return;
                }

                phoneNumber = value.getString("PhoneNumber");
                ownerName = value.getString("FullName");
                binding.etOwnerName.setText(ownerName);
                binding.etPhone.setText(phoneNumber);




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

                        //yeha kam garna baki cha
                        switch (roomType){
                            case "1 Sitter":
                                bookedBed ="availableBeds1";
                                break;

                            case "2 Sitter":
                                bookedBed = "availableBeds2";
                                break;

                            case "3 Sitter":
                                bookedBed="availableBeds3";
                                break;

                            case "4 Sitter":
                                bookedBed = "availableBeds4";
                                break;
                        }
                        availableBed = snapshot.getDouble(bookedBed);
                        allHostelRef.update(bookedBed,availableBed+1);
                        ownerRef.update(bookedBed,availableBed+1);




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

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void callOwner(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber));

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(BookingDetailGuest.this, "Please give required permission to use this service", Toast.LENGTH_SHORT).show();
            askForPermission();


        }else{
            startActivity(callIntent);
        }


    }

    private void askForPermission() {
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission
        .CALL_PHONE},1010);

    }
}