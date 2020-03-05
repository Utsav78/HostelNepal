package com.example.hostelnepal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HostelOwnerRegister extends AppCompatActivity {


    EditText hoFullName,hoEmail,hoPassword,hoPhoneNumber;
    Button hoRegisterBtn;
    TextView hoLoginBtn;
    FirebaseAuth fAuthHO;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_owner_register);

        hoFullName = findViewById(R.id.ownerName);
        hoEmail = findViewById(R.id.emailOwner);
        hoPassword = findViewById(R.id.passwordOwner);
        hoPhoneNumber = findViewById(R.id.ownerPhoneNumber);
        hoRegisterBtn = findViewById(R.id.hoRegisterBtn);

        progressBar = findViewById(R.id.progressBarOwnerRegister);
        fAuthHO = FirebaseAuth.getInstance();

        hoRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailho = hoEmail.getText().toString().trim();
                String passwordho = hoPassword.getText().toString().trim();
                String nameho =hoFullName.getText().toString().trim();
                String phonenumberho = hoPhoneNumber.getText().toString().trim();

                if(TextUtils.isEmpty(emailho)){

                    hoEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(nameho)){

                    hoFullName.setError("FullName is Required");
                    return;
                }
                if(TextUtils.isEmpty(phonenumberho)){

                    hoPhoneNumber.setError("Phone Number is Required");
                    return;
                }

                if (TextUtils.isEmpty(passwordho)){

                    hoPassword.setError("Password is Required ");
                    return;
                }

                if (passwordho.length()<6){

                    hoPassword.setError("Password is less than 6 Character");
                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase

                fAuthHO.createUserWithEmailAndPassword(emailho,passwordho).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(HostelOwnerRegister.this, "User is Registered", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(),DashboardHO.class));

                        }
                        else{

                            Toast.makeText(HostelOwnerRegister.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });




            }
        });
    }
}
