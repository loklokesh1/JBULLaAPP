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
import com.like.LikeButton;
import com.mahammadjabi.jbulla.BottomNavbarFragments.EditPostFragment;
import com.mahammadjabi.jbulla.BottomNavbarFragments.SharePostFragment;
import com.mahammadjabi.jbulla.Models.PostsModel;
import com.mahammadjabi.jbulla.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPosts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LinearLayout EditPost, DeletePost, SharePost, ReportPost,SavePost;
    private DatabaseReference ClickPostRef;
    private FirebaseAuth mAuth;
    private String Current_User_Id, databaseUserID, CurrentImageUrl, CurrentPostUrl;
    private AlphaAnimation buttonclick;

    List<PostsModel> postsList;

    public AdapterPosts(List<PostsModel> postsList) {
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_posts_layout, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderClass viewHolderClass = (ViewHolderClass) holder;

        final PostsModel posts = postsList.get(position);

        if (posts.getDate() == null) {
            viewHolderClass.date1.setVisibility(View.GONE);
        } else {
            viewHolderClass.date1.setVisibility(View.VISIBLE);
            viewHolderClass.date1.setText(posts.getDate());
        }
        if (posts.getTime() == null) {
            viewHolderClass.time1.setVisibility(View.GONE);
        } else {
            viewHolderClass.time1.setVisibility(View.VISIBLE);
            viewHolderClass.time1.setText(posts.getTime());
        }
        if (posts.getDescription() == null) {
            viewHolderClass.postdescription1.setVisibility(View.GONE);
        } else {
            viewHolderClass.postdescription1.setVisibility(View.VISIBLE);
            viewHolderClass.postdescription1.setText(posts.getDescription());
        }
        if (posts.getUsername() == null) {
            viewHolderClass.UserUserName.setVisibility(View.GONE);
        } else {
            viewHolderClass.UserUserName.setVisibility(View.VISIBLE);
            viewHolderClass.UserUserName.setText(posts.getUsername());
        }
        if (posts.getPostimage() == null) {
            viewHolderClass.UserPostImage.setVisibility(View.GONE);
        } else {
            viewHolderClass.progressBarimage.setVisibility(View.VISIBLE);
            viewHolderClass.PostLikeLayout.setVisibility(View.VISIBLE);
            viewHolderClass.sharepostimage.setVisibility(View.GONE);
            viewHolderClass.shareprogressBar.setVisibility(View.GONE);
            viewHolderClass.UserPostImage.setVisibility(View.VISIBLE);
            viewHolderClass.shareuploadedtext.setVisibility(View.GONE);
            Picasso.with(viewHolderClass.itemView.getContext())
                    .load(posts.getPostimage())
                    .into(viewHolderClass.UserPostImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            viewHolderClass.progressBarimage.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
        if (posts.getProfileimage() == null) {
            viewHolderClass.UserProfileImage.setVisibility(View.GONE);
        } else {
            Picasso.with(viewHolderClass.itemView.getContext())
                    .load(posts.getProfileimage())
                    .into(viewHolderClass.UserProfileImage);

        }
        //share fields start
        if (posts.getSharetime() == null) {
            viewHolderClass.sharetime.setVisibility(View.GONE);
            viewHolderClass.ShareLikeLayout.setVisibility(View.GONE);
            viewHolderClass.sharedata.setVisibility(View.GONE);


        } else {
            viewHolderClass.sharetime.setVisibility(View.VISIBLE);
            viewHolderClass.sharetime.setText(posts.getSharetime());
            viewHolderClass.ShareLikeLayout.setVisibility(View.VISIBLE);
            viewHolderClass.shareuploadedtext.setVisibility(View.VISIBLE);

            viewHolderClass.sharedata.setVisibility(View.VISIBLE);
            viewHolderClass.sharedata.setText(posts.getSharedate());

        }
        if (posts.getSharepostdescription() == null) {
            viewHolderClass.sharepodtdescription.setVisibility(View.GONE);
        } else {
            viewHolderClass.sharepodtdescription.setVisibility(View.VISIBLE);
            viewHolderClass.sharepodtdescription.setText(posts.getSharepostdescription());

        }
        if (posts.getShareusername() == null) {

            viewHolderClass.shareusername.setVisibility(View.GONE);
            viewHolderClass.shareprogressBar.setVisibility(View.GONE);
        } else {
            viewHolderClass.shareusername.setText(posts.getShareusername());
            viewHolderClass.shareusername.setVisibility(View.VISIBLE);
            viewHolderClass.shareprogressBar.setVisibility(View.VISIBLE);
        }
        if (posts.getShareprofileimage() == null) {
            viewHolderClass.shareprofileimage.setVisibility(View.GONE);
        } else {
            viewHolderClass.shareprofileimage.setVisibility(View.VISIBLE);
            Picasso.with(viewHolderClass.itemView.getContext())
                    .load(posts.getShareprofileimage())
                    .into(viewHolderClass.shareprofileimage);
        }
        if (posts.getSharepostimage() == null) {
            viewHolderClass.sharepostimage.setVisibility(View.GONE);
        } else {
            viewHolderClass.shareprogressBar.setVisibility(View.VISIBLE);
            viewHolderClass.sharepostimage.setVisibility(View.VISIBLE);
            viewHolderClass.UserPostImage.setVisibility(View.GONE);
            viewHolderClass.progressBarimage.setVisibility(View.GONE);
            viewHolderClass.PostLikeLayout.setVisibility(View.GONE);
            Picasso.with(viewHolderClass.itemView.getContext())
                    .load(posts.getSharepostimage())
                    .into(viewHolderClass.sharepostimage, new Callback() {
                        @Override
                        public void onSuccess() {
                            viewHolderClass.shareprogressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }

//        if(posts.getSharepostid() != null)
//        {
//            viewHolderClass.PopUpMenu.setVisibility(View.GONE);
//        }
        // share fields end

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

        //like button code start
//        viewHolderClass.LikeThumbButton.setOnLikeListener(new OnLikeListener() {
//            @Override
//            public void liked(final LikeButton likeButton) {
//                viewHolderClass.LikeDisplayText.setText("1");
//                final String postId = posts.getPostid();
//                final DatabaseReference LikesRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child("likes");
//                LikesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        long numLikes = 0;
//                        if (snapshot.exists()) {
//                            numLikes = snapshot.getValue(Long.class);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//            }

//            @Override
//            public void unLiked(LikeButton likeButton)
//            {
//                viewHolderClass.LikeDisplayText.setText("0");
//
//            }
//        });
//
//        String postid = posts.getPostid();
//        String currentuserid = posts.getUid();
//        displayNumberOfLikes(postid,currentuserid);
        //like button code end

        //start popup to show options to the edit, delete, share, report

        viewHolderClass.PopUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                            Toast.makeText(activity, "hpopopopoopooopo", Toast.LENGTH_SHORT).show();
                final View view = ((FragmentActivity) activity).getLayoutInflater().inflate(R.layout.bottom_sheet, null);
                EditPost = view.findViewById(R.id.editpost);
                DeletePost = view.findViewById(R.id.deletepost);
                SharePost = view.findViewById(R.id.sharepost);
                ReportPost = view.findViewById(R.id.reportpost);
                SavePost = view.findViewById(R.id.savepost);
                final BottomSheetDialog bottomdialog = new BottomSheetDialog(activity);
                bottomdialog.setContentView(view);
                bottomdialog.setCanceledOnTouchOutside(true);
                bottomdialog.setDismissWithAnimation(true);
                bottomdialog.show();

                mAuth = FirebaseAuth.getInstance();
                Current_User_Id = mAuth.getCurrentUser().getUid();
                databaseUserID = posts.getUid();
                ClickPostRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(databaseUserID);
                ClickPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                      databaseUserID = posts.getUid();

                        if (databaseUserID.equals(Current_User_Id)) {

                            EditPost.setVisibility(View.VISIBLE);
                            DeletePost.setVisibility(View.VISIBLE);

                            EditPost.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                            new EditPostFragment(
                                                    posts.getUsername(),
                                                    posts.getProfileimage(),
                                                    posts.getDate(),
                                                    posts.getTime(),
                                                    posts.getDescription(),
                                                    posts.getPostimage(),
                                                    posts.getPostid(),

                                                    posts.getShareusername(),
                                                    posts.getShareprofileimage(),
                                                    posts.getSharedate(),
                                                    posts.getSharetime(),
                                                    posts.getSharepostdescription(),
                                                    posts.getSharepostimage(),
                                                    posts.getSharepostid()
                                            ))
                                            .addToBackStack(null).commit();

                                    bottomdialog.dismiss();
                                }
                            });
                            DeletePost.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    CurrentPostUrl = posts.getPostid();
                                    CurrentImageUrl = posts.getPostimage();
                                    DeleteUserPost(CurrentImageUrl, activity, CurrentPostUrl, bottomdialog);
//                                  Toast.makeText(activity, "delete", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                if (posts.getSharepostid() == null) {
                    SharePost.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(activity, "share", Toast.LENGTH_SHORT).show();
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                    new SharePostFragment(
                                            posts.getUsername(),
                                            posts.getProfileimage(),
                                            posts.getDate(),
                                            posts.getTime(),
                                            posts.getDescription(),
                                            posts.getPostimage(),
                                            posts.getPostid()
                                    ))
                                    .addToBackStack(null).commit();
                            bottomdialog.dismiss();
                        }
                    });
                }
                else
                {
                    SharePost.setVisibility(View.GONE);
                }

                ReportPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity, "report", Toast.LENGTH_SHORT).show();
                        bottomdialog.dismiss();
                    }
                });
                SavePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity, "Post saved", Toast.LENGTH_SHORT).show();
                        bottomdialog.dismiss();
                    }
                });
            }
        });
        //ended popup memu
        //like buttom start code

        //like button code end
    }

    private void displayNumberOfLikes(String postId, String currentuserid)
    {
        DatabaseReference LikesRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
        LikesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    long numOfLikes = 0;
                    if (snapshot.hasChild("likes"))
                    {
                        numOfLikes = snapshot.child("likes").getValue(Long.class);
                    }

//                    LikeThumbButton
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void DeleteUserPost(final String currentImageUrl, final AppCompatActivity activity,
                                final String currentPostUrl, final BottomSheetDialog bottomdialog) {

        StorageReference picRef = FirebaseStorage.getInstance().getReferenceFromUrl(currentImageUrl);
        picRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Query fquery = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("postid").equalTo(currentPostUrl);
                        fquery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    ds.getRef().removeValue();
                                }
                                Toast.makeText(activity, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                postsList.remove(currentImageUrl);
                                bottomdialog.dismiss();
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

    public class ViewHolderClass extends RecyclerView.ViewHolder {

        TextView date1;
        TextView UserUserName;
        TextView time1;
        TextView postdescription1;
        PhotoView UserPostImage;
        ImageView PopUpMenu;
        CircleImageView UserProfileImage;
        ProgressBar progressBarimage, shareprogressBar;

        TextView sharedata, sharetime, shareusername, sharepodtdescription;
        PhotoView sharepostimage;
        CircleImageView shareprofileimage;

        TextView shareuploadedtext, postuploadedtext;

        LinearLayout PostLikeLayout;
        LinearLayout ShareLikeLayout;



        TextView LikeDisplayText;
        final LikeButton LikeThumbButton;

        public ViewHolderClass(@NonNull View itemView) {


            super(itemView);

            UserProfileImage = (CircleImageView) itemView.findViewById(R.id.post_profile_image);
            UserUserName = (TextView) itemView.findViewById(R.id.post_user_name);
            date1 = (TextView) itemView.findViewById(R.id.post_date);
            time1 = (TextView) itemView.findViewById(R.id.post_time);
            PopUpMenu = itemView.findViewById(R.id.popupmenu);
            postdescription1 = (TextView) itemView.findViewById(R.id.post_description);

            shareprofileimage = itemView.findViewById(R.id.share_post_profile_image);
            shareusername = itemView.findViewById(R.id.share_post_user_name);
            sharedata = itemView.findViewById(R.id.share_post_date);
            sharetime = itemView.findViewById(R.id.share_post_time);
            sharepodtdescription = itemView.findViewById(R.id.share_post_description);

            sharepostimage = itemView.findViewById(R.id.share_post_image);
            shareprogressBar = itemView.findViewById(R.id.shareprogressbarhome);

            UserPostImage = (PhotoView) itemView.findViewById(R.id.post_image);

            progressBarimage = itemView.findViewById(R.id.progressbarimage);

            shareuploadedtext = itemView.findViewById(R.id.shareuploaded);
            postuploadedtext = itemView.findViewById(R.id.postuploaded);

            ShareLikeLayout = itemView.findViewById(R.id.sharelikelayout);

            PostLikeLayout = itemView.findViewById(R.id.normalpostlikelayout);


            LikeDisplayText = itemView.findViewById(R.id.share_display_no_of_likes1);

            LikeThumbButton = itemView.findViewById(R.id.share_like_button);



        }
    }

}