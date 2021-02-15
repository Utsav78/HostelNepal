package com.example.hostelnepal.Guest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelnepal.Adapter.BookingViewPagerAdapter;
import com.example.hostelnepal.Map.RetrieveMapLocation;
import com.example.hostelnepal.Model.Booking;
import com.example.hostelnepal.Model.BookingOwner;
import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityBookingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;


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
    FirebaseFirestore firebaseFirestore;
    RadioButton radioButton;
    String option ="";
    String documentID="";
    String guestID="";
    Button dialogCancel;
    Button dialogBook;
    DocumentSnapshot snapshot;
    String price1,price2,price3,price4;

    TextView room1,room2,room3,room4;
    String bookingPrice;
    String hostelName;
    String roomType;
    String ownerUserId;
    String gEmail,gName,gPhone;

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
        firebaseFirestore = FirebaseFirestore.getInstance();
        docRef = firebaseFirestore.document(path);

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

                room1 = dialog.findViewById(R.id.room1_price);
                room2 = dialog.findViewById(R.id.room2_price);
                room3 = dialog.findViewById(R.id.room3_price);
                room4 = dialog.findViewById(R.id.room4_price);

                room1.setText(price1);
                room2.setText(price2);
                room3.setText(price3);
                room4.setText(price4);

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
                        roomType = radioButton.getText().toString();

                        switch (radioButton.getText().toString()) {
                            case "1 Sitter":
                                option = "availableBeds1";
                                bookingPrice=price1;
                                break;

                            case "2 Sitter":
                                option = "availableBeds2";
                                bookingPrice=price2;
                                break;

                            case "3 Sitter":
                                option = "availableBeds3";
                                bookingPrice=price3;
                                break;

                            case "4 Sitter":
                                option = "availableBeds4";
                                bookingPrice = price4;
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
                        saveBooking();

                    }

                });

            }
        });

        DocumentReference guestRef = firebaseFirestore.document("Guest/"+FirebaseAuth.getInstance()
        .getCurrentUser().getUid());
        guestRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                gEmail =  snapshot.getString("Email");
                gName = snapshot.getString("FullName");
                gPhone =  snapshot.getString("PhoneNumber");

            }
        });


    }

    private void saveBooking() {
        Booking booking = new Booking(documentID,hostelName,roomType,bookingPrice);
        DocumentReference bookingRefGuest = firebaseFirestore.document("Guest/"+FirebaseAuth.getInstance().getCurrentUser().getUid()
        +"/"+"Booking/"+System.currentTimeMillis());

        DocumentReference bookingRefOwner = firebaseFirestore.document("HostelOwner/"+ownerUserId+
                "/"+"Booking/"+System.currentTimeMillis());

        bookingRefGuest.set(booking).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(BookingActivity.this, "Booking is successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookingActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+e.getMessage());

            }
        });

        BookingOwner bookingOwner = new BookingOwner(documentID,hostelName,gPhone,gEmail,roomType,bookingPrice,gName);
        bookingRefOwner.set(bookingOwner);

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

                ownerUserId = value.getString("userID");


                for (int i =0;i<8;i++){
                    boolean bool = value.getBoolean(field[i]);
                    if (bool)
                        stringBuffer.append(facilities[i]).append("\n");

                }


                binding.facilities.setText(stringBuffer.toString());
                binding.hostelName.setText(value.getString("nameOfHostel"));
                hostelName = value.getString("nameOfHostel");
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

    private void updateInOwner(String guestID, String documentID, String option, double value) {

        FirebaseFirestore.getInstance().document("HostelOwner/"+guestID+"/"+
                "Property Details/"+documentID).update(option,value);
        dialog.dismiss();

    }


    public void checkRadioButton(View view) {
        rg = dialog.findViewById(R.id.radio_group);
        radioID = rg.getCheckedRadioButtonId();
        radioButton = dialog.findViewById(radioID);
        Log.d(TAG, "checkRadioButton: "+radioID);
    }

    public void goToReviewsActivity(View view) {
        startActivity(new Intent(this,ReviewActivity.class));
    }

    public void goAddYourOwnReview(View view) {
        Intent intent = new Intent(this,OwnReviewActivity.class);
        intent.putExtra("documentId",documentID);
        startActivity(intent);
    }

    public void mapLocationHostel(View view) {
        Intent mapIntent = new Intent(this, RetrieveMapLocation.class);
        mapIntent.putExtra("guestId",guestID).putExtra("documentId",documentID);
        startActivity(mapIntent);


    }
}