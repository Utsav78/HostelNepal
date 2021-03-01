package com.example.hostelnepal.Owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.hostelnepal.Map.HostelLocationMap;
import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.example.hostelnepal.databinding.ActivityEditPropertyBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static com.example.hostelnepal.Owner.AddProperty.REQUEST_CODE_BUILDING;
import static com.example.hostelnepal.Owner.AddProperty.REQUEST_CODE_DOCUMENT;
import static com.example.hostelnepal.Owner.AddProperty.REQUEST_CODE_KITCHEN;
import static com.example.hostelnepal.Owner.AddProperty.REQUEST_CODE_ROOM1;
import static com.example.hostelnepal.Owner.AddProperty.REQUEST_CODE_ROOM2;
import static com.example.hostelnepal.Owner.AddProperty.REQUEST_CODE_ROOM3;
import static com.example.hostelnepal.Owner.AddProperty.REQUEST_CODE_ROOM4;
import static com.example.hostelnepal.Owner.AddProperty.REQUEST_CODE_SURROUNDING;
import static com.example.hostelnepal.Owner.AddProperty.REQUEST_CODE_WASHROOM;

public class EditPropertyActivity extends AppCompatActivity {
    ActivityEditPropertyBinding binding;
    private static final String TAG = "EditPropertyActivity";

    String path,hostelId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String ownerId;
    DocumentReference allHostelRef,ownerSideRef;
    StorageReference storageReference;

    private String[] imagesName;
    private String[] downloadableUri;
    private Boolean[] booleanCheckBox;

    private String nameOfHostel, locality, city, typeOfHostel,propertyDescription;
    private Integer priceOfRoom1, priceOfRoom2, priceOfRoom3, priceOfRoom4;

    Dialog dialog;
    private RadioButton radioButton;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPropertyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Edit Property");

        path = getIntent().getStringExtra("path");
        hostelId = getIntent().getStringExtra("hostelId");
        ownerId = auth.getCurrentUser().getUid();

        imagesName = new String[]{"Room1", "Room2", "Room3", "Room4", "Document", "Washroom",
                "Building", "Kitchen", "Surrounding"};

        booleanCheckBox = new Boolean[]{false, false, false, false, false, false, false, false};
        downloadableUri = new String[9];

        ownerSideRef = db.document(path);
        allHostelRef = db.document("All Hostels/"+hostelId);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_box);
        dialog.setCanceledOnTouchOutside(false);


        ownerSideRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                PropertyModel model = value.toObject(PropertyModel.class);
                binding.editTextNameOfHostel.setText(model.getNameOfHostel());
                binding.editTextLocation.setText(model.getCity());
                binding.editTextLocality.setText(model.getLocality());

                binding.textPrice1.setText(String.valueOf(model.getPriceOfRoom1()));
                binding.textPrice2.setText(String.valueOf(model.getPriceOfRoom2()));
                binding.textPrice3.setText(String.valueOf(model.getPriceOfRoom3()));
                binding.price4.setText(String.valueOf(model.getPriceOfRoom4()));
                binding.editTextDescription.setText(model.getPropertyDescription());

                Picasso.get()
                        .load(Uri.parse(model.getUriOfRoom1()))
                        .into(binding.imageView1);

                Picasso.get()
                        .load(Uri.parse(model.getUriOfRoom2()))
                        .into(binding.imageView2);


                Picasso.get()
                        .load(Uri.parse(model.getUriOfRoom3()))
                        .into(binding.imageView3);

                Picasso.get()
                        .load(Uri.parse(model.getUriOfRoom4()))
                        .into(binding.imageView4);

                Picasso.get()
                        .load(Uri.parse(model.getUriOfBuilding()))
                        .into(binding.imageViewBuilding);

                Picasso.get()
                        .load(Uri.parse(model.getUriOfEnvironment()))
                        .into(binding.imageViewSurrounding);

                Picasso.get()
                        .load(Uri.parse(model.getUriOfKitchen()))
                        .into(binding.imageViewKitchen);

                Picasso.get()
                        .load(Uri.parse(model.getUriOfWashroom()))
                        .into(binding.imageViewWashroom);

                Picasso.get()
                        .load(Uri.parse(model.getUriOfDocument()))
                        .into(binding.imageViewDocument);

                downloadableUri[0]=model.getUriOfRoom1();
                downloadableUri[1]=model.getUriOfRoom2();
                downloadableUri[2]=model.getUriOfRoom3();
                downloadableUri[3]=model.getUriOfRoom4();
                downloadableUri[4]=model.getUriOfDocument();
                downloadableUri[5]=model.getUriOfWashroom();
                downloadableUri[6]=model.getUriOfBuilding();
                downloadableUri[7]=model.getUriOfKitchen();
                downloadableUri[8]=model.getUriOfEnvironment();


            }
        });

        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                int radioID = binding.radioGroupHostelType.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);

                nameOfHostel = binding.editTextNameOfHostel.getText().toString();
                locality = binding.editTextLocality.getText().toString();
                city = binding.editTextLocation.getText().toString();
                priceOfRoom1 = Integer.parseInt(binding.textPrice1.getText().toString());
                priceOfRoom2 = Integer.parseInt(binding.textPrice2.getText().toString());
                priceOfRoom3 = Integer.parseInt(binding.textPrice3.getText().toString());
                priceOfRoom4 = Integer.parseInt(binding.price4.getText().toString());
                typeOfHostel = radioButton.getText().toString();
                propertyDescription= binding.editTextDescription.getText().toString();

                allHostelRef.update("nameOfHostel",nameOfHostel,
                        "city",city,
                        "locality",locality,
                        "priceOfRoom1",priceOfRoom1,
                        "priceOfRoom2",priceOfRoom2,
                        "priceOfRoom3",priceOfRoom3,
                        "priceOfRoom4",priceOfRoom4,
                        "hostelType",typeOfHostel,
                        "uriOfRoom1",downloadableUri[0],
                        "uriOfRoom2",downloadableUri[1],
                        "uriOfRoom3",downloadableUri[2],
                        "uriOfRoom4",downloadableUri[3],
                        "uriOfDocument",downloadableUri[4],
                        "uriOfWashroom",downloadableUri[5],
                        "uriOfBuilding",downloadableUri[6],
                        "uriOfKitchen",downloadableUri[7],
                        "uriOfEnvironment",downloadableUri[8],
                        "checkBoxOfWifi",booleanCheckBox[0],
                        "checkBoxOfElectricity",booleanCheckBox[1],
                        "checkBoxOfWater",booleanCheckBox[2],
                        "checkBoxOfLaundry",booleanCheckBox[3],
                        "checkBoxOfParking",booleanCheckBox[4],
                        "checkBoxOfCCTV",booleanCheckBox[5],
                        "checkBoxOfSecurity",booleanCheckBox[6],
                        "checkBoxOfPlayground",booleanCheckBox[7],
                        "propertyDescription",propertyDescription);


                ownerSideRef.update("nameOfHostel",nameOfHostel,
                        "city",city,
                        "locality",locality,
                        "priceOfRoom1",priceOfRoom1,
                        "priceOfRoom2",priceOfRoom2,
                        "priceOfRoom3",priceOfRoom3,
                        "priceOfRoom4",priceOfRoom4,
                        "hostelType",typeOfHostel,
                        "uriOfRoom1",downloadableUri[0],
                        "uriOfRoom2",downloadableUri[1],
                        "uriOfRoom3",downloadableUri[2],
                        "uriOfRoom4",downloadableUri[3],
                        "uriOfDocument",downloadableUri[4],
                        "uriOfWashroom",downloadableUri[5],
                        "uriOfBuilding",downloadableUri[6],
                        "uriOfKitchen",downloadableUri[7],
                        "uriOfEnvironment",downloadableUri[8],
                        "checkBoxOfWifi",booleanCheckBox[0],
                        "checkBoxOfElectricity",booleanCheckBox[1],
                        "checkBoxOfWater",booleanCheckBox[2],
                        "checkBoxOfLaundry",booleanCheckBox[3],
                        "checkBoxOfParking",booleanCheckBox[4],
                        "checkBoxOfCCTV",booleanCheckBox[5],
                        "checkBoxOfSecurity",booleanCheckBox[6],
                        "checkBoxOfPlayground",booleanCheckBox[7],
                        "propertyDescription",propertyDescription).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dialog.dismiss();
                        Toast.makeText(EditPropertyActivity.this, "Update is Successful.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ViewProperty.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(EditPropertyActivity.this, "Something went Wrong.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: Update Failure:"+e.getMessage());
                        startActivity(new Intent(getApplicationContext(),ViewProperty.class));

                    }
                });





            }
        });






    }

    public void checkRadioButton(View view) {
        int radioID = binding.radioGroupHostelType.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);

    }

    public void checkingCheckBox(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkbox_wifi:
                booleanCheckBox[0] = checked;
                break;

            case R.id.checkbox_electricity:
                booleanCheckBox[1] = checked;
                break;

            case R.id.checkbox_water:
                booleanCheckBox[2] = checked;
                break;

            case R.id.checkbox_laundry:
                booleanCheckBox[3] = checked;
                break;

            case R.id.checkbox_parking:
                booleanCheckBox[4] = checked;
                break;

            case R.id.checkbox_cctv:
                booleanCheckBox[5] = checked;
                break;

            case R.id.checkbox_security:
                booleanCheckBox[6] = checked;
                break;

            case R.id.checkbox_playground:
                booleanCheckBox[7] = checked;
                break;
        }

    }

    public void openGallery(View view) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        switch (view.getId()) {
            case R.id.image_view_1:
                startActivityForResult(openGalleryIntent, REQUEST_CODE_ROOM1    );
                break;

            case R.id.image_view_2:
                startActivityForResult(openGalleryIntent, REQUEST_CODE_ROOM2);
                break;

            case R.id.image_view_3:
                startActivityForResult(openGalleryIntent, REQUEST_CODE_ROOM3);
                break;

            case R.id.image_view_4:
                startActivityForResult(openGalleryIntent, REQUEST_CODE_ROOM4);
                break;

            case R.id.image_view_document:
                startActivityForResult(openGalleryIntent, REQUEST_CODE_DOCUMENT);
                break;

            case R.id.image_view_washroom:
                startActivityForResult(openGalleryIntent, REQUEST_CODE_WASHROOM);
                break;

            case R.id.image_view_building:
                startActivityForResult(openGalleryIntent, REQUEST_CODE_BUILDING);
                break;

            case R.id.image_view_kitchen:
                startActivityForResult(openGalleryIntent, REQUEST_CODE_KITCHEN);
                break;


            case R.id.image_view_surrounding:
                startActivityForResult(openGalleryIntent, REQUEST_CODE_SURROUNDING);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {

            switch (requestCode) {

                case REQUEST_CODE_ROOM1:
                    uploadImage(data.getData(), imagesName[0], 0);

                    Picasso.get().load(data.getData()).into(binding.imageView1);

                    break;

                case REQUEST_CODE_ROOM2:
                    uploadImage(data.getData(), imagesName[1], 1);

                    Picasso.get().load(data.getData()).into(binding.imageView2);

                    break;

                case REQUEST_CODE_ROOM3:
                    uploadImage(data.getData(), imagesName[2], 2);
                    Picasso.get().load(data.getData()).into(binding.imageView3);

                    break;

                case REQUEST_CODE_ROOM4:
                    uploadImage(data.getData(), imagesName[3], 3);
                    Picasso.get().load(data.getData()).into(binding.imageView4);

                    break;

                case REQUEST_CODE_DOCUMENT:
                    uploadImage(data.getData(), imagesName[4], 4);
                    Picasso.get().load(data.getData()).into(binding.imageViewDocument);

                    break;

                case REQUEST_CODE_WASHROOM:
                    uploadImage(data.getData(), imagesName[5], 5);
                    Picasso.get().load(data.getData()).into(binding.imageViewWashroom);

                    break;

                case REQUEST_CODE_BUILDING:
                    uploadImage(data.getData(), imagesName[6], 6);
                    Picasso.get().load(data.getData()).into(binding.imageViewBuilding);

                    break;

                case REQUEST_CODE_KITCHEN:
                    uploadImage(data.getData(), imagesName[7], 7);
                    Picasso.get().load(data.getData()).into(binding.imageViewKitchen);

                    break;

                case REQUEST_CODE_SURROUNDING:
                    uploadImage(data.getData(), imagesName[8], 8);
                    Picasso.get().load(data.getData()).into(binding.imageViewSurrounding);

                    break;
            }
        }
    }

    private void uploadImage(Uri data, String s, int i) {
        dialog.show();
        storageReference = FirebaseStorage.getInstance().getReference("Updated/" +s+"/"+System.currentTimeMillis());
        storageReference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        switch (i) {
                            case 0:
                                downloadableUri[0] = uri.toString();
                                dialog.dismiss();
                                break;

                            case 1:
                                downloadableUri[1] = uri.toString();
                                dialog.dismiss();
                                break;

                            case 2:
                                downloadableUri[2] = uri.toString();
                                dialog.dismiss();
                                break;

                            case 3:
                                downloadableUri[3] = uri.toString();
                                dialog.dismiss();
                                break;

                            case 4:
                                downloadableUri[4] = uri.toString();
                                dialog.dismiss();
                                break;

                            case 5:
                                downloadableUri[5] = uri.toString();
                                dialog.dismiss();

                                break;

                            case 6:
                                downloadableUri[6] = uri.toString();
                                dialog.dismiss();
                                break;

                            case 7:
                                downloadableUri[7] = uri.toString();
                                dialog.dismiss();
                                break;
                            case 8:
                                downloadableUri[8] = uri.toString();
                                dialog.dismiss();
                                break;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditPropertyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_map_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit_map){
            updateLocation();
            return true;
        }else {
            return super.onOptionsItemSelected(item);

        }
    }

    private void updateLocation() {
        Intent intent = new Intent(this, HostelLocationMap.class);
        intent.putExtra("documentId",hostelId);
        startActivity(intent);


    }
}