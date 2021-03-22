package com.mahammadjabi.jbulla.BottomNavbarFragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahammadjabi.jbulla.Adapters.AdapterAskHelpUsers;
import com.mahammadjabi.jbulla.Models.AskHelpUsersModel;
import com.mahammadjabi.jbulla.R;

import java.util.ArrayList;
import java.util.List;

public class AskHelpFragment extends Fragment {



    private RecyclerView all_users_ask_help_local;

    private DatabaseReference AskHelpUserRef;

    List<AskHelpUsersModel> askhelp;
    AdapterAskHelpUsers adapterAskHelpUsers;

    private ProgressBar progressBarAsk;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AskHelpFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AskHelpFragment newInstance(String param1, String param2) {
        AskHelpFragment fragment = new AskHelpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_askhelp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        progressBarAsk = view.findViewById(R.id.progressbarrecycleask);
        Handler handler = new Handler ();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarAsk.setVisibility(View.GONE);
            }
        },1500);

        all_users_ask_help_local = (RecyclerView) view.findViewById(R.id.all_users_ask_help);
        all_users_ask_help_local.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        all_users_ask_help_local.setLayoutManager(new LinearLayoutManager(getContext()));


        askhelp = new ArrayList<>();

        AskHelpUserRef = FirebaseDatabase.getInstance().getReference().child("AskHelpPosts");


        AskHelpUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    AskHelpUsersModel data = ds.getValue(AskHelpUsersModel.class);

                    askhelp.add(data);

                }
                adapterAskHelpUsers = new AdapterAskHelpUsers(askhelp);
                all_users_ask_help_local.setAdapter(adapterAskHelpUsers);
                all_users_ask_help_local.invalidateItemDecorations();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
