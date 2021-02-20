package com.example.hostelnepal.Owner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityAvailabilityDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AvailabilityDetailsActivity extends AppCompatActivity {
    ActivityAvailabilityDetailsBinding binding;
    private static final String TAG = "AvailabilityDetails";
    DocumentReference allHostelRef,ownerSideRef;
    FirebaseFirestore db;
    String ownerId;
    String hostelId;
    String hostelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAvailabilityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();
        String path = getIntent().getStringExtra("path");
        hostelId = getIntent().getStringExtra("hostelId");
        ownerSideRef = db.document(path);
        Log.d(TAG, "onCreate: Path:"+path);

        ownerSideRef.addSnapshotListener(this, (value, error) -> {
            PropertyModel model = value.toObject(PropertyModel.class);
            binding.etRoom1.setText(String.valueOf(model.getAvailableBeds1()));
            binding.etRoom2.setText(String.valueOf(model.getAvailableBeds2()));
            binding.etRoom3.setText(String.valueOf(model.getAvailableBeds3()));
            binding.etRoom4.setText(String.valueOf(model.getAvailableBeds4()));
            Log.d(TAG, "onEvent: Value of no of beds:"+model.getAvailableBeds1());
            ownerId = model.getUserID();
            binding.hostelName.setText(model.getNameOfHostel());
        });

    }

    public void updateAvailableBeds(View view) {
        allHostelRef = db.document("All Hostels/"+hostelId);
        Integer bed1 = Integer.parseInt(binding.etRoom1.getText().toString());
        Integer bed2 = Integer.parseInt(binding.etRoom2.getText().toString());
        Integer bed3 = Integer.parseInt(binding.etRoom3.getText().toString());
        Integer bed4 = Integer.parseInt(binding.etRoom4.getText().toString());

        allHostelRef.update("availableBeds1",bed1,"availableBeds2",bed2,
                "availableBeds3",bed3,
                "availableBeds4",bed4);
        ownerSideRef.update("availableBeds1",bed1,"availableBeds2",bed2,
                "availableBeds3",bed3,
                "availableBeds4",bed4).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AvailabilityDetailsActivity.this, "Update successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),DashboardHO.class));
                finish();
            }
        });
    }
}