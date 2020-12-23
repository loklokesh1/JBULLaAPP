package com.mahammadjabi.jbulla.BottomNavbarFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mahammadjabi.jbulla.MainActivity;
import com.mahammadjabi.jbulla.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SharePostFragment  extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView datesharepost;
    TextView UserUserNamesharepost,UserName;
    TextView timesharepost;
    TextView postdescriptionsharepost;
    EditText PostShareDes;
    ImageView UserPostImagesharepost;
    CircleImageView UserProfileImagesharepost,UserprofileImage;

    ProgressBar progressBarsharepost;

    Button Sharepost;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, SharePostRef;
    private StorageReference UserPostImageRef;
    private String currentUserID;


    private String saveCurrentDate,saveCurrentTime,postRandomName;
    private String UserUserNameS,UserProfileS,postshatedes, SharePostId;
    private String mParam1;
    private String mParam2;

    private ProgressDialog loadingBarShare;

    public String time,date,postimage,username,description,profileimage,postid;

    public SharePostFragment() {
    }

    public SharePostFragment(String username,String profileimage,String date,String time,String description,String postimage,String postid)
    {
        this.username=username;
        this.profileimage=profileimage;
        this.date=date;
        this.time=time;
        this.description=description;
        this.postimage=postimage;
        this.postid = postid;
        SharePostId =  postid;

    }


    public static SharePostFragment newInstance(String param1, String param2) {
        SharePostFragment fragment = new SharePostFragment();
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
        return inflater.inflate(R.layout.fragment_share_post, container, false);
//
//        mAuth = FirebaseAuth.getInstance();
//        currentUserID = mAuth.getCurrentUser().getUid();
//        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
//        SharePostRef = FirebaseDatabase.getInstance().getReference().child("SharePostUserData");
//        UserPostImageRef = FirebaseStorage.getInstance().getReference();
//
//        progressBarsharepost= view.findViewById(R.id.progressbarsharepost);
//        datesharepost = (TextView)view.findViewById(R.id.post_datesharepost);
//        UserPostImagesharepost = (ImageView)view.findViewById(R.id.post_imagesharepost);
//        UserProfileImagesharepost = (CircleImageView)view.findViewById(R.id.post_profile_imagesharepost);
//        UserUserNamesharepost = (TextView)view.findViewById(R.id.post_user_namesharepost);
//        timesharepost = (TextView)view.findViewById(R.id.post_timesharepost);
//        postdescriptionsharepost =  (TextView)view.findViewById(R.id.post_descriptionsharepost);
//
//        UserName = view.findViewById(R.id.post_user_nameshare);
//        UserprofileImage = view.findViewById(R.id.post_profile_imageshare);
//
//        PostShareDes = view.findViewById(R.id.post_descriptionsh);
//
//        Sharepost = view.findViewById(R.id.user_share_post);
//
//
//        datesharepost.setText(date);
//        timesharepost.setText(time);
//        UserUserNamesharepost.setText(username);
//        postdescriptionsharepost.setText(description);
//
//        Picasso.with(getContext()).load(postimage).into(UserPostImagesharepost, new Callback() {
//            @Override
//            public void onSuccess()
//            {
//                progressBarsharepost.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
//        Picasso.with(getContext()).load(profileimage).into(UserProfileImagesharepost);
//
//        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if (dataSnapshot.exists())
//                {
//                    if(dataSnapshot.hasChild("username"))
//                    {
//                        String fullname = dataSnapshot.child("username").getValue().toString();
//                        UserName.setText(fullname);
//                    }
//                    if(dataSnapshot.hasChild("profileimage"))
//                    {
//                        String image = dataSnapshot.child("profileimage").getValue().toString();
//                        Picasso.with(getContext()).load(image).placeholder(R.drawable.profile1).into(UserprofileImage);
//                    }
//                    else
//                    {
//                        Toast.makeText(getContext(), "Please select post image first", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {}
//        });
//
//        Sharepost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                loadingBarShare.setMessage("Sharing post...");
//                loadingBarShare.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//                loadingBarShare.setCancelable(false);
//                loadingBarShare.show();
//
//                postshatedes =PostShareDes.getText().toString();
//
//                SaveingShareUserPostInformationToDatabase();
//
//            }
//        });

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
//        SharePostRef = FirebaseDatabase.getInstance().getReference().child("SharePostUserData");
        SharePostRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        UserPostImageRef = FirebaseStorage.getInstance().getReference();

        progressBarsharepost= view.findViewById(R.id.progressbarsharepost);
        datesharepost = (TextView)view.findViewById(R.id.post_datesharepost);
        UserPostImagesharepost = (ImageView)view.findViewById(R.id.post_imagesharepost);
        UserProfileImagesharepost = (CircleImageView)view.findViewById(R.id.post_profile_imagesharepost);
        UserUserNamesharepost = (TextView)view.findViewById(R.id.post_user_namesharepost);
        timesharepost = (TextView)view.findViewById(R.id.post_timesharepost);
        postdescriptionsharepost =  (TextView)view.findViewById(R.id.post_descriptionsharepost);

        UserName = view.findViewById(R.id.post_user_nameshare);
        UserprofileImage = view.findViewById(R.id.post_profile_imageshare);

        PostShareDes = view.findViewById(R.id.post_descriptionsh);

        Sharepost = view.findViewById(R.id.user_share_post);


        datesharepost.setText(date);
        timesharepost.setText(time);
        UserUserNamesharepost.setText(username);
        postdescriptionsharepost.setText(description);

        loadingBarShare = new ProgressDialog(getContext());

        Picasso.with(getContext()).load(postimage).into(UserPostImagesharepost, new Callback() {
            @Override
            public void onSuccess()
            {
                progressBarsharepost.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        Picasso.with(getContext()).load(profileimage).into(UserProfileImagesharepost);

        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if(dataSnapshot.hasChild("username"))
                    {
                        String fullname = dataSnapshot.child("username").getValue().toString();
                        UserName.setText(fullname);
                    }
                    if(dataSnapshot.hasChild("profileimage"))
                    {
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.with(getContext()).load(image).placeholder(R.drawable.profile1).into(UserprofileImage);
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Please select post image first", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        Sharepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loadingBarShare.setMessage("Sharing post...");
                loadingBarShare.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                loadingBarShare.show();
                loadingBarShare.setCancelable(false);

                postshatedes =PostShareDes.getText().toString();

                SaveingShareUserPostInformationToDatabase();

            }
        });



    }

    private void SaveingShareUserPostInformationToDatabase()
    {
        Calendar calFordDate = Calendar.getInstance();
        final SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot)
            {
                UserUserNameS = datasnapshot.child("username").getValue().toString();
                UserProfileS = datasnapshot.child("profileimage").getValue().toString();


                HashMap shareMap = new HashMap();
                shareMap.put("uid",currentUserID);
                shareMap.put("date",saveCurrentDate);
                shareMap.put("time",saveCurrentTime);
                shareMap.put("description",postshatedes);
                shareMap.put("profileimage",UserProfileS);
                shareMap.put("username",UserUserNameS);
                shareMap.put("postid",SharePostId);

                shareMap.put("sharedata",date);
                shareMap.put("sharetime",time);
                shareMap.put("shareusername",username);
                shareMap.put("shareprofileimage",profileimage);
                shareMap.put("sharepostdescription",description);
                shareMap.put("sharepostimage",postimage);


                shareMap.put("sharepostid",currentUserID + postRandomName);



                SharePostRef.child(currentUserID + postRandomName).updateChildren(shareMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if (task.isSuccessful())
                        {
                            SendUserToMainActivity();
//                            AppCompatActivity activity = (AppCompatActivity);
                            Toast.makeText(getContext(), "Post shared successfully", Toast.LENGTH_SHORT).show();
//                            getSupportFragmentManager().beginTransaction().replace(R.id.container,
//                                    new SearchFragment())
//                                    .addToBackStack(null).commit();
//                            loadingBar.dismiss();
                        }
                        else
                        {
                            loadingBarShare.dismiss();
                            Toast.makeText(getContext(), "Error Occured while updating your post", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void SendUserToMainActivity()
    {
        Intent mainintent = new Intent(getContext(), MainActivity.class);
        mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainintent);
        getActivity().finish();
    }

    public void onBackPressed()
    {
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
                new HomeFragment())
                .addToBackStack(null).commit();
    }
}