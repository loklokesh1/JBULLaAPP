package com.mahammadjabi.jbulla.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.core.Context;
import com.mahammadjabi.jbulla.Models.PostsModel;
import com.mahammadjabi.jbulla.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPosts extends RecyclerView.Adapter {

    List<PostsModel> postsList;

    public static ProgressBar progressBar;
    public static ShimmerFrameLayout shimmerFrameLayout;

    public boolean showShimmer = true;
    int noOfItemShow = 1;
    Context context;

    public AdapterPosts(List<PostsModel> postsList) {
        this.postsList = postsList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

//        progressBar.setVisibility(View.VISIBLE);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_posts_layout,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;

        if (showShimmer)
        {
//            ((ViewHolderClass) holder).layout1.startShimmer();
//            ((ViewHolderClass) holder).layout2.startShimmer();
//            ((ViewHolderClass) holder).layout3.startShimmer();
//            ((ViewHolderClass) holder).layout4.startShimmer();
//            ((ViewHolderClass) holder).layout5.startShimmer();
//            ((ViewHolderClass) holder).layout6.startShimmer();
//            ((ViewHolderClass) holder).layout7.startShimmer();
//            ((ViewHolderClass) holder).layout8.startShimmer();
//            ((ViewHolderClass) holder).layout9.startShimmer();
//            ((ViewHolderClass) holder).layout10.startShimmer();
        }
        else
        {
            PostsModel posts = postsList.get(position);

//            ((ViewHolderClass) holder).layout1.stopShimmer();
//            ((ViewHolderClass) holder).layout1.setShimmer(null);
//            ((ViewHolderClass) holder).layout2.stopShimmer();
//            ((ViewHolderClass) holder).layout2.setShimmer(null);
//            ((ViewHolderClass) holder).layout3.stopShimmer();
//            ((ViewHolderClass) holder).layout3.setShimmer(null);
//            ((ViewHolderClass) holder).layout4.stopShimmer();
//            ((ViewHolderClass) holder).layout4.setShimmer(null);
//            ((ViewHolderClass) holder).layout5.stopShimmer();
//            ((ViewHolderClass) holder).layout5.setShimmer(null);
//            ((ViewHolderClass) holder).layout6.stopShimmer();
//            ((ViewHolderClass) holder).layout6.setShimmer(null);
//            ((ViewHolderClass) holder).layout7.stopShimmer();
//            ((ViewHolderClass) holder).layout7.setShimmer(null);
//            ((ViewHolderClass) holder).layout8.stopShimmer();
//            ((ViewHolderClass) holder).layout8.setShimmer(null);
//            ((ViewHolderClass) holder).layout9.stopShimmer();
//            ((ViewHolderClass) holder).layout9.setShimmer(null);
//            ((ViewHolderClass) holder).layout10.stopShimmer();
//            ((ViewHolderClass) holder).layout10.setShimmer(null);

            viewHolderClass.date1.setText(posts.getDate());
            viewHolderClass.date1.setBackground(null);
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


    }


    @Override
    public int getItemCount() {
        return showShimmer ? noOfItemShow:postsList.size();
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
//        ShimmerFrameLayout layout1,layout2,layout3,layout4,layout5,layout6,layout7,layout8,layout9,layout10;

        public ViewHolderClass(@NonNull View itemView) {


            super(itemView);


             date1 = (TextView)itemView.findViewById(R.id.post_date);
             UserPostImage = (ImageView)itemView.findViewById(R.id.post_image);
             UserProfileImage = (CircleImageView)itemView.findViewById(R.id.post_profile_image);
             UserUserName = (TextView)itemView.findViewById(R.id.post_user_name);
             time1 = (TextView)itemView.findViewById(R.id.post_time);
             postdescription1 =  (TextView)itemView.findViewById(R.id.post_description);

//             layout1 = itemView.findViewById(R.id.shimmer1);
//             layout2 = itemView.findViewById(R.id.shimmer2);
//             layout3 = itemView.findViewById(R.id.shimmer3);
//             layout4 = itemView.findViewById(R.id.shimmer4);
//             layout5 = itemView.findViewById(R.id.shimmer5);
//             layout6 = itemView.findViewById(R.id.shimmer6);
//             layout7 = itemView.findViewById(R.id.shimmer7);
//             layout8 = itemView.findViewById(R.id.shimmer8);
//             layout9 = itemView.findViewById(R.id.shimmer9);
//             layout10 = itemView.findViewById(R.id.shimmer10);

             progressBar = itemView.findViewById(R.id.progressbarhome);
//            shimmerFrameLayout = itemView.findViewById(R.id.shimmer);

        }

    }

}
