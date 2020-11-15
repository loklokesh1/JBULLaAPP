package com.mahammadjabi.jbulla;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
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
import com.mahammadjabi.jbulla.UserRegister.RegisterActivity;
import com.mahammadjabi.jbulla.UserRegister.SetupActivity;
import com.mahammadjabi.jbulla.userPosts.UserPostsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNavigation;

    private NavigationView navigationView;
    private DrawerLayout dLayout;
    private RecyclerView postList;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    private CircleImageView NavProfileImage;
    private TextView NavProfileUserName;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef,PostsRef;
    private FirebaseRecyclerOptions<Posts> options;
//    private FirebaseRecyclerAdapter<Posts, PostsViewHolder> adapter;



    String currentUserID;
    private Fragment frameLayout;
    private List<Fragment>  fragmentList;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openFragment(new HomeFragment());

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

//
//
//        fragmentList = new ArrayList<>();
//        fragmentList.add(new HomeFragment());
//        fragmentList.add(new SearchFragment());
//        fragmentList.add(new NotificationFragment());
//        fragmentList.add(new NotificationFragment());
//        fragmentList.add(new ProfileFragment());
//
//
//        tabLayout = findViewById(R.id.tablayout);
//        tabLayout.getTabAt(2).getIcon().setTintList(ColorStateList.valueOf(getResources().getColor(R.color.common_google_signin_btn_text_dark_focused)));
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//
//            public void onTabSelected(TabLayout.Tab tab)
//            {
//                if (tab.getPosition() != 2)
//                {
//                    tab.getIcon().setTintList(ColorStateList.valueOf(getResources().getColor(R.color.common_google_signin_btn_text_dark_focused)));
//                    setFragment(tab.getPosition());
//                }
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab)
//            {
//                if (tab.getPosition() != 2)
//                {
//                    tab.getIcon().setTintList(ColorStateList.valueOf(Color.parseColor("#789654")));
//                }
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        tabLayout.getTabAt(0).getIcon().setTintList(ColorStateList.valueOf(getResources().getColor(R.color.common_google_signin_btn_text_dark_focused)));
////        setFragment(0);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");


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
//        postList = (RecyclerView) findViewById(R.id.all_users_post);
//        postList.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
//        postList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


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

//        DisplayAllUserPosts();
    }

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


//    private void DisplayAllUserPosts()
//    {
//        options = new FirebaseRecyclerOptions.Builder<Posts>()
//                .setQuery(PostsRef,Posts.class).build();
//
//        adapter = new FirebaseRecyclerAdapter<Posts, PostsViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull Posts model)
//            {
//                holder.setProfileimage(getApplicationContext(),model.getProfileimage());
//                holder.setUsername(model.getUsername());
//                holder.setTime(model.getTime());
//                holder.setDate(model.getDate());
//                holder.setDescription(model.getDescription());
//                holder.setPostimage(getApplicationContext(),model.getPostimage());
//
//            }
//
//            @NonNull
//            @Override
//            public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//            {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_posts,parent,false);
//
//                return new PostsViewHolder(view);
//            }
//        };


  //  }

//    public static class PostsViewHolder extends RecyclerView.ViewHolder
//    {
//
//        View mView;
//
//        public PostsViewHolder(@NonNull View itemView)
//        {
//            super(itemView);
//            mView = itemView;
//        }
//        public void setDate(String date)
//        {
//            TextView date1 = (TextView)mView.findViewById(R.id.post_date);
//            date1.setText("  "+date);
//        }
//        public void setPostimage(Context ctx,String postimage)
//        {
//            ImageView UserPostImage = mView.findViewById(R.id.post_image);
//            Picasso.with(ctx).load(postimage).into(UserPostImage);
//        }
//        public void setProfileimage(Context ctx,String profileimage)
//        {
//            CircleImageView UserProfileImage = mView.findViewById(R.id.post_image);
//            Picasso.with(ctx).load(profileimage).into(UserProfileImage);
//        }
//        public void setUsername(String username)
//        {
//            TextView UserUserName = (TextView)mView.findViewById(R.id.post_date);
//            UserUserName.setText("  "+username);
//        }
//        public void setTime(String time)
//        {
//            TextView time1 = (TextView)mView.findViewById(R.id.post_date);
//            time1.setText("  "+time);
//        }
//        public void setDescription(String description)
//        {
//            TextView postdescription1 = mView.findViewById(R.id.postdescription);
//            postdescription1.setText(description);
//        }
//    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
//                            fragment = new HomeFragment();
                            openFragment(HomeFragment.newInstance("", ""));
//                            SendUserToHomeFragment();
                            Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.navigation_search:
                            openFragment(SearchFragment.newInstance("", ""));
                            Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.navigation_post:
                            SendUserToUserPostsActivity();
                            return true;
                        case R.id.navigation_notifications:
                            openFragment(NotificationFragment.newInstance("", ""));
                            Toast.makeText(MainActivity.this, "Notification", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.navigation_profile:
                            openFragment(ProfileFragment.newInstance("", ""));
                            Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
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
        {
//            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent nointernet = new  Intent(MainActivity.this, NoInternetActivity.class);
            nointernet.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(nointernet);
            finish();
//            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        /////////////

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

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
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
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.jblogo)
                .setTitle("JBUlla")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }



}