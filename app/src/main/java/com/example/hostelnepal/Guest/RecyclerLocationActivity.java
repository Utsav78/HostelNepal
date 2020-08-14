package com.example.hostelnepal.Guest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hostelnepal.Adapter.ForYouAdapter;
import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

public class RecyclerLocationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ForYouAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_location);
        recyclerView = findViewById(R.id.recycler_view);

        initRecyclerView();
    }

    private void initRecyclerView() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        Query query = FirebaseFirestore.getInstance().collection("All Hostels")
                .whereEqualTo("locality",name).orderBy("nameOfHostel", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<PropertyModel> options = new FirestoreRecyclerOptions.Builder<PropertyModel>()
                .setQuery(query,PropertyModel.class)
                .build();
         adapter = new ForYouAdapter(options);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(RecyclerLocationActivity.this,2,GridLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

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

    public void goBack(View view) {
        startActivity(new Intent(this,HomeActivity.class));
        finish();

    }
}