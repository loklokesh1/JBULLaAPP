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
import com.mahammadjabi.jbulla.BottomNavbarFragments.SharePostFragment;
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

//      progressBar.setVisibility(View.VISIBLE);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_posts_layout,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position)
    {
        final ViewHolderClass viewHolderClass = (ViewHolderClass)holder;

        final PostsModel posts = postsList.get(position);

        if(posts.getDate()== null)
        {
            viewHolderClass.date1.setVisibility(View.GONE);
        }
        else
        {
            viewHolderClass.date1.setText(posts.getDate());
        }
        if (posts.getTime()== null)
        {
            viewHolderClass.time1.setVisibility(View.GONE);
        }
        else
        {
            viewHolderClass.time1.setText(posts.getTime());
        }
        if (posts.getDescription() == null)
        {
            viewHolderClass.postdescription1.setVisibility(View.GONE);
        }
        else
        {
            viewHolderClass.postdescription1.setText(posts.getDescription());
        }
        if (posts.getUsername()== null)
        {
            viewHolderClass.UserUserName.setVisibility(View.GONE);
        }
        else
        {
            viewHolderClass.UserUserName.setVisibility(View.VISIBLE);
            viewHolderClass.UserUserName.setText(posts.getUsername());
        }
        if (posts.getPostimage() == null)
        {
            viewHolderClass.UserPostImage.setVisibility(View.GONE);
        }
        else
        {
            viewHolderClass.progressBarimage.setVisibility(View.VISIBLE);
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
        if (posts.getProfileimage()== null)
        {
            viewHolderClass.UserProfileImage.setVisibility(View.GONE);
        }
        else
        {
            Picasso.with(viewHolderClass.itemView.getContext())
                    .load(posts.getProfileimage())
                    .into(viewHolderClass.UserProfileImage);

        }
        if (posts.getSharedate() == null)
        {
            viewHolderClass.sharedata.setVisibility(View.GONE);
        }
        else
        {
            viewHolderClass.sharedata.setVisibility(View.VISIBLE);
            viewHolderClass.sharedata.setText(posts.getSharedate());
        }

        if (posts.getSharetime() == null)
        {
            viewHolderClass.sharetime.setVisibility(View.GONE);
        }
        else
        {
            viewHolderClass.sharetime.setVisibility(View.VISIBLE);
            viewHolderClass.shareuploadedtext.setVisibility(View.VISIBLE);
            viewHolderClass.sharetime.setText(posts.getSharetime());
        }
        if (posts.getSharepostdescription() == null)
        {
            viewHolderClass.sharepodtdescription.setVisibility(View.GONE);
        }
        else
        {
            viewHolderClass.sharepodtdescription.setVisibility(View.VISIBLE);
            viewHolderClass.sharepodtdescription.setText(posts.getSharepostdescription());

        }
        if (posts.getShareusername() == null)
        {

            viewHolderClass.shareusername.setVisibility(View.GONE);
            viewHolderClass.shareprogressBar.setVisibility(View.GONE);
        }
        else
        {
            viewHolderClass.shareusername.setText(posts.getShareusername());
            viewHolderClass.shareusername.setVisibility(View.VISIBLE);
            viewHolderClass.shareprogressBar.setVisibility(View.VISIBLE);
        }
        if (posts.getShareprofileimage()== null)
        {
            viewHolderClass.shareprofileimage.setVisibility(View.GONE);
        }
        else
        {
            Picasso.with(viewHolderClass.itemView.getContext())
                    .load(posts.getShareprofileimage())
                    .into(viewHolderClass.shareprofileimage);
        }
        if (posts.getSharepostimage()== null)
        {
            viewHolderClass.sharepostimage.setVisibility(View.GONE);
        }
        else
        {
            viewHolderClass.shareprogressBar.setVisibility(View.VISIBLE);
            viewHolderClass.sharepostimage.setVisibility(View.VISIBLE);
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


        //start popup to show options to the edit, delete, share, report
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
                bottomdialog.setDismissWithAnimation(true);
                bottomdialog.show();

                mAuth = FirebaseAuth.getInstance();
                Current_User_Id = mAuth.getCurrentUser().getUid();
                databaseUserID = posts.getUid();
                ClickPostRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(databaseUserID);
                ClickPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
//                      databaseUserID = posts.getUid();

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
                                    DeleteUserPost(CurrentImageUrl,activity,CurrentPostUrl,bottomdialog);
//                                  Toast.makeText(activity, "delete", Toast.LENGTH_SHORT).show();
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

                ReportPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity, "report", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //ended popup memu
    }

    private void DeleteUserPost(final String currentImageUrl, final AppCompatActivity activity,
                                final String currentPostUrl, final BottomSheetDialog bottomdialog)
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

    public class ViewHolderClass extends RecyclerView.ViewHolder
    {

        TextView date1;
        TextView UserUserName;
        TextView time1;
        TextView postdescription1;
        PhotoView UserPostImage;
        ImageView PopUpMenu;
        CircleImageView UserProfileImage;
        ProgressBar progressBarimage,shareprogressBar;

        TextView sharedata,sharetime,shareusername,sharepodtdescription;
        PhotoView sharepostimage;
        CircleImageView shareprofileimage;

        TextView shareuploadedtext,postuploadedtext;



        public ViewHolderClass(@NonNull View itemView) {


            super(itemView);

            date1 = (TextView)itemView.findViewById(R.id.post_date);
            UserPostImage = (PhotoView) itemView.findViewById(R.id.post_image);
            UserProfileImage = (CircleImageView)itemView.findViewById(R.id.post_profile_image);
            UserUserName = (TextView)itemView.findViewById(R.id.post_user_name);
            time1 = (TextView)itemView.findViewById(R.id.post_time);
            postdescription1 =  (TextView)itemView.findViewById(R.id.post_description);
            progressBarimage = itemView.findViewById(R.id.progressbarimage);
            PopUpMenu = itemView.findViewById(R.id.popupmenu);

            sharedata =itemView.findViewById(R.id.share_post_date);
            sharetime =itemView.findViewById(R.id.share_post_time);
            shareusername =itemView.findViewById(R.id.share_post_user_name);
            shareprofileimage =itemView.findViewById(R.id.share_post_profile_image);
            sharepodtdescription =itemView.findViewById(R.id.share_post_description);
            sharepostimage = itemView.findViewById(R.id.share_post_image);
            shareprogressBar = itemView.findViewById(R.id.shareprogressbarhome);

            shareuploadedtext = itemView.findViewById(R.id.shareuploaded);
            postuploadedtext = itemView.findViewById(R.id.postuploaded);



        }
    }

}
/*

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto">



    <!--    <com.facebook.shimmer.ShimmerFrameLayout-->
<!--        android:layout_width="match_parent"-->
<!--        android:layout_height="wrap_content"-->
<!--        app:shimmer_colored="true"-->
<!--        app:shimmer_highlight_color="#9C9DA0"-->
<!--        android:id="@+id/shimmer">-->

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:layout_marginTop="10dp"
        >

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="1dp"
            android:padding="5dp"
            android:orientation="horizontal">

<!--            <com.facebook.shimmer.ShimmerFrameLayout-->
<!--                android:id="@+id/shimmer1"-->
<!--                app:shimmer_auto_start="true"-->
<!--                app:shimmer_colored="true"-->
<!--                app:shimmer_duration="900"-->
<!--                android:layout_gravity="center"-->
<!--                app:shimmer_highlight_color="#9C9DA0"-->
<!--                android:layout_width="wrap_content"-->
<!--                android:layout_height="wrap_content"-->
<!--                >-->

                <de.hdodenhof.circleimageview.CircleImageView
                    android:id="@+id/post_profile_image"
                    android:layout_width="55dp"
                    android:padding="4dp"
                    android:layout_height="55dp"
                    android:src="@drawable/profile1"
                    android:scaleType="centerCrop"
                    android:background="@drawable/circle_background_profile"
                    android:layout_marginStart="4dp"/>
            <!--            </com.facebook.shimmer.ShimmerFrameLayout>-->

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical">

<!--                <com.facebook.shimmer.ShimmerFrameLayout-->
<!--                    android:id="@+id/shimmer2"-->
<!--                    app:shimmer_auto_start="true"-->
<!--                    app:shimmer_colored="true"-->
<!--                    app:shimmer_duration="900"-->
<!--                    android:layout_gravity="center"-->
<!--                    android:layout_marginLeft="2dp"-->
<!--                    app:shimmer_highlight_color="#9C9DA0"-->
<!--                    android:layout_width="match_parent"-->
<!--                    android:layout_height="wrap_content"-->
<!--                    >-->
                <TextView
                    android:id="@+id/post_user_name"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="user name"
                    android:fontFamily="@font/salsa"
                    android:layout_marginStart="7dp"
                    android:layout_marginTop="8dp"
                    android:textAlignment="textStart"
                    android:textSize="16sp"
                    android:textStyle="bold"
                    android:textColor="@android:color/black"/>

<!--                </com.facebook.shimmer.ShimmerFrameLayout>-->

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal"
                    android:layout_marginStart="5dp"
                    android:padding="2dp">
<!--                    <com.facebook.shimmer.ShimmerFrameLayout-->
<!--                        android:id="@+id/shimmer3"-->
<!--                        app:shimmer_duration="900"-->
<!--                        android:layout_gravity="center"-->
<!--                        app:shimmer_auto_start="true"-->
<!--                        app:shimmer_colored="true"-->
<!--                        android:layout_marginLeft="2dp"-->
<!--                        app:shimmer_highlight_color="#9C9DA0"-->
<!--                        android:layout_width="wrap_content"-->
<!--                        android:layout_height="wrap_content"-->
<!--                        >-->
                    <TextView
                        android:id="@+id/text"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Uploaded on"
                        android:fontFamily="@font/salsa"
                        android:layout_gravity="center_vertical"
                        android:textColor="@android:color/darker_gray"
                        android:textSize="13sp"
                        android:textStyle="bold" />
<!--                    </com.facebook.shimmer.ShimmerFrameLayout>-->
<!--                    <com.facebook.shimmer.ShimmerFrameLayout-->
<!--                        android:id="@+id/shimmer4"-->
<!--                        app:shimmer_duration="900"-->
<!--                        android:layout_gravity="center"-->
<!--                        app:shimmer_auto_start="true"-->
<!--                        app:shimmer_colored="true"-->
<!--                        android:layout_marginLeft="2dp"-->
<!--                        app:shimmer_highlight_color="#9C9DA0"-->
<!--                        android:layout_width="wrap_content"-->
<!--                        android:layout_height="wrap_content"-->
<!--                        >-->

                    <TextView
                        android:id="@+id/post_date"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Date"
                        android:fontFamily="@font/salsa"
                        android:layout_gravity="center_vertical"
                        android:padding="4dp"
                        android:lines="1"
                        android:textColor="@android:color/black"
                        android:textSize="14sp" />
<!--                    </com.facebook.shimmer.ShimmerFrameLayout>-->
<!--                    <com.facebook.shimmer.ShimmerFrameLayout-->
<!--                        android:id="@+id/shimmer5"-->
<!--                        app:shimmer_duration="900"-->
<!--                        android:layout_gravity="center"-->
<!--                        app:shimmer_auto_start="true"-->
<!--                        app:shimmer_colored="true"-->
<!--                        android:layout_marginLeft="2dp"-->
<!--                        app:shimmer_highlight_color="#9C9DA0"-->
<!--                        android:layout_width="wrap_content"-->
<!--                        android:layout_height="wrap_content"-->
<!--                        >-->
                    <TextView
                        android:id="@+id/post_time"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Time"
                        android:fontFamily="@font/salsa"
                        android:lines="1"
                        android:layout_gravity="center_vertical"
                        android:padding="4dp"
                        android:textColor="@android:color/black"
                        android:textSize="14sp" />
<!--                    </com.facebook.shimmer.ShimmerFrameLayout>-->
                    <ImageView
                        android:id="@+id/popupmenu"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:src="@drawable/menudetails"
                        android:padding="2dp"
                        />
                </LinearLayout>


            </LinearLayout>

        </LinearLayout>

<!--        <com.facebook.shimmer.ShimmerFrameLayout-->
<!--            android:id="@+id/shimmer6"-->
<!--            app:shimmer_duration="900"-->
<!--            android:layout_gravity="center"-->
<!--            app:shimmer_auto_start="true"-->
<!--            app:shimmer_colored="true"-->
<!--            android:layout_marginLeft="2dp"-->
<!--            app:shimmer_highlight_color="#9C9DA0"-->
<!--            android:layout_width="match_parent"-->
<!--            android:layout_height="wrap_content"-->
<!--            >-->
        <TextView
            android:id="@+id/post_description"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Post description.."
            android:fontFamily="@font/salsa"
            android:layout_marginTop="6dp"
            android:visibility="visible"
            android:layout_marginLeft="8dp"
            android:layout_marginRight="4dp"
            android:layout_marginBottom="4dp"
            android:padding="3dp"
            android:textColor="@android:color/black"
            android:textSize="14sp" />
<!--        </com.facebook.shimmer.ShimmerFrameLayout>-->

<!--        <com.facebook.shimmer.ShimmerFrameLayout-->
<!--            android:id="@+id/shimmer7"-->
<!--            app:shimmer_duration="900"-->
<!--            android:layout_gravity="center"-->
<!--            app:shimmer_auto_start="true"-->
<!--            app:shimmer_colored="true"-->
<!--            android:layout_marginTop="2dp"-->
<!--            android:layout_marginLeft="2dp"-->
<!--            android:layout_marginBottom="3dp"-->
<!--            app:shimmer_highlight_color="#9C9DA0"-->
<!--            android:layout_width="match_parent"-->
<!--            android:layout_height="wrap_content"-->
<!--            >-->


        <!--changed ImageView To Touchimage view  -->
<!--        <com.ortiz.touchview.TouchImageView-->
<!--            android:id="@+id/post_image"-->
<!--            android:layout_width="match_parent"-->
<!--            android:layout_height="350dp"-->
<!--            android:visibility="visible"-->
<!--            android:scaleType="centerCrop"-->
<!--            android:scrollbars="vertical|horizontal"-->
<!--             />-->
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">
        <com.github.chrisbanes.photoview.PhotoView
            android:id="@+id/post_image"
            android:layout_width="match_parent"
            android:layout_height="400dp"
            android:visibility="visible"
            android:scaleType="centerCrop"
            tools:ignore="MissingPrefix"
            />

        <ProgressBar
            android:id="@+id/progressbarhome"
            android:layout_width="60dp"
            android:layout_height="60dp"
            android:visibility="visible"
            android:layout_centerInParent="true"
             />
<!--        </com.facebook.shimmer.ShimmerFrameLayout>-->

        </RelativeLayout>
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:layout_marginLeft="10dp"
            android:layout_marginRight="10dp">
<!--            <com.facebook.shimmer.ShimmerFrameLayout-->
<!--                android:id="@+id/shimmer8"-->
<!--                app:shimmer_duration="900"-->
<!--                android:layout_gravity="center"-->
<!--                app:shimmer_auto_start="true"-->
<!--                app:shimmer_colored="true"-->
<!--                android:layout_marginLeft="2dp"-->
<!--                app:shimmer_highlight_color="#9C9DA0"-->
<!--                android:layout_width="wrap_content"-->
<!--                android:layout_height="wrap_content"-->
<!--                >-->
            <ImageView
                android:id="@+id/like_button"
                android:layout_width="30dp"
                android:layout_height="35dp"
                android:backgroundTint="@android:color/white"
                android:layout_gravity="center_vertical"
                android:src="@drawable/likeicon"/>
<!--            </com.facebook.shimmer.ShimmerFrameLayout>-->
<!--            <com.facebook.shimmer.ShimmerFrameLayout-->
<!--                android:id="@+id/shimmer9"-->
<!--                app:shimmer_duration="900"-->
<!--                android:layout_gravity="center"-->
<!--                app:shimmer_auto_start="true"-->
<!--                app:shimmer_colored="true"-->
<!--                android:layout_marginLeft="2dp"-->
<!--                app:shimmer_highlight_color="#9C9DA0"-->
<!--                android:layout_width="wrap_content"-->
<!--                android:layout_height="wrap_content"-->
<!--                >-->
            <TextView
                android:id="@+id/display_no_of_likes"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="143541 likes"
                android:fontFamily="@font/salsa"
                android:layout_gravity="center_vertical"
                android:padding="15dp"
                android:textColor="@android:color/black"
                android:textSize="14dp"
                android:textStyle="bold"/>
<!--            </com.facebook.shimmer.ShimmerFrameLayout>-->
<!--            <com.facebook.shimmer.ShimmerFrameLayout-->
<!--                android:id="@+id/shimmer10"-->
<!--                app:shimmer_duration="900"-->
<!--                android:layout_gravity="center"-->
<!--                app:shimmer_auto_start="true"-->
<!--                app:shimmer_colored="true"-->
<!--                android:layout_marginLeft="2dp"-->
<!--                app:shimmer_highlight_color="#9C9DA0"-->
<!--                android:layout_width="wrap_content"-->
<!--                android:layout_height="wrap_content"-->
<!--                >-->
            <ImageButton
                android:id="@+id/comment_button"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="150dp"
                android:layout_gravity="center_vertical"
                android:backgroundTint="@android:color/white"
                android:baselineAlignBottom="true"
                android:src="@drawable/commentsicon"/>
<!--            </com.facebook.shimmer.ShimmerFrameLayout>-->



        </LinearLayout>
<!--        <TextView-->
<!--            android:layout_width="match_parent"-->
<!--            android:layout_height="wrap_content"-->
<!--            android:layout_margin="0dp"-->
<!--            android:padding="0dp"-->
<!--            android:elevation="4dp"-->
<!--            android:text="____________________________________________________________"-->
<!--            />-->
    </LinearLayout>

<!--    </com.facebook.shimmer.ShimmerFrameLayout>-->

</RelativeLayout>

 */