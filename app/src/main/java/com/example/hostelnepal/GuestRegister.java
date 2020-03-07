package com.example.hostelnepal;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GuestRegister extends AppCompatActivity {

    EditText gFullName,gEmail,gPassword,gPhoneNumber;
    Button gRegisterBtn;
    TextView gLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;



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
        fAuth = FirebaseAuth.getInstance();

        gRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = gEmail.getText().toString().trim();
                String password = gPassword.getText().toString().trim();
                String name =gFullName.getText().toString().trim();
                String phonenumber = gPhoneNumber.getText().toString().trim();

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
