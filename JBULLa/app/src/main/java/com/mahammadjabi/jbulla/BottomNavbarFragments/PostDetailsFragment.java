package com.mahammadjabi.jbulla.BottomNavbarFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mahammadjabi.jbulla.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView date1;
    TextView UserUserName;
    TextView time1;
    TextView postdescription1;
    ImageView UserPostImage;
    CircleImageView UserProfileImage;


    private String mParam1;
    private String mParam2;

    public String time,date,postimage,username,description,profileimage;


    public PostDetailsFragment() {

    }
    public PostDetailsFragment(String username,String profileimage,String date,String time,String description,String postimage)
    {
        this.time=time;
        this.date=date;
        this.postimage=postimage;
        this.username=username;
        this.description=description;
        this.profileimage=profileimage;

    }

    public static PostDetailsFragment newInstance(String param1, String param2) {
        PostDetailsFragment fragment = new PostDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_details, container, false);

        date1 = (TextView)view.findViewById(R.id.details_post_date);
        UserPostImage = (ImageView)view.findViewById(R.id.details_post_image);
        UserProfileImage = (CircleImageView)view.findViewById(R.id.details_post_profile_image);
        UserUserName = (TextView)view.findViewById(R.id.details_post_user_name);
        time1 = (TextView)view.findViewById(R.id.details_post_time);
        postdescription1 =  (TextView)view.findViewById(R.id.details_post_description);


        date1.setText(date);
        time1.setText(time);
        UserUserName.setText(username);
        postdescription1.setText(description);

        Picasso.with(getContext()).load(postimage).into(UserPostImage);
        Picasso.with(getContext()).load(profileimage).into(UserProfileImage);

        return  view;


    }

    public void onBackPressed()
    {
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
                new HomeFragment())
                .addToBackStack(null).commit();
    }
}