package com.mahammadjabi.jbulla.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahammadjabi.jbulla.BottomNavbarFragments.PostDetailsFragment;
import com.mahammadjabi.jbulla.Models.PostsModel;
import com.mahammadjabi.jbulla.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPosts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LinearLayout EditPost,DeletePost,SharePost,ReportPost;
    private DatabaseReference ClickPostRef;
    private FirebaseAuth mAuth;
    private String Current_User_Id,databaseUserID;
    private AlphaAnimation buttonclick;

    List<PostsModel> postsList;


    public static ProgressBar progressBar;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position)
    {
        final ViewHolderClass viewHolderClass = (ViewHolderClass)holder;

        final PostsModel posts = postsList.get(position);

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

        viewHolderClass.UserPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new PostDetailsFragment(
                                posts.getUsername(),
                                posts.getProfileimage(),
                                posts.getDate(),
                                posts.getTime(),
                                posts.getDescription(),
                                posts.getPostimage()
                        ))
                        .addToBackStack(null).commit();
            }
        });
        viewHolderClass.PopUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final AppCompatActivity activity = (AppCompatActivity)v.getContext();
//                            Toast.makeText(activity, "hpopopopoopooopo", Toast.LENGTH_SHORT).show();
                final View view = ((FragmentActivity)activity).getLayoutInflater().inflate(R.layout.bottom_sheet,null);
                EditPost = view.findViewById(R.id.editpost);
                DeletePost = view.findViewById(R.id.deletepost);
                SharePost = view.findViewById(R.id.sharepost);
                ReportPost = view.findViewById(R.id.reportpost);
                BottomSheetDialog dialog = new BottomSheetDialog(activity);
                dialog.setContentView(view);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                buttonclick = new AlphaAnimation(1F,0.8F);
                mAuth = FirebaseAuth.getInstance();
                Current_User_Id = mAuth.getCurrentUser().getUid();
                databaseUserID = posts.getUid();
                ClickPostRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(databaseUserID);
                ClickPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
//                                    databaseUserID = posts.getUid();

                        if (databaseUserID.equals(Current_User_Id))
                        {

                            EditPost.setVisibility(View.VISIBLE);
                            DeletePost.setVisibility(View.VISIBLE);

                            EditPost.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(activity, "edit", Toast.LENGTH_SHORT).show();
                                }
                            });
                            DeletePost.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v)
                                {
                                    DeleteUserPost();
//                                                Toast.makeText(activity, "delete", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                    }
                });

                SharePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity, "share", Toast.LENGTH_SHORT).show();
                    }
                });

                ReportPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity, "report", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void DeleteUserPost()
    {
        ClickPostRef.removeValue();
    }


    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder
    {

        TextView date1;
        TextView UserUserName;
        TextView time1;
        TextView postdescription1;
        ImageView UserPostImage;
        ImageView PopUpMenu;
        CircleImageView UserProfileImage;


        public ViewHolderClass(@NonNull View itemView) {


            super(itemView);

            date1 = (TextView)itemView.findViewById(R.id.post_date);
            UserPostImage = (ImageView)itemView.findViewById(R.id.post_image);
            UserProfileImage = (CircleImageView)itemView.findViewById(R.id.post_profile_image);
            UserUserName = (TextView)itemView.findViewById(R.id.post_user_name);
            time1 = (TextView)itemView.findViewById(R.id.post_time);
            postdescription1 =  (TextView)itemView.findViewById(R.id.post_description);
            progressBar = itemView.findViewById(R.id.progressbarhome);
            PopUpMenu = itemView.findViewById(R.id.popupmenu);

        }
    }
}