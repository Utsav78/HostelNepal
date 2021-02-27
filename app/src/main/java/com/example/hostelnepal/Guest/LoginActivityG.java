package com.example.hostelnepal.Guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hostelnepal.Owner.DashboardHO;
import com.example.hostelnepal.Owner.LoginActivityHO;
import com.example.hostelnepal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivityG extends AppCompatActivity {

    FirebaseAuth fAuth;
    EditText gEmail,gPassword;
    Button gLoginBtn;
    FirebaseFirestore db;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_g);
        pd = new ProgressDialog(this);

        pd.setTitle("Login");
        pd.setMessage("Processing...");
        pd.setCanceledOnTouchOutside(false);

        gEmail = findViewById(R.id.emailGuest);
        gPassword = findViewById(R.id.passwordGuestLogin);
        gLoginBtn = findViewById(R.id.loginG);


        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        gLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = gEmail.getText().toString().trim();
                final String password = gPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    gEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {

                    gPassword.setError("Password is Required ");
                    return;
                }


                pd.show();
                loginUser(email,password);



                }
            });
        }

    private void loginUser(final String email, final String password) {
        FirebaseFirestore.getInstance().collection("Guest").whereEqualTo("Email", email).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()){

                            fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    pd.dismiss();

                                    startActivity(new Intent(LoginActivityG.this, HomeActivity.class));


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(LoginActivityG.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                    }else{
                            pd.dismiss();
                            Toast.makeText(LoginActivityG.this, "User Credential is invalid", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(LoginActivityG.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


        public void studentRegister(View v) {

                Intent intent = new Intent(LoginActivityG.this, GuestRegister.class);
                startActivity(intent);

            }

    }