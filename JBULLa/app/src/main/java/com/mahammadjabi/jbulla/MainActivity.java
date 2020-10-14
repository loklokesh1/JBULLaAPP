package com.mahammadjabi.jbulla;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mahammadjabi.jbulla.UserRegister.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigation;

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;

    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

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
        Intent loginintent = new Intent(MainActivity.this,RegisterActivity.class);
        loginintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginintent);
    }

    //**************************
    //------------------------------
    ///**************
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
    //*************

}