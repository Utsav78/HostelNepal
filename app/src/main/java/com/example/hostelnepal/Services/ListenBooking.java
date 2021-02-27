package com.example.hostelnepal.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.hostelnepal.Owner.BookingDetailsOwner;
import com.example.hostelnepal.Owner.HoBooking;
import com.example.hostelnepal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ListenBooking extends Service implements EventListener {
    FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    CollectionReference collectionReference;
    String userId="";

    public ListenBooking(){

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }




    @Override
    public void onCreate() {
        super.onCreate();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        fStore= FirebaseFirestore.getInstance();
        collectionReference = fStore.collection("HostelOwner").document(userId).collection("Booking");
        Toast.makeText(this, collectionReference.getPath(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        collectionReference.addSnapshotListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    public void showNotification(){

        Intent intent=new Intent(getBaseContext(), HoBooking.class);
        // intent.putExtra("userPhone",request.getPhone());

        PendingIntent contentIntent=PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //A PendingIntent is a token that you give to a foreign application
        // (e.g. NotificationManager, AlarmManager, Home Screen AppWidgetManager,
        // or other 3rd party applications), which allows the foreign application
        // to use your application's permissions to execute a predefined piece of code

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=
                    new NotificationChannel("HostelBook","Hostel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getBaseContext(),"HostelBook");

        builder.setAutoCancel(false)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Hostel")
                .setContentTitle("Booking Status")
                .setContentText("You have a new Booking in your hostel")
                .setContentIntent(contentIntent)
                .setContentInfo("Info")
                .setSmallIcon(R.drawable.logo);


        NotificationManager notificationManager=(NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(1,builder.build());




    }


    @Override
    public void onEvent(@Nullable Object value, @Nullable FirebaseFirestoreException error) {
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                Log.d("My Activity","Inside OnEvent");
                if(e!=null){
                    Log.d("My Activity", Objects.requireNonNull(e.getMessage()));
                }else {
                    assert queryDocumentSnapshots != null;
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        switch (doc.getType()){
                            case ADDED:
                                showNotification();
                                Log.d("My Activity","Document Added");
                                break;
                            case REMOVED:
                                Log.d("My Activity","Document Removed");
                                break;
                            case MODIFIED:
                                //showNotification();
                                break;
                        }

                    }
                }

            }
        });//.remove();
    }


}
