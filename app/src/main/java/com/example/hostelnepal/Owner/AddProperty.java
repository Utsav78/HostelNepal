package com.example.hostelnepal.Owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class AddProperty extends AppCompatActivity {
    public static final int REQUEST_CODE_ROOM1 = 1;
    public static final int REQUEST_CODE_ROOM2 = 2;
    public static final int REQUEST_CODE_ROOM3 = 3;
    public static final int REQUEST_CODE_ROOM4 = 4;
    public static final int REQUEST_CODE_DOCUMENT = 5;
    public static final int REQUEST_CODE_WASHROOM = 6;
    public static final int REQUEST_CODE_BUILDING = 7;
    public static final int REQUEST_CODE_KITCHEN = 8;
    public static final int REQUEST_CODE_SURROUNDING = 9;
    public static final String TAG = "TAG";
    private EditText editTextNameOfHostel, editTextLocality, editTextCity, editTextPrice1, editTextPrice2, editTextPrice3, editTextPrice4;
    private ImageView imageViewRoom1, imageViewRoom2, imageViewRoom3, imageViewRoom4, imageViewDocument, imageViewWashroom, imageViewSurrounding, imageViewKitchen, imageViewBuilding;
    // private CheckBox checkBoxWifi,checkBoxLaundry,checkBoxElectricity,checkBoxParking,checkBoxCCTV,checkBoxWater,checkBoxPlayground,checkBoxSecurity;
    private Button buttonSave;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private String nameOfHostel, locality, city, typeOfHostel;
    private Integer priceOfRoom1, priceOfRoom2, priceOfRoom3, priceOfRoom4;
    private String[] imagesName;

    private String[] downloadableUri;
    private Boolean[] booleanCheckBox;

    FirebaseAuth aFirebaseAuth;
    FirebaseFirestore db;
    DocumentReference addPropertyDocumentReference;
    DocumentReference allHostel;
    StorageReference addPropertyStorageReference;
    PropertyModel propertyModel;
    Dialog dialog;
    public static final String TAGGY = "AddProperty";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        db = FirebaseFirestore.getInstance();

        editTextNameOfHostel = findViewById(R.id.edit_text_name_of_hostel);
        editTextLocality = findViewById(R.id.edit_text_locality);
        editTextCity = findViewById(R.id.edit_text_location);
        editTextPrice1 = findViewById(R.id.edit_text_price1);
        editTextPrice2 = findViewById(R.id.edit_text_price2);
        editTextPrice3 = findViewById(R.id.edit_text_price3);
        editTextPrice4 = findViewById(R.id.edit_text_price4);

        imageViewRoom1 = findViewById(R.id.image_view_1);
        imageViewRoom2 = findViewById(R.id.image_view_2);
        imageViewRoom3 = findViewById(R.id.image_view_3);
        imageViewRoom4 = findViewById(R.id.image_view_4);
        imageViewDocument = findViewById(R.id.image_view_document);
        imageViewWashroom = findViewById(R.id.image_view_washroom);
        imageViewSurrounding = findViewById(R.id.image_view_surrounding);
        imageViewKitchen = findViewById(R.id.image_view_kitchen);
        imageViewBuilding = findViewById(R.id.image_view_building);

        buttonSave = findViewById(R.id.button_save);
        radioGroup = findViewById(R.id.radio_group_hostel_type);

     /*   checkBoxWifi = findViewById(R.id.checkbox_wifi);
        checkBoxLaundry = findViewById(R.id.checkbox_laundry);
        checkBoxElectricity = findViewById(R.id.checkbox_electricity);
        checkBoxParking = findViewById(R.id.checkbox_parking);
        checkBoxCCTV = findViewById(R.id.checkbox_cctv);
        checkBoxWater= findViewById(R.id.checkbox_water);
        checkBoxPlayground = findViewById(R.id.checkbox_playground);
        checkBoxSecurity = findViewById(R.id.checkbox_security);

      */

        aFirebaseAuth = FirebaseAuth.getInstance();
        addPropertyDocumentReference = db.document("HostelOwner/" + aFirebaseAuth.getCurrentUser().getUid() + "/" + "Property Details/" + System.currentTimeMillis());
        allHostel =db.document("All Hostels/"+System.currentTimeMillis());
        imagesName = new String[]{"Room1", "Room2", "Room3", "Room4", "Document", "Washroom",
                "Building", "Kitchen", "Surrounding"};

        booleanCheckBox = new Boolean[]{false, false, false, false, false, false, false, false};
        downloadableUri = new String[9];
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_box);
        dialog.setCanceledOnTouchOutside(false);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);

                if (editTextNameOfHostel.getText().toString().trim().length() != 0 && editTextCity.getText().toString().trim().length() != 0 &&
                        editTextLocality.getText().toString().trim().length() != 0 && editTextPrice1.getText().toString().length() != 0
                        && editTextPrice2.getText().toString().trim().length() != 0 && editTextPrice3.getText().toString().trim().length() != 0
                        && editTextPrice4.getText().toString().trim().length() != 0 && downloadableUri[0] != null && downloadableUri[1] != null && downloadableUri[2] != null && downloadableUri[3] != null &&
                        downloadableUri[4] != null && downloadableUri[5] != null && downloadableUri[6] != null && downloadableUri[7] != null && downloadableUri[8] != null) {


                    dialog.show();


                    nameOfHostel = editTextNameOfHostel.getText().toString();
                    locality = editTextLocality.getText().toString();
                    city = editTextCity.getText().toString();
                    priceOfRoom1 = Integer.parseInt(editTextPrice1.getText().toString());
                    priceOfRoom2 = Integer.parseInt(editTextPrice2.getText().toString());
                    priceOfRoom3 = Integer.parseInt(editTextPrice3.getText().toString());
                    priceOfRoom4 = Integer.parseInt(editTextPrice4.getText().toString());
                    typeOfHostel = radioButton.getText().toString();

                    propertyModel = new PropertyModel(nameOfHostel, typeOfHostel, city, locality, priceOfRoom1, priceOfRoom2, priceOfRoom3, priceOfRoom4,
                            downloadableUri[0], downloadableUri[1], downloadableUri[2], downloadableUri[3], downloadableUri[4], downloadableUri[5], downloadableUri[6],
                            downloadableUri[7], downloadableUri[8], booleanCheckBox[0], booleanCheckBox[1], booleanCheckBox[2], booleanCheckBox[3], booleanCheckBox[4], booleanCheckBox[5],
                            booleanCheckBox[6], booleanCheckBox[7]);


                   // addPropertyDocumentReference = FirebaseFirestore.getInstance().document("HostelOwner/" + aFirebaseAuth.getCurrentUser().getUid()
                   //         + "/" + "Property Details/" + System.currentTimeMillis());

                    addPropertyDocumentReference.set(propertyModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddProperty.this, "Profile is completely ready. Look in View Property to know the details", Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddProperty.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();


                        }
                    });

                    allHostel.set(propertyModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddProperty.this, "Your Hostel is added Successfully in our App.", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddProperty.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {
                    Toast.makeText(AddProperty.this, "Some Field are missing", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });


    }

    public void openGallery(View view) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        switch (view.getId()) {
            case R.id.image_view_1:
                startActivityForResult(openGalleryIntent, REQUEST_CODE_ROOM1);
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

                    Picasso.get().load(data.getData()).into(imageViewRoom1);

                    break;

                case REQUEST_CODE_ROOM2:
                    uploadImage(data.getData(), imagesName[1], 1);

                    Picasso.get().load(data.getData()).into(imageViewRoom2);

                    break;

                case REQUEST_CODE_ROOM3:
                    uploadImage(data.getData(), imagesName[2], 2);
                    Picasso.get().load(data.getData()).into(imageViewRoom3);

                    break;

                case REQUEST_CODE_ROOM4:
                    uploadImage(data.getData(), imagesName[3], 3);
                    Picasso.get().load(data.getData()).into(imageViewRoom4);

                    break;

                case REQUEST_CODE_DOCUMENT:
                    uploadImage(data.getData(), imagesName[4], 4);
                    Picasso.get().load(data.getData()).into(imageViewDocument);

                    break;

                case REQUEST_CODE_WASHROOM:
                    uploadImage(data.getData(), imagesName[5], 5);
                    Picasso.get().load(data.getData()).into(imageViewWashroom);

                    break;

                case REQUEST_CODE_BUILDING:
                    uploadImage(data.getData(), imagesName[6], 6);
                    Picasso.get().load(data.getData()).into(imageViewBuilding);

                    break;

                case REQUEST_CODE_KITCHEN:
                    uploadImage(data.getData(), imagesName[7], 7);
                    Picasso.get().load(data.getData()).into(imageViewKitchen);

                    break;

                case REQUEST_CODE_SURROUNDING:
                    uploadImage(data.getData(), imagesName[8], 8);
                    Picasso.get().load(data.getData()).into(imageViewSurrounding);

                    break;
            }
        }
    }

    private void uploadImage(Uri data, String s, final int k) {
        dialog.show();
        addPropertyStorageReference = FirebaseStorage.getInstance().getReference("AddProperty/" +s+"/"+System.currentTimeMillis());
        addPropertyStorageReference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                addPropertyStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        switch (k) {
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
                        Toast.makeText(AddProperty.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });
    }

    public void checkRadioButton(View view) {
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
    }

    public void checkingCheckBox(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkbox_wifi:
                if (checked)
                    booleanCheckBox[0] = true;
                else
                    booleanCheckBox[0] = false;
                break;

            case R.id.checkbox_electricity:
                if (checked)
                    booleanCheckBox[1] = true;
                else
                    booleanCheckBox[1] = false;
                break;

            case R.id.checkbox_water:
                if (checked)
                    booleanCheckBox[2] = true;
                else
                    booleanCheckBox[2] = false;
                break;

            case R.id.checkbox_laundry:
                if (checked)
                    booleanCheckBox[3] = true;
                else
                    booleanCheckBox[3] = false;
                break;

            case R.id.checkbox_parking:
                if (checked)
                    booleanCheckBox[4] = true;
                else
                    booleanCheckBox[4] = false;
                break;

            case R.id.checkbox_cctv:
                if (checked)
                    booleanCheckBox[5] = true;
                else
                    booleanCheckBox[5] = false;
                break;

            case R.id.checkbox_security:
                if (checked)
                    booleanCheckBox[6] = true;
                else
                    booleanCheckBox[6] = false;
                break;

            case R.id.checkbox_playground:
                if (checked)
                    booleanCheckBox[7] = true;
                else
                    booleanCheckBox[7] = false;
                break;
        }

    }
}

