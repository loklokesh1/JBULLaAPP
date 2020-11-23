package com.mahammadjabi.jbulla;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahammadjabi.jbulla.BottomNavbarFragments.HomeFragment;
import com.mahammadjabi.jbulla.BottomNavbarFragments.NotificationFragment;
import com.mahammadjabi.jbulla.BottomNavbarFragments.ProfileFragment;
import com.mahammadjabi.jbulla.BottomNavbarFragments.SearchFragment;
import com.mahammadjabi.jbulla.NetworkCheck.MyService;
import com.mahammadjabi.jbulla.UserRegister.RegisterActivity;
import com.mahammadjabi.jbulla.UserRegister.SetupActivity;
import com.mahammadjabi.jbulla.databinding.ActivityMainBinding;
import com.mahammadjabi.jbulla.userPosts.UserPostsActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
//        implements ConnectivityReceiver.ConnectivityReceiverListner

{

    ActivityMainBinding binding;
    public static final String BroadcastStringForAction = "checkinternet";
    private IntentFilter mIntentFilter;

    public BottomNavigationView bottomNavigation;

    private NavigationView navigationView;
    private DrawerLayout dLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    private CircleImageView NavProfileImage;
    private TextView NavProfileUserName;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;

    //creating doubliePressToExit
    private boolean doublePressToExit = false;



    String currentUserID;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BroadcastStringForAction);
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);


        if (isOnline(getApplicationContext()))
        {
            //if the user in online
        }
        else
        {
            //if the user is OFFLINE
            Set_Visibility_Off();

        }


        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
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
        View navView = navigationView.inflateHeaderView(R.layout.nav_header_menu);
        View view;

        openFragment(new HomeFragment());


        NavProfileImage = (CircleImageView) navView.findViewById(R.id.nav_profile_image);
        NavProfileUserName = (TextView)  navView.findViewById(R.id.nav_profile_username);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                UserMenuSelector(item);
                return false;
            }
        });

        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    if(dataSnapshot.hasChild("username"))
                    {
                        String fullname = dataSnapshot.child("username").getValue().toString();
                        NavProfileUserName.setText(fullname);
                    }
                    if(dataSnapshot.hasChild("profileimage"))
                    {
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.with(MainActivity.this).load(image).placeholder(R.drawable.profile1).into(NavProfileImage);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Profile name doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });


    }


    //// code is checking response of isOnline starts

    public BroadcastReceiver MyBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BroadcastStringForAction));
            {
                if (intent.getStringExtra("online_status").equals("true"))
                {
                    //If the user is online do something here
                }
                else
                {
                    //if no internet connection do something here
                    Set_Visibility_Off();
                }
            }
        }
    };

    public boolean isOnline(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnectedOrConnecting())
            return true;
        else
            return false;
    }

    public void Set_Visibility_Off()
    {

        Intent noInternetConnection = new Intent(MainActivity.this, NoInternetActivity.class);
        startActivity(noInternetConnection);
        finish();
//        Snackbar.make(this.getWindow().getDecorView().findViewById(R.id.drawer_layout),"Please Check your InterNet Connection!!",Snackbar.LENGTH_LONG).show();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
//            getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.blacknavcolar));
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    changeNavBarColor();
//                }
//            },3200);
//        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(MyBroadcastReceiver,mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(MyBroadcastReceiver,mIntentFilter);
    }

    //// code is checking response of isOnline ends

    private boolean openFragment(Fragment fragment)
    {
        if (fragment != null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(HomeFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_search:
                            openFragment(SearchFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_post:
                            SendUserToUserPostsActivity();
                            return true;
                        case R.id.navigation_notifications:
                            openFragment(NotificationFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_profile:
                            openFragment(ProfileFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };


    private void SendUserToUserPostsActivity()
    {
        Intent postactivity = new Intent(MainActivity.this, UserPostsActivity.class);
        startActivity(postactivity);
    }


    @Override
    protected void onStart() {
        super.onStart();
        ///////////
        if (isConnected())
        {}
        else
        {
            Intent nointernet = new  Intent(MainActivity.this, NoInternetActivity.class);
            nointernet.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(nointernet);
            finish();
//          Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        ///////////
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

    public boolean isConnected()
    {
        boolean connected = false;
        try
        {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        }
        catch (Exception e)
        {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }


    private void CheckUserExistence() {

        final String current_user_id = mAuth.getCurrentUser().getUid();

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
        Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
        startActivity(setupIntent);
    }
    private void SendUserToLoginActivity()
    {
        Intent loginIntent = new Intent(MainActivity.this, RegisterActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
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
    @Override
    public void onBackPressed() {
        // to show the snackbar
        if (doublePressToExit)
        {
            super.onBackPressed();
            return;
        }
        this.doublePressToExit = true;
        Snackbar.make(this.getWindow().getDecorView().findViewById(R.id.drawer_layout),"Press back again to exit Bol app.",Snackbar.LENGTH_LONG).show();
//9666004335
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.blacknavcolar));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    changeNavBarColor();
                }
            },3200);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doublePressToExit = false;
            }
        },2000);

//        alert dialog to box to exit the app
//        new AlertDialog.Builder(this)
//                .setIcon(R.drawable.jblogo)
//                .setTitle("JBUlla")
//                .setMessage("Are you sure you want to exit?")
//                .setCancelable(true)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        MainActivity.super.onBackPressed();
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();


    }

    private void changeNavBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
        {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

    }

    private void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view,message,duration).show();
    }

//    @Override
//    public void onNetworkConnectionChangerd(boolean isConnected)
//    {
//        //changeActivity here is connected
//        showSnackbar(isConnected);
//
//
//    }
}