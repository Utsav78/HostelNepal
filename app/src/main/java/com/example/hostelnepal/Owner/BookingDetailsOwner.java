package com.example.hostelnepal.Owner;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hostelnepal.Model.Booking;
import com.example.hostelnepal.Model.BookingOwner;
import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityBookingDetailsOwnerBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BookingDetailsOwner extends AppCompatActivity {
    ActivityBookingDetailsOwnerBinding binding;
    String email;
    private String hostelId;
    private  DocumentReference docRef;
    private FirebaseFirestore db;
    private static final String TAG = "BookingDetailsOwner";
    private String guestId;
    private String roomType;
    private String ownerId;
    private DocumentReference allHostelRef,ownerSideHostelRef;
    private DocumentReference bookingOwnerSideRef;
    private String bookedBed;

    //availableBed ma value aairako xaina
    //work to do : cancel garda no of bed +1 hunu paro ...
    double  availableBed=0;
    private String status;
    private DocumentSnapshot snapshot;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailsOwnerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Delete");
        progressDialog.setMessage("Deleting....");
        progressDialog.setCanceledOnTouchOutside(false);

        db= FirebaseFirestore.getInstance();
        //we can also get documentId,guestId,roomType from the model class
        String path = getIntent().getStringExtra("path");
        ownerId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String hostelIdFromIntent = getIntent().getStringExtra("hostelId");
        Log.d(TAG, "onCreate: HostelID:"+hostelIdFromIntent);

        allHostelRef = db.document("All Hostels/"+hostelIdFromIntent);
        ownerSideHostelRef = db.document("HostelOwner/"+ownerId+"/"+"Property Details/"+hostelIdFromIntent);


        allHostelRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                snapshot = value;

            }
        });

        bookingOwnerSideRef = db.document(path);
        bookingOwnerSideRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                BookingOwner model = value.toObject(BookingOwner.class);
                binding.etEmail.setText(model.getEmail());
                email = model.getEmail();
                binding.etGuestName.setText(model.getGuestName());
                binding.etHostelName.setText(model.getHostelName());
                binding.etPhone.setText(model.getPhoneNumber());
                binding.etPrice.setText(model.getBookingPrice());
                binding.etRoomType.setText(model.getRoomType());
                binding.etDate.setText(model.getDate());
                binding.etStatus.setText(model.getStatus());//this may bring problem.not updated in database.

                status = model.getStatus();

                roomType = model.getRoomType();
                hostelId = model.getHostelId();
                guestId= model.getGuestId();
                long timestamp = model.getTimestamp();
                Log.d(TAG, "onEvent: HostelId and timestamp:"+hostelId+"\n"+timestamp);

                docRef = db.document("Guest/"+guestId+"/"+"Booking/"+timestamp);

                if (status.equals("Confirmed") || status.equals("Cancelled")){
                    binding.btnConfirm.setEnabled(false);
                    binding.btnCancel.setEnabled(false);
                    binding.btnDelete.setEnabled(true);
                }



            }
        });




    }

    private void findStatus(DocumentReference docRef) {
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Booking booking = value.toObject(Booking.class);
                status = booking.getStatus();
                buttonSetting(status);

            }
        });


    }

    private void buttonSetting(String status) {
        if (status.equals("Confirmed")){
            binding.btnCancel.setEnabled(false);

        }else if (status.equals("Cancelled")){
            binding.btnConfirm.setEnabled(false);
            binding.btnDelete.setEnabled(true);
        }

    }

    public void confirmBooking(View view) {
        String path = docRef.getPath();
        Log.d(TAG, "confirmBooking: Path"+path);
        docRef.update("status","Confirmed").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(BookingDetailsOwner.this, "Booking is confirmed!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Confirm Booking:"+e.getMessage());

            }
        });

        bookingOwnerSideRef.update("status","Confirmed").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: update is successful");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: failure on update");

            }
        });

    }

    public void cancelBooking(View view) {
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
        Log.d(TAG, "cancelBooking: All Hostel Ref:"+allHostelRef.getPath());
        ownerSideHostelRef.update(bookedBed,availableBed+1);
        Log.d(TAG, "cancelBooking: OnwerSideHostelRef:"+ownerSideHostelRef.getPath());

        docRef.update("status","Cancelled").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(BookingDetailsOwner.this, "Booking is cancelled", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Cancel Booking:"+e.getMessage());

            }
        });
        bookingOwnerSideRef.update("status","Cancelled")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: ");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: ");

                    }
                });
    }

    public void deleteBooking(View view) {
        progressDialog.show();
        docRef.delete();
        bookingOwnerSideRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(BookingDetailsOwner.this, "Booking is deleted", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                startActivity(new Intent(BookingDetailsOwner.this,DashboardHO.class));
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookingDetailsOwner.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                startActivity(new Intent(BookingDetailsOwner.this,DashboardHO.class));
                finish();


            }
        });


    }
}