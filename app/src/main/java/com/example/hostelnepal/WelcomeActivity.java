package com.example.hostelnepal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import static com.example.hostelnepal.GuestRegister.EMAIL;
import static com.example.hostelnepal.GuestRegister.PASSWORD;

public class WelcomeActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();



    }


    public void hostelOwnerLogin(View view){
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
                                startActivity(new Intent(WelcomeActivity.this,DashboardHO.class));

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
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

                        startActivity(new Intent(WelcomeActivity.this,LoginActivityHO.class));
                    }
                }
            });
        }else{
            startActivity(new Intent(WelcomeActivity.this,LoginActivityHO.class));
        }




    }
    public void guestLogin(View view) {
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
                                startActivity(new Intent(WelcomeActivity.this,HomeActivity.class));

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
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
                        startActivity(new Intent(WelcomeActivity.this,LoginActivityG.class));

                    }
                }
            });
        }else{
            startActivity(new Intent(WelcomeActivity.this,LoginActivityG.class));
        }

    }


}
