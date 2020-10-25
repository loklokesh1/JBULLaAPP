package com.jabiullamahammad.sabol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jabiullamahammad.sabol.UserRegister.RegisterActivity;
import com.jabiullamahammad.sabol.UserRegister.SetupActivity;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigation;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dLayout.openDrawer(Gravity.LEFT);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                UserMenuSelector(item);
                return false;
            }
        });

    }
    private void UserMenuSelector(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.nav_raise_your_voice:
                Toast.makeText(MainActivity.this, "Raise your Voice", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_darkmode:
                Toast.makeText(MainActivity.this, "Dark Mode", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
//                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                logout();
                break;
        }
    }

    private void logout()
    {
        mAuth.signOut();
        sendUserToLoginActivity();
    }

    private void sendUserToLoginActivity()
    {
        Intent loginintent = new Intent(MainActivity.this,RegisterActivity.class);
        loginintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginintent);
        finish();
    }

    //**************************
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
        {
            sendUserToLoginActivity();
        }
        else
        {
            CheckUser();
        }
    }

    private void CheckUser()
    {
        final String current_user_id =mAuth.getCurrentUser().getUid();

        UsersRef.addValueEventListener(new ValueEventListener() {
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

    private void sendUserToSetupActivity()
    {
        Intent setupintent = new Intent(MainActivity.this, SetupActivity.class);
        setupintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupintent);
        finish();

    }

    //------------------------------
    ///**************
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
    //*************

}