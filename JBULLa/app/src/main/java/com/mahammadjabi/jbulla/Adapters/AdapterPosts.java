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

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mahammadjabi.jbulla.Models.PostsModel;
import com.mahammadjabi.jbulla.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPosts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LinearLayout EditPost,DeletePost,SharePost,ReportPost;
    private DatabaseReference ClickPostRef;
    private FirebaseAuth mAuth;
    private String Current_User_Id,databaseUserID,CurrentImageUrl,CurrentPostUrl;
    private AlphaAnimation buttonclick;

    List<PostsModel> postsList;

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
                .into(viewHolderClass.UserPostImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolderClass.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });

//        viewHolderClass.UserPostImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                final AppCompatActivity activity = (AppCompatActivity)view.getContext();
////                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
////                        new PostDetailsFragment(
////                                posts.getUsername(),
////                                posts.getProfileimage(),
////                                posts.getDate(),
////                                posts.getTime(),
////                                posts.getDescription(),
////                                posts.getPostimage()
////                        ))
////                        .addToBackStack(null).commit();
//
////                viewHolderClass.UserPostImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
////                viewHolderClass.UserPostImage.getLayoutParams().height=500;
////                viewHolderClass.UserPostImage.requestLayout();
//
////                viewHolderClass.UserPostImage.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
////                        View mView = activity.getLayoutInflater().inflate(R.layout.photoview_full_image,null );
////                        PhotoView photoView = mView.findViewById(R.id.imageviewfull);
////                        photoView.setImageResource(R.drawable.profile1);
////                        mBuilder.setView(mView);
////                        AlertDialog mDialog = mBuilder.create();
////                        mDialog.show();
////
////                    }
////                });
//            }
//        });

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
                final BottomSheetDialog bottomdialog = new BottomSheetDialog(activity);
                bottomdialog.setContentView(view);
                bottomdialog.setCanceledOnTouchOutside(true);
                bottomdialog.show();


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

                                    CurrentPostUrl = posts.getPostid();
                                    CurrentImageUrl  = posts.getPostimage();
                                    DeleteUserPost(CurrentImageUrl,activity,CurrentPostUrl);

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

    private void DeleteUserPost(final String currentImageUrl, final AppCompatActivity activity, final String currentPostUrl)
    {

        StorageReference picRef = FirebaseStorage.getInstance().getReferenceFromUrl(currentImageUrl);
        picRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Query fquery = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("postid").equalTo(currentPostUrl);
                        fquery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds: snapshot.getChildren())
                                {
                                    ds.getRef().removeValue();
                                }
                                Toast.makeText(activity, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                postsList.remove(currentImageUrl);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
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
//        ImageView UserPostImage;
//        TouchImageView UserPostImage;
        PhotoView UserPostImage;
        ImageView PopUpMenu;
        CircleImageView UserProfileImage;
        ProgressBar progressBar;


        public ViewHolderClass(@NonNull View itemView) {


            super(itemView);

            date1 = (TextView)itemView.findViewById(R.id.post_date);
            UserPostImage = (PhotoView) itemView.findViewById(R.id.post_image);
            UserProfileImage = (CircleImageView)itemView.findViewById(R.id.post_profile_image);
            UserUserName = (TextView)itemView.findViewById(R.id.post_user_name);
            time1 = (TextView)itemView.findViewById(R.id.post_time);
            postdescription1 =  (TextView)itemView.findViewById(R.id.post_description);
            progressBar = itemView.findViewById(R.id.progressbarhome);
            PopUpMenu = itemView.findViewById(R.id.popupmenu);

        }
    }
}