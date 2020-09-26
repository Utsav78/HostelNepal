package com.example.hostelnepal.Owner;

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
import android.widget.Toast;

import com.example.hostelnepal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivityHO extends AppCompatActivity {

    FirebaseAuth fAuthHO;
    EditText hoEmail,hoPassword;
    Button hoLoginBtn;
    ProgressBar progressBar;
    private static final String TAG = "LoginActivityHO";




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

                loginUser(emailho, passwordho);
            }
        });


    }

    private void loginUser(final String emailho, final String passwordho) {
        FirebaseFirestore.getInstance().collection("HostelOwner").whereEqualTo("Email",emailho).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){


                            fAuthHO.signInWithEmailAndPassword(emailho, passwordho).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivityHO.this, "" +
                                            "Login is successful", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(LoginActivityHO.this, DashboardHO.class));


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivityHO.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onFailure: on signin "+e.getMessage());

                                }
                            });
                    }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivityHO.this, "User credentials is invalid", Toast.LENGTH_SHORT).show();


                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivityHO.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void hostelOwnerRegister(View view){
        startActivity(new Intent(LoginActivityHO.this,HostelOwnerRegister.class));
    }


}
