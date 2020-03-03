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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivityG extends AppCompatActivity {

    FirebaseAuth fAuth;
    EditText gEmail,gPassword;
    Button gLoginBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_g);

        gEmail = findViewById(R.id.emailGuest);
        gPassword = findViewById(R.id.passwordGuestLogin);
        gLoginBtn = findViewById(R.id.loginG);
        progressBar = findViewById(R.id.progressBarLoginGuest);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }

        gLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = gEmail.getText().toString().trim();
                String password = gPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    gEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {

                    gPassword.setError("Password is Required ");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                //authenticate the user

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);

                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            Toast.makeText(LoginActivityG.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }

                    }
                });

            }


        });
    }


        public void studentRegister(View v){

            Intent intent = new Intent(LoginActivityG.this, GuestRegister.class);
            startActivity(intent);
        }
    }