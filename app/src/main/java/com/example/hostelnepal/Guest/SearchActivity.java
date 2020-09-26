package com.example.hostelnepal.Guest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.hostelnepal.Adapter.SearchAdapter;
import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private android.widget.SearchView searchView;
    private CollectionReference colRef = FirebaseFirestore.getInstance().collection("All Hostels");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.search);
        recyclerView = findViewById(R.id.rv_all_hostel);
        setRecyclerView();

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               Query querySearch = colRef.orderBy("nameOfHostel", Query.Direction.ASCENDING).
                       startAt(newText);
               FirestoreRecyclerOptions<PropertyModel> recyclerOptions = new FirestoreRecyclerOptions.Builder<PropertyModel>().
                       setQuery(querySearch,PropertyModel.class).build();
               searchAdapter.updateOptions(recyclerOptions);

               return true;
           }
       });

    }

    private void setRecyclerView() {
        Query query =colRef.orderBy("nameOfHostel", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<PropertyModel> options = new FirestoreRecyclerOptions.Builder<PropertyModel>()
                .setQuery(query,PropertyModel.class).build();
        searchAdapter = new SearchAdapter(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        searchAdapter.startListening();
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (searchAdapter != null){
            searchAdapter.stopListening();

        }

    }
}