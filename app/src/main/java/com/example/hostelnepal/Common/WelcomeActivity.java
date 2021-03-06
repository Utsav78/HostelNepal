package com.example.hostelnepal.Common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hostelnepal.Owner.DashboardHO;
import com.example.hostelnepal.Guest.HomeActivity;
import com.example.hostelnepal.Guest.LoginActivityG;
import com.example.hostelnepal.Owner.LoginActivityHO;
import com.example.hostelnepal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import static com.example.hostelnepal.Guest.GuestRegister.EMAIL;
import static com.example.hostelnepal.Guest.GuestRegister.PASSWORD;

public class WelcomeActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    String userID;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);
        pd.setTitle("Login");
        pd.setMessage("Processing...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

    }


    public void hostelOwnerLogin(View view){
        pd.show();
        if (firebaseAuth.getCurrentUser() != null){
            userID = firebaseAuth.getCurrentUser().getUid();
            DocumentReference docRef = db.document("HostelOwner"+"/"+userID);
            docRef.addSnapshotListener(WelcomeActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot.exists()){
                        String email = documentSnapshot.getString("Email");
                        String password = documentSnapshot.getString("Password");
                        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(WelcomeActivity.this, DashboardHO.class));
                                pd.dismiss();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                new AlertDialog.Builder(WelcomeActivity.this).setTitle("Error").setMessage(e.toString())
                                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        });

                            }
                        });


                    }else{

                        startActivity(new Intent(WelcomeActivity.this, LoginActivityHO.class));
                        pd.dismiss();
                    }
                }
            });
        }else{
            startActivity(new Intent(WelcomeActivity.this,LoginActivityHO.class));
            pd.dismiss();
        }




    }
    public void guestLogin(View view) {
        pd.show();
        if (firebaseAuth.getCurrentUser() != null){
            userID = firebaseAuth.getCurrentUser().getUid();
            DocumentReference docRef = db.document("Guest"+"/"+userID);
            docRef.addSnapshotListener(WelcomeActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot.exists()){
                        String email = documentSnapshot.getString(EMAIL);
                        String password = documentSnapshot.getString(PASSWORD);
                        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                                pd.dismiss();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                new AlertDialog.Builder(WelcomeActivity.this).setTitle("Error").setMessage(e.toString())
                                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                pd.dismiss();

                                            }
                                        });

                            }
                        });


                    }else{
                        startActivity(new Intent(WelcomeActivity.this, LoginActivityG.class));
                        pd.dismiss();

                    }
                }
            });
        }else{
            startActivity(new Intent(WelcomeActivity.this,LoginActivityG.class));
            pd.dismiss();
        }

    }


}
