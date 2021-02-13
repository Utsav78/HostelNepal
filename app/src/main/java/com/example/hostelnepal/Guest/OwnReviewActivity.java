package com.example.hostelnepal.Guest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaDrm;
import android.media.Rating;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.hostelnepal.Model.MeanRating;
import com.example.hostelnepal.Model.RatingModel;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityOwnReviewBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class OwnReviewActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private ActivityOwnReviewBinding binding;
    private float ratingCleanliness,ratingFood,ratingSecurity,ratingEnvironment,
            ratingStaff,ratingFacilities,ratingValueForMoney;
    private String comment;
    private RatingModel ratingModel;
    private FirebaseFirestore fireStore;
    private String documentId;
    private DocumentReference docRef;
    private DocumentReference allHostelRefRating;
    private DocumentReference allHostelReview;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private float umCleanliness,umSecurity,umFood,umValueForMoney,umEnvironment,umStaff,umFacilities,count;
    private MeanRating meanRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOwnReviewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        fireStore = FirebaseFirestore.getInstance();
        documentId = getIntent().getStringExtra("documentId");
        docRef = fireStore.document("Guest/"+userId+"/"+"ReviewsAndRating/"+documentId);

        allHostelRefRating = fireStore.document("All Hostels/"+documentId+"/"+"ReviewsAndRating/"+documentId);
        allHostelReview = fireStore.document("All Hostels/"+documentId+"/"+"Review/"+userId);

        binding.ratingCleanliness.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingCleanliness = rating;
                checkButton();
            }
        });

        binding.ratingEnvironment.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingEnvironment =rating;
                checkButton();
            }
        });

        binding.ratingFood.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingFood = rating;
                checkButton();
            }
        });

        binding.ratingFacilities.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingFacilities = rating;
                checkButton();
            }
        });
        binding.ratingStaff.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingStaff= rating;
                checkButton();
            }
        });

        binding.ratingValueForMoney.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingValueForMoney = rating;
                checkButton();
            }
        });

        binding.ratingSecurity.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingSecurity = rating;
                checkButton();
            }
        });

        binding.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = binding.comment.getText().toString().trim();
                if (TextUtils.isEmpty(comment)){
                    ratingModel = new RatingModel(ratingCleanliness,ratingSecurity,ratingEnvironment,
                            ratingFacilities,ratingFood,ratingStaff,ratingValueForMoney);

                }else{
                    ratingModel = new RatingModel(ratingCleanliness,ratingSecurity,ratingEnvironment,ratingFacilities,
                            ratingFood,ratingStaff,ratingValueForMoney,comment);
                }

                docRef.set(ratingModel).addOnSuccessListener(OwnReviewActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(OwnReviewActivity.this, "Your review is posted successfully",
                                Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(OwnReviewActivity.this,new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OwnReviewActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OwnReviewActivity.this,BookingActivity.class));

                    }
                });
                allHostelRefRating.addSnapshotListener(OwnReviewActivity.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value.exists()){
                            meanRating = value.toObject(MeanRating.class);
                            count = meanRating.getCount();
                            umCleanliness = (meanRating.getMeanCleanliness()*count+
                                    ratingModel.getRatingCleanliness())/(count+1);
                            umSecurity = (meanRating.getMeanSecurity()*count
                                    +ratingModel.getRatingSecurity())/(count+1);

                            umFacilities = (meanRating.getMeanFacilities()*count
                                    +ratingModel.getRatingFacilities())/(count+1);

                            umFood = (meanRating.getMeanFood()*count+
                                    ratingModel.getRatingFood())/(count+1);

                            umStaff = (meanRating.getMeanFood()*count+
                                    ratingModel.getRatingFood())/(count+1);

                            umValueForMoney =(meanRating.getMeanValueForMoney()*count+
                                    ratingModel.getRatingValueForMoney())/(count+1);

                            umFacilities = (meanRating.getMeanFacilities()*count+
                                    ratingModel.getRatingFacilities())/(count+1);
                            umEnvironment = (meanRating.getMeanEnvironment()*count+
                                    ratingModel.getRatingEnvironment())/(count+1);

                            count = count+1;
                            allHostelRefRating.update("meanCleanliness",umCleanliness,
                                    "meanSecurity",umSecurity,
                                    "meanFacilities",umFacilities,
                                    "meanFood",umFood,
                                    "meanStaff",umStaff,
                                    "meanEnvironment",umEnvironment,
                                    "meanValueForMoney",umValueForMoney,
                                    "count",count);

                        }else{
                            meanRating = new MeanRating(ratingCleanliness,ratingSecurity,ratingFood,
                                    ratingStaff,ratingEnvironment,
                                    ratingFacilities,ratingValueForMoney,1);
                            allHostelRefRating.set(meanRating).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: ");

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+e.getMessage());

                                }
                            });
                        }
                    }
                });
            }

        });




    }

    private void checkButton() {
        binding.post.setEnabled(ratingCleanliness!=0 && ratingEnvironment!=0 && ratingFacilities!=0
                && ratingFood !=0 && ratingStaff!=0 && ratingValueForMoney!=0 && ratingSecurity !=0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                    binding.post.setText("Update");
                    RatingModel model = value.toObject(RatingModel.class);
                    binding.ratingSecurity.setRating(model.getRatingSecurity());
                    binding.ratingValueForMoney.setRating(model.getRatingValueForMoney());
                    binding.ratingEnvironment.setRating(model.getRatingEnvironment());
                    binding.ratingFood.setRating(model.getRatingFood());
                    binding.ratingFacilities.setRating(model.getRatingFacilities());
                    binding.ratingStaff.setRating(model.getRatingStaff());
                    binding.ratingCleanliness.setRating(model.getRatingCleanliness());

                    if (model.getComment()!=null){
                        binding.comment.setText(model.getComment());
                    }
                }

            }
        });
    }
}