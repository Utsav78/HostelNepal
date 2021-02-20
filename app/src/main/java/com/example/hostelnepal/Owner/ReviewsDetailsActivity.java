package com.example.hostelnepal.Owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hostelnepal.Adapter.CommentAdapter;
import com.example.hostelnepal.Model.CommentModel;
import com.example.hostelnepal.Model.MeanRating;
import com.example.hostelnepal.databinding.ActivityReviewsDetailsBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class ReviewsDetailsActivity extends AppCompatActivity {
    ActivityReviewsDetailsBinding binding;
    private static final String TAG = "ReviewsDetailsActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference ratingRef;
    private CollectionReference colRef;
    private String hostelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewsDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        hostelId = getIntent().getStringExtra("hostelId");

        ratingRef= db.document("All Hostels/"+hostelId+"/"+"ReviewsAndRating/"
        +hostelId);

        ratingRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                MeanRating rating = value.toObject(MeanRating.class);
                binding.meanCleanliness.setText(String.valueOf(rating.getMeanCleanliness()));
                binding.meanFood.setText(String.valueOf(rating.getMeanCleanliness()));
                binding.meanEnvironment.setText(String.valueOf(rating.getMeanEnvironment()));
                binding.meanStaff.setText(String.valueOf(rating.getMeanStaff()));
                binding.meanSecurity.setText(String.valueOf(rating.getMeanSecurity()));
                binding.meanValueForMoney.setText(String.valueOf(rating.getMeanValueForMoney()));
                binding.meanFacilities.setText(String.valueOf(rating.getMeanFacilities()));

            }
        });



        binding.seeAllReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewsDetailsActivity.this,AllCommentsActivity.class);
                intent.putExtra("hostelId",hostelId);
                Log.d(TAG, "onClick: HostelId:::"+hostelId);
                startActivity(intent);
            }
        });

    }



}