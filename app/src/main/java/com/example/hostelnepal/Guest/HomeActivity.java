package com.example.hostelnepal.Guest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelnepal.Adapter.HomeAdapter;
import com.example.hostelnepal.Common.WelcomeActivity;
import com.example.hostelnepal.Adapter.HostelLocationAdapter;
import com.example.hostelnepal.Model.PropertyModel;
import com.example.hostelnepal.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.hostelnepal.Guest.GuestRegister.EMAIL;
import static com.example.hostelnepal.Guest.GuestRegister.FULL_NAME;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    TextView mGuestEmail,mFullNameOfGuest;
    CircleImageView mProfilePicture;
    NavigationView navigationView;
    RecyclerView hostelLocation;
    ArrayList<Integer> locationImages;
    ArrayList<String> locationNames;
    List<String> lNames = Arrays.asList("New Baneshwor","Gausala","Kalanki","Sankhamul","Maitighar","Tripureshwor"
    ,"Lalitpur");
    Integer[] images = {R.drawable.naya_baneshwor,R.drawable.gausala,R.drawable.kalanki,
    R.drawable.sankhamul,R.drawable.maitighar,R.drawable.tripureshwor,R.drawable.lalitpur};
    HostelLocationAdapter adapter;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db;
    DocumentReference documentReference;
    String userID;
    StorageReference storageReference;
    View view;
    ImageView hamburgerIcon;
    LinearLayout header;
    RecyclerView rvForYou;
    private CollectionReference colRef;
    HomeAdapter homeAdapter;
    private static final String TAG = "HomeActivity";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        locationImages = new ArrayList<>();
        locationNames = new ArrayList<>();
        mFirebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        colRef = db.collection("All Hostels");
        hostelLocation = findViewById(R.id.hostel_location);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        hamburgerIcon = findViewById(R.id.hamburgerIcon);
        userID = mFirebaseAuth.getCurrentUser().getUid();

        storageReference = FirebaseStorage.getInstance().getReference("Guest/"+userID+"/"+"ProfilePicture");

        view = navigationView.getHeaderView(0);
        mFullNameOfGuest = view.findViewById(R.id.fullNameOfGuest);
        mGuestEmail = view.findViewById(R.id.guestEmail);
        mProfilePicture=view.findViewById(R.id.imageView3);
        header = view.findViewById(R.id.header);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        //hamburgerIcon reaction
        hamburgerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        documentReference = db.document("Guest"+"/"+userID);

       documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
             mFullNameOfGuest.setText(documentSnapshot.getString(FULL_NAME));
             mGuestEmail.setText(documentSnapshot.getString(EMAIL));
            }
        });
       storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {
               Picasso.get().load(uri).into(mProfilePicture);

           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {

           }
       });

        initImagesAndNames();
        initForYouRecyclerView();

        //Header onclick reaction
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(getApplicationContext(),EditProfileOfGuest.class));
            }
        });
    }

    private void initForYouRecyclerView() {
        Query query = colRef.orderBy("nameOfHostel", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<PropertyModel> options = new FirestoreRecyclerOptions.Builder<PropertyModel>().
                setQuery(query,PropertyModel.class).build();
        homeAdapter = new HomeAdapter(options);
        rvForYou = findViewById(R.id.recycler_view_for_you);
       // rvForYou.setHasFixedSize(true); This has created the problem
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        rvForYou.setLayoutManager(gridLayoutManager);
        rvForYou.setAdapter(homeAdapter);

        homeAdapter.setOnClickListener(new HomeAdapter.OnClickListener() {
            @Override
            public void onClick(DocumentSnapshot documentSnapshot, int position) {
                Intent newIntent = new Intent(HomeActivity.this,BookingActivity.class);
                newIntent.putExtra("path",documentSnapshot.getReference().getPath());
                Log.d(TAG, "onClick: "+documentSnapshot.getReference().getPath());
                startActivity(newIntent);
            }
        });
    }

    private void initImagesAndNames() {
        locationNames.addAll(lNames);
        locationImages.addAll(Arrays.asList(images));
        initRecyclerView();

    }

    private void initRecyclerView() {
        hostelLocation = findViewById(R.id.hostel_location);
         adapter = new HostelLocationAdapter(this,locationImages,locationNames);
        hostelLocation.setAdapter(adapter);
        hostelLocation.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter.setOnClickListener(new HostelLocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name) {
                Intent intent = new Intent(HomeActivity.this,RecyclerLocationActivity.class);
                intent.putExtra("Name",name);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);


        if (menuItem.getItemId() == R.id.nav_logout){
            mFirebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();
        }
        if (menuItem.getItemId() == R.id.nav_saved){
            startActivity(new Intent(this,SavedBookingActivity.class));
        }
        if (menuItem.getItemId() == R.id.nav_aboutUs){
            startActivity(new Intent(HomeActivity.this,AboutUsActivity.class));
        }
        if (menuItem.getItemId() == R.id.nav_contact){
            startActivity(new Intent(HomeActivity.this,ContactUs.class));
        }
        if (menuItem.getItemId() == R.id.nav_terms){
            startActivity(new Intent(HomeActivity.this,TermsActivity.class));
        }
        if (menuItem.getItemId() == R.id.nav_setting){
            startActivity(new Intent(HomeActivity.this,EditProfileOfGuest.class));
        }


        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        homeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        homeAdapter.stopListening();
    }

    public void openSearchActivity(View view) {
        startActivity(new Intent(HomeActivity.this,SearchActivity.class));
    }
}
