//package com.mahammadjabi.jbulla.BottomNavbarFragments;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.mahammadjabi.jbulla.Adapters.SharePosts;
//import com.mahammadjabi.jbulla.Models.SharePostsModel;
//import com.mahammadjabi.jbulla.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Share_recyclerview extends Fragment {
//
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;
//
//    private RecyclerView all_users_share_post;
//
//    private DatabaseReference SharePostsRef;
//
//    List<SharePostsModel> shareposts;
//    SharePosts adapterSharePosts;
//
//
//    public Share_recyclerview() {
//    }
//
//
//    public static Share_recyclerview newInstance(String param1, String param2) {
//        Share_recyclerview fragment = new Share_recyclerview();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_share_recyclerview, container, false);
//    }
//    @Override
//    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState)
//    {
//        super.onViewCreated(view, savedInstanceState);
//
//        all_users_share_post = view.findViewById(R.id.all_sharepost_users_post);
//        all_users_share_post.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
//        linearLayoutManager1.setReverseLayout(true);
//        linearLayoutManager1.setStackFromEnd(true);
//        all_users_share_post.setLayoutManager(new LinearLayoutManager(getContext()));
//
//
//
//        shareposts = new ArrayList<>();
//        SharePostsRef = FirebaseDatabase.getInstance().getReference().child("SharePostUserData");
//
//        SharePostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    SharePostsModel data = ds.getValue(SharePostsModel.class);
//
//                    shareposts.add(data);
////                            adapterPosts.notifyDataSetChanged();
////                            adapterPosts.showShimmer=false;
//                }
//                adapterSharePosts = new SharePosts(shareposts);
//                all_users_share_post.setAdapter(adapterSharePosts);
//                all_users_share_post.invalidateItemDecorations();
//                adapterSharePosts.notifyDataSetChanged();
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error)
//            {}
//        });
//    }
//}