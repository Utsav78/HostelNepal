package com.example.hostelnepal.Guest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.hostelnepal.Adapter.BookingViewPagerAdapter;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityBookingBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class BookingActivity extends AppCompatActivity {

    private static final String TAG = "BookingActivity";
    private ActivityBookingBinding binding;
    private String[] imageUrls;
    private StringBuffer stringBuffer;
    private String[] facilities;
    String[] field;
    Dialog dialog ;
    RadioGroup rg;
    int radioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        imageUrls = new String[8];
        stringBuffer = new StringBuffer();
        dialog = new Dialog(this);

        //getting the path of documents from SearchActivity and Adapter
        String path = getIntent().getStringExtra("path");
        field = new String[]{"checkBoxOfWifi","checkBoxOfLaundry","checkBoxOfElectricity",
                "checkBoxOfParking","checkBoxOfCCTV",
                "checkBoxOfWater","checkBoxOfPlayground","checkBoxOfSecurity"};
        facilities = new String[]{"Wi-Fi","Laundry","24 Hrs Electricity","Parking Space",
                "CCTV Surveillance", "Hot & Cold Water","Playground","Security"};

        fillImagesAndInformation(path);
        BookingViewPagerAdapter adapter= new BookingViewPagerAdapter(this,imageUrls);
        binding.hostelImageViewPager.setAdapter(adapter);


    }


    private void fillImagesAndInformation(String path) {
        DocumentReference docRef = FirebaseFirestore.getInstance().document(path);
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                imageUrls[0]=value.getString("uriOfBuilding");
                imageUrls[1]=value.getString("uriOfEnvironment");
                imageUrls[2]=value.getString("uriOfKitchen");
                imageUrls[3]=value.getString("uriOfRoom1");
                imageUrls[4]=value.getString("uriOfRoom2");
                imageUrls[5]=value.getString("uriOfRoom3");
                imageUrls[6]=value.getString("uriOfRoom4");
                imageUrls[7]=value.getString("uriOfWashroom");

                for (int i =0;i<8;i++){
                    boolean bool = value.getBoolean(field[i]);
                    if (bool)
                        stringBuffer.append(facilities[i]).append("\n");

                }
                binding.facilities.setText(stringBuffer.toString());
                binding.hostelName.setText(value.getString("nameOfHostel"));
                binding.hostelType.setText(value.getString("hostelType"));

            }
        });
    }

    public void openDialogBox(View view) {
        dialog.setContentView(R.layout.booking_options);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        dialog.show();

        Button dialogCancel = dialog.findViewById(R.id.cancel);
        Button dialogBook = dialog.findViewById(R.id.dialog_book);

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    public void checkRadioButton(View view) {
        rg = dialog.findViewById(R.id.radio_group);
        radioID = rg.getCheckedRadioButtonId();
        Log.d(TAG, "checkRadioButton: "+radioID);
    }
}