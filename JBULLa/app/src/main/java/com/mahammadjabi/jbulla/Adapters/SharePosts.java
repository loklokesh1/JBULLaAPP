//package com.mahammadjabi.jbulla.Adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AlphaAnimation;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentActivity;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.github.chrisbanes.photoview.PhotoView;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.mahammadjabi.jbulla.Models.SharePostsModel;
//import com.mahammadjabi.jbulla.R;
//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class SharePosts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private LinearLayout EditPost,DeletePost,SharePost,ReportPost;
//    private DatabaseReference ClickPostRef;
//    private FirebaseAuth mAuth;
//    private String Current_User_Id,databaseUserID,CurrentImageUrl,CurrentPostUrl;
//    private AlphaAnimation buttonclick;
//
//    List<SharePostsModel> sharepostsList;
//
//    public SharePosts(List<SharePostsModel> sharepostsList) {
//        this.sharepostsList = sharepostsList;
//
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//    {
//
////      progressBar.setVisibility(View.VISIBLE);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users_share_post,parent,false);
//        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
//        return viewHolderClass;
//
//    }
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position)
//    {
//        final ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
//
//        final SharePostsModel shareposts = sharepostsList.get(position);
//
//        viewHolderClass.normaldata.setText(shareposts.getDate());
//        viewHolderClass.normaltime.setText(shareposts.getTime());
//        viewHolderClass.normalpostdescription.setText(shareposts.getDescription());
//        viewHolderClass.normalusername.setText(shareposts.getUsername());
//
//
//        viewHolderClass.sharedata.setText(shareposts.getSharedate());
//        viewHolderClass.sharetime.setText(shareposts.getSharetime());
//        viewHolderClass.sharepodtdescription.setText(shareposts.getSharepostdescription());
//        viewHolderClass.shareusername.setText(shareposts.getShareusername());
//
//        Picasso.with(viewHolderClass.itemView.getContext())
//                .load(shareposts.getProfileimage())
//                .into(viewHolderClass.normalprofileimage);
//
//        Picasso.with(viewHolderClass.itemView.getContext())
//                .load(shareposts.getShareprofileimage())
//                .into(viewHolderClass.shareprofileimage);
//
//        Picasso.with(viewHolderClass.itemView.getContext())
//                .load(shareposts.getSharepostimage())
//                .into(viewHolderClass.sharepostimage, new Callback() {
//                    @Override
//                    public void onSuccess() {
////                        viewHolderClass.progressBar.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });
//
////        viewHolderClass.UserPostImage.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view)
////            {
////                final AppCompatActivity activity = (AppCompatActivity)view.getContext();
//////                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
//////                        new PostDetailsFragment(
//////                                posts.getUsername(),
//////                                posts.getProfileimage(),
//////                                posts.getDate(),
//////                                posts.getTime(),
//////                                posts.getDescription(),
//////                                posts.getPostimage()
//////                        ))
//////                        .addToBackStack(null).commit();
////
//////                viewHolderClass.UserPostImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//////                viewHolderClass.UserPostImage.getLayoutParams().height=500;
//////                viewHolderClass.UserPostImage.requestLayout();
////
//////                viewHolderClass.UserPostImage.setOnClickListener(new View.OnClickListener() {
//////                    @Override
//////                    public void onClick(View v) {
//////                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
//////                        View mView = activity.getLayoutInflater().inflate(R.layout.photoview_full_image,null );
//////                        PhotoView photoView = mView.findViewById(R.id.imageviewfull);
//////                        photoView.setImageResource(R.drawable.profile1);
//////                        mBuilder.setView(mView);
//////                        AlertDialog mDialog = mBuilder.create();
//////                        mDialog.show();
//////
//////                    }
//////                });
////            }
////        });
//
//
//        //start popup to show options to the edit, delete, share, report
//        viewHolderClass.normalpopupmenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                final AppCompatActivity activity = (AppCompatActivity)v.getContext();
////                            Toast.makeText(activity, "hpopopopoopooopo", Toast.LENGTH_SHORT).show();
//                final View view = ((FragmentActivity)activity).getLayoutInflater().inflate(R.layout.bottom_sheet,null);
//                EditPost = view.findViewById(R.id.editpost);
//                DeletePost = view.findViewById(R.id.deletepost);
//                SharePost = view.findViewById(R.id.sharepost);
//                ReportPost = view.findViewById(R.id.reportpost);
//                final BottomSheetDialog bottomdialog = new BottomSheetDialog(activity);
//                bottomdialog.setContentView(view);
//                bottomdialog.setCanceledOnTouchOutside(true);
//                bottomdialog.setDismissWithAnimation(true);
//                bottomdialog.show();
//
//                mAuth = FirebaseAuth.getInstance();
//                Current_User_Id = mAuth.getCurrentUser().getUid();
//                databaseUserID = shareposts.getUid();
//                ClickPostRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(databaseUserID);
//                ClickPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//                    {
////                      databaseUserID = posts.getUid();
//
//                        if (databaseUserID.equals(Current_User_Id))
//                        {
//
//                            EditPost.setVisibility(View.VISIBLE);
//                            DeletePost.setVisibility(View.VISIBLE);
//
//                            EditPost.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Toast.makeText(activity, "edit", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            DeletePost.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v)
//                                {
//
//                                    CurrentPostUrl = shareposts.getPostid();
//                                    CurrentImageUrl  = shareposts.getSharepostimage();
//                                    DeleteUserPost(CurrentImageUrl,activity,CurrentPostUrl,bottomdialog);
////                                  Toast.makeText(activity, "delete", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error)
//                    {
//                    }
//                });
//
////                SharePost.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Toast.makeText(activity, "share", Toast.LENGTH_SHORT).show();
////                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
////                                new SharePostFragment(
////                                        posts.getUsername(),
////                                        posts.getProfileimage(),
////                                        posts.getDate(),
////                                        posts.getTime(),
////                                        posts.getDescription(),
////                                        posts.getPostimage(),
////                                        posts.getPostid()
////                                ))
////                                .addToBackStack(null).commit();
////                        bottomdialog.dismiss();
////                    }
////                });
//
//                ReportPost.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(activity, "report", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//        //ended popup memu
//    }
//
//    private void DeleteUserPost(final String currentImageUrl, final AppCompatActivity activity,
//                                final String currentPostUrl, final BottomSheetDialog bottomdialog)
//    {
//
//        StorageReference picRef = FirebaseStorage.getInstance().getReferenceFromUrl(currentImageUrl);
//        picRef.delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid)
//                    {
//                        Query fquery = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("postid").equalTo(currentPostUrl);
//                        fquery.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                for (DataSnapshot ds: snapshot.getChildren())
//                                {
//                                    ds.getRef().removeValue();
//                                }
//                                Toast.makeText(activity, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
//                                notifyDataSetChanged();
//                                sharepostsList.remove(currentImageUrl);
//                                bottomdialog.dismiss();
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//        ClickPostRef.removeValue();
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return sharepostsList.size();
//    }
//
//    public class ViewHolderClass extends RecyclerView.ViewHolder
//    {
//
//        TextView normalusername,normaldata,normaltime,normalpostdescription;
//        ImageView normalpopupmenu;
//        CircleImageView normalprofileimage;
//        TextView sharedata,sharetime,shareusername,sharepodtdescription;
//        PhotoView sharepostimage;
//        CircleImageView shareprofileimage;
//
//
//
//        public ViewHolderClass(@NonNull View itemView) {
//
//
//            super(itemView);
//
//            normaldata = itemView.findViewById(R.id.normalpost_date);
//            normaltime = itemView.findViewById(R.id.normalpost_time);
//            normalpopupmenu = itemView.findViewById(R.id.normalpopupmenu);
//            normalpostdescription = itemView.findViewById(R.id.normalpost_description1);
//            normalusername = itemView.findViewById(R.id.normal_post_user_name);
//            normalprofileimage = itemView.findViewById(R.id.normal_post_profile_image);
//
//            sharedata =itemView.findViewById(R.id.share_post_date);
//            sharetime =itemView.findViewById(R.id.share_post_time);
//            shareusername =itemView.findViewById(R.id.share_post_user_name);
//            shareprofileimage =itemView.findViewById(R.id.share_post_profile_image);
//            sharepodtdescription =itemView.findViewById(R.id.share_post_description);
//            sharepostimage = itemView.findViewById(R.id.share_post_image);
//
//
//
//        }
//    }
//}