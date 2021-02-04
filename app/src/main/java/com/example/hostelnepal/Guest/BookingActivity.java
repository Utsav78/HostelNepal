package com.example.hostelnepal.Guest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hostelnepal.Adapter.BookingViewPagerAdapter;
import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityBookingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class BookingActivity extends AppCompatActivity {

    private static final String TAG = "BookingActivity";
    private ActivityBookingBinding binding;
    private String[] imageUrls;
    private StringBuffer stringBuffer;
    private String[] facilities;
    private String[] field;
    Dialog dialog ;
    RadioGroup rg;
    int radioID;
    double d=0;
    DocumentReference docRef;
    RadioButton radioButton;
    String option ="";
    String documentID="";
    String guestID="";
    Button dialogCancel;
    Button dialogBook;
    DocumentSnapshot snapshot;
    String price1,price2,price3,price4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        imageUrls = new String[8];
        stringBuffer = new StringBuffer();
        dialog = new Dialog(this);
        String path = getIntent().getStringExtra("path");
        docRef = FirebaseFirestore.getInstance().document(path);

        field = new String[]{"checkBoxOfWifi","checkBoxOfLaundry","checkBoxOfElectricity",
                "checkBoxOfParking","checkBoxOfCCTV",
                "checkBoxOfWater","checkBoxOfPlayground","checkBoxOfSecurity"};

        facilities = new String[]{"Wi-Fi","Laundry","24 Hrs Electricity","Parking Space",
                "CCTV Surveillance", "Hot & Cold Water","Playground","Security"};

        fillImagesAndInformation(path);
        BookingViewPagerAdapter adapter= new BookingViewPagerAdapter(this,imageUrls);
        binding.hostelImageViewPager.setAdapter(adapter);

        binding.bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.booking_options);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                dialog.show();

                dialogCancel = dialog.findViewById(R.id.cancel);
                dialogBook = dialog.findViewById(R.id.dialog_book);

                dialogCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialogBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        rg = dialog.findViewById(R.id.radio_group);
                        radioID = rg.getCheckedRadioButtonId();
                        radioButton = dialog.findViewById(radioID);

                        switch (radioButton.getText().toString()) {
                            case "1 Sitter":
                                option = "availableBeds1";
                                break;

                            case "2 Sitter":
                                option = "availableBeds2";
                                break;

                            case "3 Sitter":
                                option = "availableBeds3";
                                break;

                            case "4 Sitter":
                                option = "availableBeds4";
                                break;
                        }
                        d=snapshot.getDouble(option);

                        docRef.update(option,d-1 ).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    Toast.makeText(BookingActivity.this, "Booking is completed.", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(BookingActivity.this, "Something went Wrong.", Toast.LENGTH_SHORT).show();
                            }

                        });
                        updateInOwner(guestID,documentID,option, d -1);

                    }

                });

            }
        });

    }


    private void fillImagesAndInformation(String path) {

        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    Log.d(TAG, "onEvent: "+error.getMessage());
                    return;
                }
                imageUrls[0]=value.getString("uriOfBuilding");
                imageUrls[1]=value.getString("uriOfEnvironment");
                imageUrls[2]=value.getString("uriOfKitchen");
                imageUrls[3]=value.getString("uriOfRoom1");
                imageUrls[4]=value.getString("uriOfRoom2");
                imageUrls[5]=value.getString("uriOfRoom3");
                imageUrls[6]=value.getString("uriOfRoom4");
                imageUrls[7]=value.getString("uriOfWashroom");


                price1 = String.valueOf(value.getDouble("priceOfRoom1"));
                price2 = String.valueOf(value.getDouble("priceOfRoom2"));
                price3 = String.valueOf(value.getDouble("priceOfRoom3"));
                price4 = String.valueOf(value.getDouble("priceOfRoom4"));




                for (int i =0;i<8;i++){
                    boolean bool = value.getBoolean(field[i]);
                    if (bool)
                        stringBuffer.append(facilities[i]).append("\n");

                }


                binding.facilities.setText(stringBuffer.toString());
                binding.hostelName.setText(value.getString("nameOfHostel"));
                binding.hostelType.setText(value.getString("hostelType"));
                PropertyModel model = value.toObject(PropertyModel.class);
                guestID = model.getUserID();
                documentID = value.getId();
                Log.d(TAG, "onEvent: "+guestID+"\n"+documentID);
                snapshot = value;

                stringBuffer.delete(0,stringBuffer.length());


            }
        });
    }

    private void updateInOwner(String guestID, String documentID, String option,double value) {
        FirebaseFirestore.getInstance().document("HostelOwner/"+guestID+"/"+
                "Property Details/"+documentID).update(option,value-1);
        dialog.dismiss();


    }


    public void checkRadioButton(View view) {
        rg = dialog.findViewById(R.id.radio_group);
        radioID = rg.getCheckedRadioButtonId();
        radioButton = dialog.findViewById(radioID);
        Log.d(TAG, "checkRadioButton: "+radioID);
    }
}