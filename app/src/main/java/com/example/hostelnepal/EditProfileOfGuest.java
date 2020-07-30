package com.example.hostelnepal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.hostelnepal.EditProfileActivity.PICK_IMAGES_REQUEST_CODE;
import static com.example.hostelnepal.GuestRegister.EMAIL;
import static com.example.hostelnepal.GuestRegister.FULL_NAME;
import static com.example.hostelnepal.GuestRegister.PHONE_NUMBER;

public class EditProfileOfGuest extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    StorageReference storageReference;
    FirebaseUser user;
    String userID;

    CircleImageView mEditProfilePicture;
    EditText mEditName,mEditPhone,mEditEmail;
    Button mEditSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_of_guest);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        storageReference = FirebaseStorage.getInstance().getReference("Guest/"+userID+"/"+"ProfilePicture");

        mEditName = findViewById(R.id.editTextName);
        mEditPhone = findViewById(R.id.editTextPhoneNumber);
        mEditEmail = findViewById(R.id.editTextEmail);
        mEditSave = findViewById(R.id.btnSave);
        mEditProfilePicture= findViewById(R.id.editProfilePicture);
        user = firebaseAuth.getCurrentUser();

        documentReference = firebaseFirestore.document("Guest/"+userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mEditName.setText(documentSnapshot.getString(FULL_NAME));
                mEditEmail.setText(documentSnapshot.getString(EMAIL));
                mEditPhone.setText(documentSnapshot.getString(PHONE_NUMBER));


            }
        });
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mEditProfilePicture);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileOfGuest.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        mEditProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               // startActivityForResult(openGalleryIntent,PICK_IMAGES_REQUEST_CODE);
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(EditProfileOfGuest.this);
            }
        });

        mEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditEmail.getText().toString().trim().isEmpty() || mEditName.getText().toString().trim().isEmpty()
                        || mEditPhone.getText().toString().trim().isEmpty()){
                    Toast.makeText(EditProfileOfGuest.this, "Some field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = mEditEmail.getText().toString().trim();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Map<String,Object> edited =new HashMap<>();
                        edited.put(FULL_NAME,mEditName.getText().toString());
                        edited.put(PHONE_NUMBER,mEditPhone.getText().toString());
                        edited.put(EMAIL,mEditEmail.getText().toString());
                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfileOfGuest.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfileOfGuest.this, "Profile not Updated", Toast.LENGTH_SHORT).show();

                            }
                        });
                        Toast.makeText(EditProfileOfGuest.this, "Email is Updated.", Toast.LENGTH_SHORT).show();

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //This has some problem . wh
                        new AlertDialog.Builder(EditProfileOfGuest.this).setTitle("Problem").setMessage(e.toString()).setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseAuth.signOut();
                                startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
                                finish();


                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).create().show();

                    }
                });
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       /* if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == Activity.RESULT_OK
        && data !=null && data.getData() != null){
            Uri imageUri = data.getData();
            uploadFileToFirebase(imageUri);
        }

        */
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                uploadFileToFirebase(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFileToFirebase(Uri imageUri) {
        if (imageUri != null){
            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(EditProfileOfGuest.this, "Profile Picture Updated.", Toast.LENGTH_SHORT).show();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(mEditProfilePicture);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfileOfGuest.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }
}