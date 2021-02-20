package com.example.hostelnepal.Guest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hostelnepal.Adapter.GuestBookingAdapter;
import com.example.hostelnepal.Model.Booking;
import com.example.hostelnepal.Owner.PropertyDetails;
import com.example.hostelnepal.Owner.ViewProperty;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivitySavedBookingBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SavedBookingActivity extends AppCompatActivity {
    ActivitySavedBookingBinding binding;
    private static final String TAG = "SavedBookingActivity";
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colRef = db.collection("Guest/"+fAuth.getCurrentUser().getUid()+
            "/"+"Booking");
    private GuestBookingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedBookingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setRecyclerView();

    }

    private void setRecyclerView() {
        Query query = colRef.orderBy("hostelName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Booking> options = new FirestoreRecyclerOptions.Builder<Booking>()
                .setQuery(query,Booking.class).build();
        adapter = new GuestBookingAdapter(options);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new GuestBookingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String path = documentSnapshot.getReference().getPath();
                String documentId = documentSnapshot.getReference().getId();
                Log.d(TAG, "onItemClick: DocumentId"+documentId);
                Intent intent = new Intent(SavedBookingActivity.this, BookingDetailGuest.class);
                intent.putExtra("path",path);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}