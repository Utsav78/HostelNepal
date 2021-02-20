package com.example.hostelnepal.Owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hostelnepal.Adapter.OwnerBookingAdapter;
import com.example.hostelnepal.Adapter.ViewPropertyAdapter;
import com.example.hostelnepal.Model.BookingOwner;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityHoBookingBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HoBooking extends AppCompatActivity {
     ActivityHoBookingBinding binding;
    private static final String TAG = "HoBooking";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CollectionReference colRef = db.collection("HostelOwner/"+firebaseAuth.getCurrentUser().getUid()+"/"+"Booking");
    private OwnerBookingAdapter adapter;
    private String guestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHoBookingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setRecyclerView();

    }

    private void setRecyclerView() {
        Query query = colRef.orderBy("timestamp", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<BookingOwner> options = new FirestoreRecyclerOptions.Builder<BookingOwner>()
                .setQuery(query,BookingOwner.class).build();
        adapter = new OwnerBookingAdapter(options);

        binding.rvBooking.setHasFixedSize(true);
        binding.rvBooking.setLayoutManager(new LinearLayoutManager(this));
        binding.rvBooking.setAdapter(adapter);
        adapter.setOnClickListener(new OwnerBookingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String path = documentSnapshot.getReference().getPath();
                String bookingId = documentSnapshot.getReference().getId();
                BookingOwner model =documentSnapshot.toObject(BookingOwner.class);
                String hostelId = model.getHostelId();
                //left to do . Start from here!!
                Intent intent = new Intent(HoBooking.this,BookingDetailsOwner.class);
                intent.putExtra("path",path).putExtra("hostelId",hostelId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
