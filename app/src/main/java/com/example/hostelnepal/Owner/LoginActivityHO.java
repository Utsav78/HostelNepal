package com.example.hostelnepal.Owner;

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

import com.example.hostelnepal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivityHO extends AppCompatActivity {

    FirebaseAuth fAuthHO;
    EditText hoEmail,hoPassword;
    Button hoLoginBtn;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ho);
        hoEmail = findViewById(R.id.emailOwner);
        hoPassword = findViewById(R.id.passwordOwnerLogin);
        hoLoginBtn = findViewById(R.id.loginHO);
        progressBar = findViewById(R.id.progressBarOwnerLogin);

        fAuthHO = FirebaseAuth.getInstance();



        hoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailho = hoEmail.getText().toString().trim();
                String passwordho = hoPassword.getText().toString().trim();

                if (TextUtils.isEmpty(emailho)) {

                    hoEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(passwordho)) {

                    hoPassword.setError("Password is Required ");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                //authenticate the user

                fAuthHO.signInWithEmailAndPassword(emailho, passwordho).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);

                            startActivity(new Intent(getApplicationContext(),DashboardHO.class));
                        } else {
                            Toast.makeText(LoginActivityHO.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }

                    }
                });

            }


        });


    }
    public void hostelOwnerRegister(View view){
        startActivity(new Intent(LoginActivityHO.this,HostelOwnerRegister.class));
    }


}
