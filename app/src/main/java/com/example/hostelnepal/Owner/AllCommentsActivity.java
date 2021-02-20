package com.example.hostelnepal.Owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hostelnepal.Adapter.CommentAdapter;
import com.example.hostelnepal.Model.CommentModel;
import com.example.hostelnepal.databinding.ActivityAllCommentsBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AllCommentsActivity extends AppCompatActivity {
    ActivityAllCommentsBinding binding;
    private static final String TAG = "AllCommentsActivity";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colRef;
    private CommentAdapter adapter;
    private String hostelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllCommentsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        hostelId = getIntent().getStringExtra("hostelId");
        colRef= db.collection("All Hostels/"+hostelId+"/"+
                "Review");
        Log.d(TAG, "setRecyclerView: CollectionReference :"+colRef.getPath());
        setRecyclerView();
    }

    private void setRecyclerView() {
        Query query = colRef.orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<CommentModel> options = new FirestoreRecyclerOptions.Builder<CommentModel>().
                setQuery(query,CommentModel.class).build();
        adapter = new CommentAdapter(options);
        binding.recyclerView.setNestedScrollingEnabled(false);

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