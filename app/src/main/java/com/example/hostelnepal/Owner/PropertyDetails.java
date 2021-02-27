package com.example.hostelnepal.Owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelnepal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class PropertyDetails extends AppCompatActivity {
    private static final String TAG = "PropertyDetails";
    TextView tvNameOfHostel,tvLocality,tvTypeOfHostel,tvCity,tvPrice1,tvPrice2,tvPrice3,tvPrice4,tvFacilities
            ,tvDescription;
    ImageView ivRoom1,ivRoom2,ivRoom3,ivRoom4,ivDocument,ivWashroom,ivBuilding,ivKitchen,ivSurrounding;
    DocumentReference docRef;
    FirebaseAuth firebaseAuth;
    String[] facilities;
    String[] field;
    StringBuffer stringBuffer;

    //for hostelId and path;
    String path;
    String hostelId;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Property Details");

        firebaseAuth = FirebaseAuth.getInstance();

        tvNameOfHostel = findViewById(R.id.text_name_of_hostel);
        tvTypeOfHostel = findViewById(R.id.text_type_of_hostel);
        tvLocality= findViewById(R.id.text_locality);
        tvCity = findViewById(R.id.text_location);
        tvPrice1 = findViewById(R.id.text_price1);
        tvPrice2 = findViewById(R.id.text_price2);
        tvPrice3 = findViewById(R.id.text_price3);
        tvPrice4 = findViewById(R.id.price4);
        tvFacilities = findViewById(R.id.tv_facilities);
        tvDescription = findViewById(R.id.tv_description);

        ivRoom1 = findViewById(R.id.image_view_1);
        ivRoom2 = findViewById(R.id.image_view_2);
        ivRoom3 = findViewById(R.id.image_view_3);
        ivRoom4 = findViewById(R.id.image_view_4);
        ivDocument = findViewById(R.id.image_view_document);
        ivWashroom = findViewById(R.id.image_view_washroom);
        ivBuilding = findViewById(R.id.image_view_building);
        ivKitchen = findViewById(R.id.image_view_kitchen);
        ivSurrounding = findViewById(R.id.image_view_surrounding);

        facilities = new String[]{"Wi-Fi","Laundry","24 Hrs Electricity","Parking Space","CCTV Surveillance",
        "Hot & Cold Water","Playground","Security"};

        field = new String[]{"checkBoxOfWifi","checkBoxOfLaundry","checkBoxOfElectricity","checkBoxOfParking"
        ,"checkBoxOfCCTV","checkBoxOfWater","checkBoxOfPlayground","checkBoxOfSecurity"};

        stringBuffer = new StringBuffer();

        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        hostelId = intent.getStringExtra("hostelId");
        docRef = FirebaseFirestore.getInstance().document(path);
        docRef.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                tvNameOfHostel.setText(documentSnapshot.getString("nameOfHostel"));
                tvTypeOfHostel.setText(documentSnapshot.getString("hostelType"));
                tvLocality.setText(documentSnapshot.getString("locality"));
                tvCity.setText(documentSnapshot.getString("city"));
                tvPrice1.setText(String.valueOf(documentSnapshot.getDouble("priceOfRoom1")));
                tvPrice2.setText(String.valueOf(documentSnapshot.getDouble("priceOfRoom2")));
                tvPrice3.setText(String.valueOf(documentSnapshot.getDouble("priceOfRoom3")));
                tvPrice4.setText(String.valueOf(documentSnapshot.getDouble("priceOfRoom4")));
                tvDescription.setText(documentSnapshot.getString("propertyDescription"));


                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfRoom1"))).into(ivRoom1);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfRoom2"))).into(ivRoom2);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfRoom3"))).into(ivRoom3);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfRoom4"))).into(ivRoom4);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfDocument"))).into(ivDocument);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfWashroom"))).into(ivWashroom);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfBuilding"))).into(ivBuilding);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfKitchen"))).into(ivKitchen);
                Picasso.get().load(Uri.parse(documentSnapshot.getString("uriOfEnvironment"))).into(ivSurrounding);

                for (int i=0;i<8;i++){
                    boolean bool = documentSnapshot.getBoolean(field[i]);
                    if (bool){
                        stringBuffer.append(facilities[i]).append("\n");
                    }

                }
                tvFacilities.setText(stringBuffer.toString());


            }
        });

    }

    public void editPropertyDetails() {
        Intent intent = new Intent(this,EditPropertyActivity.class);
        intent.putExtra("path",path).putExtra("hostelId",hostelId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.property_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                deleteDocument();
                return true;

            case R.id.edit:
                editPropertyDetails();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteDocument() {
        new AlertDialog.Builder(this).setTitle("Delete Hostel")
                .setMessage("Are you sure you want to delete the hostel?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(PropertyDetails.this, "Yes is clicked", Toast.LENGTH_SHORT).show();
                       docRef.delete();


                       FirebaseFirestore.getInstance().document("All Hostels/"+hostelId)
                               .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               Toast.makeText(PropertyDetails.this, "Hostel deleted successfully.", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(PropertyDetails.this,DashboardHO.class));
                               finish();

                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(PropertyDetails.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                               Log.d(TAG, "onFailure: Delete document:"+e.getMessage());
                           }
                       });

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(PropertyDetails.this, "No is clicked", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).show();

    }
}