package com.example.hostelnepal.Guest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hostelnepal.Adapter.CommentAdapter;
import com.example.hostelnepal.Adapter.ViewPropertyAdapter;
import com.example.hostelnepal.Model.CommentModel;
import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityReviewBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ReviewActivity extends AppCompatActivity {
    private static final String TAG = "ReviewActivity";
    ActivityReviewBinding binding;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String documentId;
    private CollectionReference colRef;
    private CommentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        documentId = getIntent().getStringExtra("documentId");
        colRef = db.collection("All Hostels/"+documentId+"/"+"Review");
        Log.d(TAG, "onCreate: "+colRef.getPath()+""+documentId);
        setRecyclerView();



    }

    private void setRecyclerView() {
        Query query = colRef.orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<CommentModel> options = new FirestoreRecyclerOptions.Builder<CommentModel>().
                setQuery(query,CommentModel.class).build();
        adapter = new CommentAdapter(options);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
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