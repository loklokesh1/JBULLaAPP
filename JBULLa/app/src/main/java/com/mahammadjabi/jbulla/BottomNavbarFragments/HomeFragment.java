package com.mahammadjabi.jbulla.BottomNavbarFragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
import com.mahammadjabi.jbulla.Adapters.AdapterPosts;
import com.mahammadjabi.jbulla.Models.PostsModel;
import com.mahammadjabi.jbulla.R;
import com.mahammadjabi.jbulla.ShowStatus.StatusActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView all_users_post,all_users_share_post;
    private FloatingActionButton StatusFloatingButton;



    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    private DatabaseReference PostsRef;

    List<PostsModel> posts;
    AdapterPosts adapterPosts;

    private ImageView popupmenu;

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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
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
        //        });
//
//        swipeRefreshLayout.setColorSchemeColors(Color.BLUE,
//                Color.GREEN,Color.BLACK,Color.RED);



        progressBar = view.findViewById(R.id.progressbarrecycle);
        Handler handler = new Handler ();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        },5000);


        all_users_post = (RecyclerView) view.findViewById(R.id.all_users_post);
        all_users_post.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        all_users_post.setLayoutManager(new LinearLayoutManager(getContext()));


        all_users_share_post = view.findViewById(R.id.all_users_post);
        all_users_share_post.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);
        all_users_share_post.setLayoutManager(new LinearLayoutManager(getContext()));


        posts = new ArrayList<>();
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");


//        popupmenu= view.findViewById(R.id.popupmenu);
//
//        popupmenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Pop Up menu", Toast.LENGTH_SHORT).show();
//            }
//        });



//        all_users_post.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

//        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
//        }
//      if (linearLayoutManager.findLastCompletelyVisibleItemPosition()   == posts.size()-1){}


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

    @Override
    public void onStart() {
        super.onStart();


        PostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PostsModel data = ds.getValue(PostsModel.class);

                    posts.add(data);
//                            adapterPosts.notifyDataSetChanged();
//                            adapterPosts.showShimmer=false;
                }
                adapterPosts = new AdapterPosts(posts);
                all_users_post.setAdapter(adapterPosts);
                all_users_post.invalidateItemDecorations();
                adapterPosts.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {}
        });

    }
}