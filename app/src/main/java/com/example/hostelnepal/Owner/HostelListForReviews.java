package com.example.hostelnepal.Owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hostelnepal.Adapter.ViewPropertyAdapter;
import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityHostelListForReviewsBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HostelListForReviews extends AppCompatActivity {
    private static final String TAG = "HostelListForReviews";
    ActivityHostelListForReviewsBinding binding;
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CollectionReference colRef = db.collection("HostelOwner/"+
            firebaseAuth.getCurrentUser().getUid()
            +"/"+"Property Details");
    private ViewPropertyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostelListForReviewsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setRecyclerView();
    }

    private void setRecyclerView() {
        Query query = colRef.orderBy("nameOfHostel", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<PropertyModel> options = new FirestoreRecyclerOptions.Builder<PropertyModel>().
                setQuery(query,PropertyModel.class).build();
        adapter = new ViewPropertyAdapter(options);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter.setOnClickListener(new ViewPropertyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                String path = documentSnapshot.getReference().getPath();
                String hostelId = documentSnapshot.getReference().getId();
                Intent intent = new Intent(HostelListForReviews.this, ReviewsDetailsActivity.class);
                intent.putExtra("path",path);
                intent.putExtra("hostelId",hostelId);
                Log.d(TAG, "onItemClick:HostelId: "+hostelId);
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