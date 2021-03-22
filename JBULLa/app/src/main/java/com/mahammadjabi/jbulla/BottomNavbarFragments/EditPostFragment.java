package com.mahammadjabi.jbulla.BottomNavbarFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mahammadjabi.jbulla.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditPostFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView EditPostDate,PostDate;
    TextView EditUsername,PostUserName;
    TextView EditPostTime,PostTime;
    TextView Postdescription;
    TextView Editdescription,sharetext;
    ImageView PostImage;
    CircleImageView EditUserProfileImage,PostSahreUserprofileImage;

    ProgressBar EditprogressBar;

    LinearLayout Shareuserinfo;

    public String time, date, postimage, username, description, profileimage, postid,
            sharedate, sharetime, shareusername, sharepostdescription, sharepostid,
            sharepostimage, shareprofileimage;


    public EditPostFragment(String username, String profileimage, String date, String time,
                            String description, String postimage, String postid,
                            String shareusername, String shareprofileimage, String sharedate,
                            String sharetime, String sharepostdescription, String sharepostimage,
                            String sharepostid)
    {
        this.username = username;
        this.profileimage = profileimage;
        this.time = time;
        this.date = date;
        this.description = description;
        this.postimage = postimage;
        this.postid = postid;

        this.shareusername = shareusername;
        this.shareprofileimage = shareprofileimage;
        this.sharedate = sharedate;
        this.sharetime = sharetime;
        this.sharepostdescription = sharepostdescription;
        this.sharepostimage = sharepostimage;
        this.sharepostid = sharepostid;

    }


    public EditPostFragment() {
        // Required empty public constructor
    }

    public static EditPostFragment newInstance(String param1, String param2) {
        EditPostFragment fragment = new EditPostFragment();
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
        return inflater.inflate(R.layout.all_users_edit_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditprogressBar = view.findViewById(R.id.edit_progressbarhome11);
        sharetext =view.findViewById(R.id.share_post_text1);
        Shareuserinfo = view.findViewById(R.id.sharelayoutuserinfo);

        EditPostDate = view.findViewById(R.id.edit_post_date);
        EditPostTime = view.findViewById(R.id.edit_post_time);
        EditUsername = view.findViewById(R.id.edit_post_user_name);
        Editdescription = view.findViewById(R.id.edit_post_description1);
        EditUserProfileImage = view.findViewById(R.id.edit_post_profile_image);


        EditPostDate.setText(date);
        EditPostTime.setText(time);
        EditUsername.setText(username);
        Editdescription.setText(description,TextView.BufferType.EDITABLE);

        Picasso.with(getContext()).load(profileimage).into(EditUserProfileImage);

        PostDate = view.findViewById(R.id.edit_share_post_date);
        PostTime = view.findViewById(R.id.edit_share_post_time);
        Postdescription = view.findViewById(R.id.edit_share_post_description);
        PostUserName = view.findViewById(R.id.edit_share_post_user_name);

        PostSahreUserprofileImage = view.findViewById(R.id.edit_share_post_profile_image);
        PostImage = view.findViewById(R.id.edit_share_post_image);

        PostDate.setText(sharedate);
        PostTime.setText(sharetime);
        Postdescription.setText(sharepostdescription);
        PostUserName.setText(shareusername);

        Picasso.with(getContext()).load(shareprofileimage).into(PostSahreUserprofileImage);

        if(sharedate == null)
        {
            Shareuserinfo.setVisibility(View.GONE);
            Picasso.with(getContext()).load(postimage).into(PostImage, new Callback() {
                @Override
                public void onSuccess()
                {
                    EditprogressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });
        }
        else
        {
            Picasso.with(getContext()).load(sharepostimage).into(PostImage, new Callback() {
                @Override
                public void onSuccess()
                {
                    EditprogressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });
            sharetext.setVisibility(View.VISIBLE);

        }



    }
}
