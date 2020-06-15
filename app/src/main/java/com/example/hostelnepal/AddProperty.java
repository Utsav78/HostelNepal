package com.example.hostelnepal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    public static final String DOWNLOADABLEURIFAILED = "DOWNLOADABLEURIFAILED";
    private EditText editTextNameOfHostel,editTextLocality,editTextCity,editTextPrice1,editTextPrice2,editTextPrice3,editTextPrice4;
    private ImageView imageViewRoom1,imageViewRoom2,imageViewRoom3,imageViewRoom4,imageViewDocument,imageViewWashroom,imageViewSurrounding,imageViewKitchen,imageViewBuilding;
    private CheckBox checkBoxWifi,checkBoxLaundry,checkBoxElectricity,checkBoxParking,checkBoxCCTV,checkBoxWater,checkBoxPlayground,checkBoxSecurity;
    private Button buttonSave;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private String nameOfHostel,locality,city,typeOfHostel;
    private Integer priceOfRoom1,priceOfRoom2,priceOfRoom3,priceOfRoom4;
    private String[] imagesName;
    private String[] deviceUri ;
    private String[] downloadableUri;
    private Boolean[] booleanCheckBox;

    FirebaseAuth aFirebaseAuth;
    DocumentReference addPropertyDocumentReference;
    StorageReference addPropertyStorageReference;
    PropertyModel propertyModel;
    Dialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

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

        checkBoxWifi = findViewById(R.id.checkbox_wifi);
        checkBoxLaundry = findViewById(R.id.checkbox_laundry);
        checkBoxElectricity = findViewById(R.id.checkbox_electricity);
        checkBoxParking = findViewById(R.id.checkbox_parking);
        checkBoxCCTV = findViewById(R.id.checkbox_cctv);
        checkBoxWater= findViewById(R.id.checkbox_water);
        checkBoxPlayground = findViewById(R.id.checkbox_playground);
        checkBoxSecurity = findViewById(R.id.checkbox_security);

        aFirebaseAuth = FirebaseAuth.getInstance();
        addPropertyDocumentReference = FirebaseFirestore.getInstance().document("HostelOwner/"+aFirebaseAuth.getCurrentUser().getUid()+"/"+"Property Details/"+System.currentTimeMillis());

        imagesName = new String[]{"Room1","Room2","Room3","Room4","Document","Washroom",
                "Building","Kitchen","Surrounding"};
        deviceUri = new String[9];
       booleanCheckBox = new Boolean[]{false,false,false,false,false,false,false,false};
        downloadableUri = new String[9];
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_box);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);

                if (editTextNameOfHostel.getText().toString().trim().length() !=0 && editTextCity.getText().toString().trim().length() !=0 &&
                editTextLocality.getText().toString().trim().length() !=0 && editTextPrice1.getText().toString().length() !=0
                && editTextPrice2.getText().toString().trim().length() != 0 && editTextPrice3.getText().toString().trim().length() !=0
                && editTextPrice4.getText().toString().trim().length() !=0 && deviceUri[0] !=null && deviceUri[1] !=null &&deviceUri[2] !=null &&deviceUri[3] !=null &&
                        deviceUri[4] !=null && deviceUri[5] !=null && deviceUri[6] !=null && deviceUri[7] !=null && deviceUri[8] !=null ){

                    dialog.show();


                    //looping
                    for(int i =0;i<9;i++){

                        addPropertyStorageReference = FirebaseStorage.getInstance().
                                getReference("HostelOwner/"+aFirebaseAuth.getCurrentUser().getUid()+"/"+"Property's pictures/"+imagesName[i]);
                        final int finalI = i;
                        addPropertyStorageReference.putFile(Uri.parse(deviceUri[finalI])).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                addPropertyStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        downloadableUri[finalI]= uri.toString();


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(DOWNLOADABLEURIFAILED, "onFailure: "+e.getMessage());
                                        Toast.makeText(AddProperty.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        return;

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddProperty.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onFailure: "+e.getMessage());
                                return;

                            }
                        });

                    }
                    nameOfHostel= editTextNameOfHostel.getText().toString();
                    locality = editTextLocality.getText().toString();
                    city =editTextCity.getText().toString();
                    priceOfRoom1 = Integer.parseInt(editTextPrice1.getText().toString());
                    priceOfRoom2 = Integer.parseInt(editTextPrice2.getText().toString());
                    priceOfRoom3 = Integer.parseInt(editTextPrice3.getText().toString());
                    priceOfRoom4 = Integer.parseInt(editTextPrice4.getText().toString());
                    typeOfHostel = radioButton.getText().toString();

                    propertyModel = new PropertyModel(nameOfHostel,typeOfHostel,city,locality,priceOfRoom1,priceOfRoom2,priceOfRoom3,priceOfRoom4,
                            downloadableUri[0],downloadableUri[1],downloadableUri[2],downloadableUri[3],downloadableUri[4],downloadableUri[5],downloadableUri[6],
                            downloadableUri[7],downloadableUri[8],booleanCheckBox[0],booleanCheckBox[1],booleanCheckBox[2],booleanCheckBox[3],booleanCheckBox[4],booleanCheckBox[5],
                    booleanCheckBox[6],booleanCheckBox[7]);


                    addPropertyDocumentReference = FirebaseFirestore.getInstance().document("HostelOwner/"+aFirebaseAuth.getCurrentUser().getUid()
                    +"/"+"Property Details/"+System.currentTimeMillis());

                    addPropertyDocumentReference.set(propertyModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddProperty.this, "Profile is completely ready", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddProperty.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            return;

                        }
                    });

                }
                else{
                    Toast.makeText(AddProperty.this, "Some Field are missing", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });




    }
    public void openGallery(View view){
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        switch (view.getId()){
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

        if (resultCode == RESULT_OK && data != null && data.getData() != null){

            switch (requestCode){

                case REQUEST_CODE_ROOM1 :
                    deviceUri[0]=data.getData().toString();
                    Picasso.get().load(data.getData()).into(imageViewRoom1);
                    break;

                case REQUEST_CODE_ROOM2 :
                    deviceUri[1]=data.getData().toString();
                    Picasso.get().load(data.getData()).into(imageViewRoom2);
                    break;

                case REQUEST_CODE_ROOM3 :
                    deviceUri[2]=data.getData().toString();
                    Picasso.get().load(data.getData()).into(imageViewRoom3);
                    break;

                case REQUEST_CODE_ROOM4 :
                    deviceUri[3]=data.getData().toString();
                    Picasso.get().load(data.getData()).into(imageViewRoom4);
                    break;

                case REQUEST_CODE_DOCUMENT :
                    deviceUri[4]=data.getData().toString();
                    Picasso.get().load(data.getData()).into(imageViewDocument);
                    break;

                case REQUEST_CODE_WASHROOM :
                    deviceUri[5]=data.getData().toString();
                    Picasso.get().load(data.getData()).into(imageViewWashroom);
                    break;

                case REQUEST_CODE_BUILDING :
                    deviceUri[6]=data.getData().toString();
                    Picasso.get().load(data.getData()).into(imageViewBuilding);
                    break;

                case REQUEST_CODE_KITCHEN :
                    deviceUri[7]=data.getData().toString();
                    Picasso.get().load(data.getData()).into(imageViewKitchen);
                    break;

                case REQUEST_CODE_SURROUNDING :
                    deviceUri[8]=data.getData().toString();
                    Picasso.get().load(data.getData()).into(imageViewSurrounding);
                    break;

            }
        }
    }

    public void checkRadioButton(View view) {
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
    }

    public void checkingCheckBox(View view) {
        boolean checked = ((CheckBox)view).isChecked();
        switch (view.getId()){
            case R.id.checkbox_wifi:
                if (checked)
                    booleanCheckBox[0]= true;
                else
                    booleanCheckBox[0]=false;
                break;

            case R.id.checkbox_electricity:
                if (checked)
                    booleanCheckBox[1]= true;
                else
                    booleanCheckBox[1]=false;
                break;

            case R.id.checkbox_water:
                if (checked)
                    booleanCheckBox[2]= true;
                else
                    booleanCheckBox[2]=false;
                break;

            case R.id.checkbox_laundry:
                if (checked)
                    booleanCheckBox[3]= true;
                else
                    booleanCheckBox[3]=false;
                break;

            case R.id.checkbox_parking:
                if (checked)
                    booleanCheckBox[4]= true;
                else
                    booleanCheckBox[4]=false;
                break;

            case R.id.checkbox_cctv:
                if (checked)
                    booleanCheckBox[5]= true;
                else
                    booleanCheckBox[5]=false;
                break;

            case R.id.checkbox_security:
                if (checked)
                    booleanCheckBox[6]= true;
                else
                    booleanCheckBox[6]=false;
                break;

            case R.id.checkbox_playground:
                if (checked)
                    booleanCheckBox[7]= true;
                else
                    booleanCheckBox[7]=false;
                break;
        }

    }
}
