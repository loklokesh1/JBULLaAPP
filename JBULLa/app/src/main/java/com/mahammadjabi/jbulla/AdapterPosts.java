package com.mahammadjabi.jbulla;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.core.Context;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPosts extends RecyclerView.Adapter {

    List<Posts> postsList;

    boolean showShimmer = true;
    Context context;



    public AdapterPosts(List<Posts> postsList) {
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_posts_layout,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;

        Posts posts = postsList.get(position);
//
//        Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
//                .setBaseColor(Color.parseColor("#F3F3F#"))
//                .setBaseAlpha(1)
//                .setHighlightColor(Color.parseColor("#E7E7E7"))
//                .setDropoff(50)
//                .build();
//
//        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
//
//        shimmerDrawable.setShimmer(shimmer);

        viewHolderClass.date1.setText(posts.getDate());
        viewHolderClass.time1.setText(posts.getTime());
        viewHolderClass.postdescription1.setText(posts.getDescription());
        viewHolderClass.UserUserName.setText(posts.getUsername());
        Picasso.with(viewHolderClass.itemView.getContext())
                .load(posts.getProfileimage())
                .into(viewHolderClass.UserProfileImage);
        Picasso.with(viewHolderClass.itemView.getContext())
                .load(posts.getPostimage())
                .into(viewHolderClass.UserPostImage);
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder
    {
//        String uid,time,date,postimage,username,description,profileimage;

        TextView date1;
        TextView UserUserName;
        TextView time1;
        TextView postdescription1;
        ImageView UserPostImage;
        CircleImageView UserProfileImage;

        public ViewHolderClass(@NonNull View itemView) {


            super(itemView);


             date1 = (TextView)itemView.findViewById(R.id.post_date);
             UserPostImage = (ImageView)itemView.findViewById(R.id.post_image);
             UserProfileImage = (CircleImageView)itemView.findViewById(R.id.post_profile_image);
             UserUserName = (TextView)itemView.findViewById(R.id.post_user_name);
             time1 = (TextView)itemView.findViewById(R.id.post_time);
             postdescription1 =  (TextView)itemView.findViewById(R.id.post_description);
        }
    }
}
