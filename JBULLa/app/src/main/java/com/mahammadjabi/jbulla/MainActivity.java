package com.mahammadjabi.jbulla;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Toast;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.navigation.NavigationView;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.mahammadjabi.jbulla.UserRegister.RegisterActivity;
//import com.mahammadjabi.jbulla.UserRegister.SetupActivity;
//
//public class MainActivity extends AppCompatActivity {
//
//    private DrawerLayout dLayout;
//    private Toolbar toolbar;
//    private NavigationView navigationView;
//    private BottomNavigationView bottomNavigation;
//
//    private FirebaseAuth mAuth;
//    private DatabaseReference UsersRef;
//    private String currentUser ;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        mAuth = FirebaseAuth.getInstance();
//        currentUser = mAuth.getCurrentUser().getUid();
//
//
//
//
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        dLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
//        setSupportActionBar(toolbar);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dLayout.openDrawer(Gravity.LEFT);
//            }
//        });
//
//        navigationView = (NavigationView) findViewById(R.id.navigation);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item)
//            {
//                UserMenuSelector(item);
//                return false;
//            }
//        });
//
//    }
//    private void UserMenuSelector(MenuItem item)
//    {
//        switch(item.getItemId())
//        {
//            case R.id.nav_raise_your_voice:
//                Toast.makeText(MainActivity.this, "Raise your Voice", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.nav_darkmode:
//                Toast.makeText(MainActivity.this, "Dark Mode", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.nav_logout:
////                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
//                logout();
//                break;
//        }
//    }
//
//    private void logout()
//    {
//        mAuth.signOut();
//        Intent loginintent = new Intent(MainActivity.this,RegisterActivity.class);
//        loginintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(loginintent);
//    }
//
//    //**************************
//    //------------------------------
//    ///**************
//    @Override
//    public void onBackPressed() {
//        finishAffinity();
//    }
//    //*************
//
//
//    //////////////////////////////
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        final DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser);
//        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                User user = dataSnapshot.getValue(User.class);
//                if (!dataSnapshot.hasChild(currentUser)) {
//                    startActivity(new Intent(MainActivity.this, SetupActivity.class));
//                    finish();
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//
//
//    //////////////////////////////
//
//}

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahammadjabi.jbulla.UserRegister.RegisterActivity;
import com.mahammadjabi.jbulla.UserRegister.SetupActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout dLayout;
    private RecyclerView postList;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private CircleImageView NavProfileImage;
    private TextView NavProfileUserName;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;

    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dLayout.openDrawer(Gravity.LEFT);
            }
        });

//        View navView = navigationView.inflateHeaderView(R.layout.nav_header_menu);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                UserMenuSelector(item);
                return false;
            }
        });
//
//        NavProfileImage = (CircleImageView) navView.findViewById(R.id.nav_profile_image);
//        NavProfileUserName = (TextView) navView.findViewById(R.id.nav_profile_username);
//
//
//        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//
//                    if(dataSnapshot.hasChild("fullname")) {
//                        String fullname = dataSnapshot.child("fullname").getValue().toString();
//                        NavProfileUserName.setText(fullname);
//                    }
//                    if(dataSnapshot.hasChild("profileImage")) {
//                        String image = dataSnapshot.child("profileImage").getValue().toString();
//                        Picasso.with(MainActivity.this).load(image).placeholder(R.drawable.ic_launcher_foreground).into(NavProfileImage);
//                    }
//                    else {
//                        Toast.makeText(MainActivity.this, "Profile name doesn't exist", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            SendUserToLoginActivity();
        }
        else
        {
            CheckUserExistence();
        }
    }

    private void CheckUserExistence() {
        final String current_user_id = mAuth.getCurrentUser().getUid();

        UsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(current_user_id))
                {
                    sendUserToSetupActivity();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void sendUserToSetupActivity() {
        Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);

        startActivity(setupIntent);

    }




    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, RegisterActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true; //по нажатию на гамбургер открывает боковое меню
        }

        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_raise_your_voice:
                Toast.makeText(this, "nav_raise_your_voice", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_darkmode:
                Toast.makeText(this, "nav_darkmode", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                mAuth.signOut();
                SendUserToLoginActivity();
                break;
        }
    }
}