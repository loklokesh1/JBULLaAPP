package com.mahammadjabi.jbulla.BottomNavbarFragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahammadjabi.jbulla.AdapterPosts;
import com.mahammadjabi.jbulla.Posts;
import com.mahammadjabi.jbulla.R;
import com.mahammadjabi.jbulla.ShowStatus.StatusActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView all_users_post;
    private FloatingActionButton StatusFloatingButton;

    private SwipeRefreshLayout swipeRefreshLayout;

    private DatabaseReference PostsRef;

    List<Posts> posts;
    AdapterPosts adapterPosts;

    Handler handler;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        swipeRefreshLayout =view.findViewById(R.id.refreshlayout);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh()
//            {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run()
//                    {
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                },3000);
//
//                boolean connection = isNetworkAvailable();
//
//                if (!connection)
//                {
//                    Toast.makeText(getContext(), "\"Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
//                    Intent nointernet = new  Intent(getActivity(), NoInternetActivity.class);
//                    nointernet.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(nointernet);
//                    getActivity().finish();
//                }
//            }
//        });
//
//        swipeRefreshLayout.setColorSchemeColors(Color.BLUE,
//                Color.GREEN,Color.BLACK,Color.RED);



        all_users_post = (RecyclerView) view.findViewById(R.id.all_users_post);
        all_users_post.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        all_users_post.setLayoutManager(linearLayoutManager);



//        all_users_post.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

//        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
//        }
//      if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == posts.size()-1){}

        posts = new ArrayList<>();

        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        PostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final Posts data = ds.getValue(Posts.class);

//                    posts.add(data);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run()
                        {
                            posts.add(data);
                            adapterPosts.notifyDataSetChanged();
                            adapterPosts.showShimmer=false;
                        }
                    },500);

                }
                adapterPosts = new AdapterPosts(posts);
                all_users_post.setAdapter(adapterPosts);
                all_users_post.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
        StatusFloatingButton = view.findViewById(R.id.statusfloatingbutton);
        StatusFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallingStatusActivity();
            }
        });
    }

    private void CallingStatusActivity()
    {
        Intent callstatus = new Intent(getContext(), StatusActivity.class);
        startActivity(callstatus);
    }

//    private boolean isNetworkAvailable()
//    {
//        ConnectivityManager connectivityManager = (ConnectivityManager)this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
//        return networkinfo != null;
//
//    }


//    @Override
//    public void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//    }
}
//
//<!--<?xml version="1.0" encoding="utf-8"?>-->
//<!--<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"-->
//<!--    android:layout_width="match_parent"-->
//<!--    xmlns:app="http://schemas.android.com/apk/res-auto"-->
//<!--    android:layout_height="wrap_content">-->
//
//<!--    <LinearLayout-->
//<!--        android:layout_width="match_parent"-->
//<!--        android:layout_height="wrap_content"-->
//<!--        android:orientation="vertical"-->
//<!--        >-->
//
//<!--        <LinearLayout-->
//<!--            android:layout_width="match_parent"-->
//<!--            android:layout_height="wrap_content"-->
//<!--            android:layout_marginTop="10dp"-->
//<!--            android:layout_marginLeft="6dp"-->
//<!--            android:padding="5dp"-->
//<!--            android:orientation="horizontal">-->
//
//<!--            <de.hdodenhof.circleimageview.CircleImageView-->
//<!--                android:layout_width="60dp"-->
//<!--                android:layout_height="60dp"-->
//<!--                android:src="@drawable/profile1"-->
//<!--                android:id="@+id/post_profile_image"-->
//<!--                android:scaleType="centerCrop"-->
//<!--                android:layout_marginLeft="4dp"-->
//<!--                />-->
//<!--            <LinearLayout-->
//<!--                android:layout_width="match_parent"-->
//<!--                android:layout_height="wrap_content"-->
//<!--                android:orientation="vertical">-->
//<!--                <TextView-->
//<!--                    android:layout_width="match_parent"-->
//<!--                    android:layout_height="wrap_content"-->
//<!--                    android:text="UserName"-->
//<!--                    android:id="@+id/post_user_name"-->
//<!--                    android:layout_marginLeft="5dp"-->
//<!--                    android:layout_marginTop="8dp"-->
//<!--                    android:textAlignment="textStart"-->
//<!--                    android:textSize="16sp"-->
//<!--                    android:textStyle="bold"-->
//<!--                    android:textColor="@android:color/black"-->
//<!--                    />-->
//<!--                <LinearLayout-->
//<!--                    android:layout_width="match_parent"-->
//<!--                    android:layout_height="wrap_content"-->
//<!--                    android:orientation="horizontal"-->
//<!--                    android:layout_marginLeft="5dp"-->
//<!--                    android:padding="2dp">-->
//
//<!--                    <TextView-->
//<!--                        android:id="@+id/text2"-->
//<!--                        android:layout_width="wrap_content"-->
//<!--                        android:layout_height="wrap_content"-->
//<!--                        android:text="Post uploaded on "-->
//<!--                        android:textColor="@android:color/secondary_text_light_nodisable"-->
//<!--                        android:textSize="13sp"-->
//<!--                        android:textStyle="bold"-->
//<!--                        />-->
//<!--                    <TextView-->
//<!--                        android:id="@+id/post_date"-->
//<!--                        android:layout_width="wrap_content"-->
//<!--                        android:layout_height="wrap_content"-->
//<!--                        android:text="Date"-->
//<!--                        android:lines="1"-->
//<!--                        android:textColor="@android:color/darker_gray"-->
//<!--                        android:textSize="13sp"-->
//<!--                        />-->
//<!--                    <TextView-->
//<!--                        android:id="@+id/post_time"-->
//<!--                        android:layout_width="wrap_content"-->
//<!--                        android:layout_height="wrap_content"-->
//<!--                        android:text="Time"-->
//<!--                        android:lines="1"-->
//<!--                        android:textColor="@android:color/darker_gray"-->
//<!--                        android:textSize="13sp"-->
//<!--                        />-->
//<!--                </LinearLayout>-->
//
//<!--            </LinearLayout>-->
//<!--        </LinearLayout>-->
//<!--        <TextView-->
//<!--            android:layout_width="match_parent"-->
//<!--            android:layout_height="wrap_content"-->
//<!--            android:text="post description"-->
//<!--            android:layout_marginTop="2dp"-->
//<!--            android:id="@+id/postdescription"-->
//<!--            android:layout_marginLeft="6dp"-->
//<!--            android:layout_marginRight="6dp"-->
//<!--            android:padding="3dp"-->
//<!--            android:textSize="14sp"-->
//<!--            android:textColor="@android:color/black"-->
//<!--            />-->
//<!--        <ImageView-->
//<!--            android:layout_width="match_parent"-->
//<!--            android:layout_height="380dp"-->
//<!--            android:layout_marginTop="4dp"-->
//<!--            android:layout_margin="3dp"-->
//<!--            android:adjustViewBounds="true"-->
//<!--            android:scaleType="centerCrop"-->
//<!--            android:id="@+id/post_image"/>-->
//<!--    </LinearLayout>-->
//
//
//<!--</RelativeLayout>-->
