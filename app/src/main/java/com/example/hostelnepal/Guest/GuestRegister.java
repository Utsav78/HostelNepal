package com.example.hostelnepal.Guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelnepal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GuestRegister extends AppCompatActivity {

    public static final String FULL_NAME = "FullName";
    public static final String EMAIL = "Email";
    public static final String PHONE_NUMBER = "PhoneNumber";
    public static final String PASSWORD = "Password";
    public static final String TAG = "TAG";
    EditText gFullName,gEmail,gPassword,gPhoneNumber;
    Button gRegisterBtn;
    TextView gLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStoreGuest;
    String userID;
    DocumentReference documentReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_register);

        gFullName = findViewById(R.id.name);
        gEmail = findViewById(R.id.emailGuest);
        gPassword = findViewById(R.id.password);
        gPhoneNumber = findViewById(R.id.phoneNumber);
        gRegisterBtn = findViewById(R.id.gRegisterBtn);

        progressBar = findViewById(R.id.progressBar);
        fStoreGuest = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();


        gRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = gEmail.getText().toString().trim();
                final String password = gPassword.getText().toString().trim();
                final String name =gFullName.getText().toString().trim();
                final String phonenumber = gPhoneNumber.getText().toString().trim();

                if(TextUtils.isEmpty(email)){

                    gEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(name)){

                    gFullName.setError("FullName is Required");
                    return;
                }
                if(TextUtils.isEmpty(phonenumber)){

                    gPhoneNumber.setError("Phone Number is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){

                    gPassword.setError("Password is Required ");
                    return;
                }

                if (password.length()<6){

                    gPassword.setError("Password is less than 6 Character");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()){

                            //email verification
                            FirebaseUser fUser = fAuth.getCurrentUser();
                            fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(GuestRegister.this, "Verification Email has been Sent.", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","On Failure: Email verification code not sent"+e.getMessage());

                                }
                            });





                            Toast.makeText(GuestRegister.this, "User is Registered", Toast.LENGTH_SHORT).show();


                            userID = fAuth.getCurrentUser().getUid();
                            documentReference = fStoreGuest.collection("Guest").document(userID);

                            Map<String,Object> guest= new HashMap<>();
                            guest.put(FULL_NAME,name);
                            guest.put(EMAIL,email);
                            guest.put(PHONE_NUMBER,phonenumber);
                            guest.put(PASSWORD,password);
                            documentReference.set(guest).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess:User Profile is created  "+userID);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: "+e.toString());

                                }
                            });

                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                        }
                        else{

                            Toast.makeText(GuestRegister.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });




            }
        });


    }
}
