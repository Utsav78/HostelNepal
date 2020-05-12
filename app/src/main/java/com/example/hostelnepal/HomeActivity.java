package com.example.hostelnepal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.hostelnepal.GuestRegister.EMAIL;
import static com.example.hostelnepal.GuestRegister.FULL_NAME;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    TextView mGuestEmail,mFullNameOfGuest;
    ImageView mProfilePicture;
    NavigationView navigationView;
    RecyclerView hostelLocation;
    ArrayList<Integer> locationImages;
    ArrayList<String> locationNames;
    List<String> lNames = Arrays.asList("New Baneshwor","Gausala","Kalanki","Sankhamul","Maitighar","Tripureshwor"
    ,"Lalitpur");
    Integer[] images = {R.drawable.naya_baneshwor,R.drawable.gausala,R.drawable.kalanki,
    R.drawable.sankhamul,R.drawable.maitighar,R.drawable.tripureshwor,R.drawable.lalitpur};

    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db;
    DocumentReference documentReference;
    String userID;
    View view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        locationImages = new ArrayList<>();
        locationNames = new ArrayList<>();
        mFirebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        hostelLocation = findViewById(R.id.hostel_location);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        //This isnot the right way
      /*  mGuestEmail = navigationView.findViewById(R.id.guestEmail);
        mFullNameOfGuest = navigationView.findViewById(R.id.fullNameOfGuest);
        mProfilePicture =navigationView.findViewById(R.id.imageView3);*/


        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);//to enable hamburger icon
        actionBarDrawerToggle.syncState();

/*      This is also wrong way of doing.
        view= navigationView.getHeaderView(0);
        mGuestEmail = view.findViewById(R.id.guestEmail);
        mFullNameOfGuest = findViewById(R.id.fullNameOfGuest);
        mProfilePicture =findViewById(R.id.imageView3);

      mFullNameOfGuest.setText("Utsav Budathoki");*/







        userID = mFirebaseAuth.getCurrentUser().getUid();
        documentReference = db.document("Guest/"+userID);
      //  mFullNameOfGuest.setText("Utsav Budathoki");//this is the problem. You cannot reference the view in navigation directly.

       documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
           //    mFullNameOfGuest.setText(documentSnapshot.getString(FULL_NAME));
           //    mGuestEmail.setText(documentSnapshot.getString(EMAIL));
            }
        });

        initImagesAndNames();









    }

    private void initImagesAndNames() {
        locationNames.addAll(lNames);
        locationImages.addAll(Arrays.asList(images));

        initRecyclerView();



    }

    private void initRecyclerView() {
        hostelLocation = findViewById(R.id.hostel_location);
        HostelLocationAdapter adapter = new HostelLocationAdapter(this,locationImages,locationNames);
        hostelLocation.setAdapter(adapter);
        hostelLocation.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);

        if (menuItem.getItemId() == R.id.nav_home){


        }

        if (menuItem.getItemId() == R.id.nav_notification){


        }


        return true;
    }


}
