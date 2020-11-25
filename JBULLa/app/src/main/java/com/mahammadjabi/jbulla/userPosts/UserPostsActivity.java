package com.mahammadjabi.jbulla.userPosts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mahammadjabi.jbulla.MainActivity;
import com.mahammadjabi.jbulla.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserPostsActivity extends AppCompatActivity {

    private CircleImageView ProfileImage;
    private TextView UserName,Time,Date;
    private Button SharePostButton;
    private EditText PostDescription;
    private ProgressDialog loadingBar;
    private ImageView SelectPostImage;
    private ImageView SelectPostImage1;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, PostsRef;
    private StorageReference UserPostImageRef;

    private  Uri ImageUri;

    private String postdescription;

    private String currentUserID, saveCurrentTime, saveCurrentDate, postRandomName, downloadUrl;

    private static final int Gallery_Pick = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        UserPostImageRef = FirebaseStorage.getInstance().getReference();



        ProfileImage = (CircleImageView) findViewById(R.id.click_post_profile_image);
        UserName = (TextView) findViewById(R.id.click_post_user_name);
//        Time = (TextView) findViewById(R.id.click_post_time);
//        Date = (TextView) findViewById(R.id.click_post_date);


        loadingBar = new ProgressDialog(this);


        SelectPostImage = (ImageView) findViewById(R.id.click_post_image);
        SelectPostImage1 = (ImageView) findViewById(R.id.click_post_image1);
        PostDescription = (EditText) findViewById(R.id.click_post_description);
        SharePostButton = (Button) findViewById(R.id.click_share_post);


        SelectPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .setAspectRatio(1,1
//                        .start(UserPostsActivity.this,Gallery_Pick);
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Pick);

//                Intent galleryintent = new Intent();
//                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
//                galleryintent.setType("image/*");
////                galleryintent.putExtra("outputX",96);
////                galleryintent.putExtra("outputY",96);
////                galleryintent.putExtra("aspectX",1);
////                galleryintent.putExtra("aspectY",1);
////                galleryintent.putExtra("scale",true);
////                galleryintent.putExtra("return-data",true);
//                startActivityForResult(galleryintent,Gallery_Pick);


            }
        });

        SharePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                postdescription = PostDescription.getText().toString();

                if (ImageUri == null && TextUtils.isEmpty(postdescription))
                {
                    Toast.makeText(UserPostsActivity.this, "Please select Image or\nWrite something..!", Toast.LENGTH_SHORT).show();
                }
//                else if (ImageUri != null || postdescription != null)
//                {
//                    ShareUserPost();
//                }
                else
                {
                    ShareUserPost();
//                    Toast.makeText(UserPostsActivity.this, "Error while selecting your imput", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
                        Picasso.with(UserPostsActivity.this).load(image).placeholder(R.drawable.profile1).into(ProfileImage);
                    }
                    else
                    {
                        Toast.makeText(UserPostsActivity.this, "Please select post image first", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null)
        {
           ImageUri = data.getData();
           SelectPostImage1.setImageURI(ImageUri);
           SelectPostImage1.setVisibility(View.VISIBLE);
           SelectPostImage.setVisibility(View.GONE);
        }
    }

    private void ShareUserPost()
    {
        loadingBar.setMessage("Uploading post...");
        loadingBar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        loadingBar.show();
        if (ImageUri !=null) {
            StoringImageToFirebaseStorage();
        }
        if (postdescription != null)
            SaveingPostInformationToDatabase();
    }

    private void StoringImageToFirebaseStorage()
    {
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        StorageReference filepath = UserPostImageRef.child("Post Images").child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");
        filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri)
                    {

                        downloadUrl = uri.toString();
                        UsersRef.child("post").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {

                                if (task.isSuccessful())
                                {
//                                 Toast.makeText(UserPostsActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                                    SaveingPostInformationToDatabase();
                                }
                                else
                                {
                                    String massage = task.getException().getMessage();
                                    Toast.makeText(UserPostsActivity.this, "Error occured "+massage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private void SaveingPostInformationToDatabase()
    {
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot)
            {
                if (datasnapshot.exists())
                {
                    String UserUserName = datasnapshot.child("username").getValue().toString();
                    String UserProfile = datasnapshot.child("profileimage").getValue().toString();

                    HashMap postMap = new HashMap();
                    postMap.put("uid",currentUserID);
                    postMap.put("date",saveCurrentDate);
                    postMap.put("time",saveCurrentTime);
                    postMap.put("description",postdescription);
                    postMap.put("postimage",downloadUrl);
                    postMap.put("profileimage",UserProfile);
                    postMap.put("username",UserUserName);

                    PostsRef.child(currentUserID + postRandomName).updateChildren(postMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task)
                        {
                            if (task.isSuccessful())
                            {
                                SendUserToMainActivity();
                                loadingBar.dismiss();
                                Toast.makeText(UserPostsActivity.this, "New Post updated successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(UserPostsActivity.this, "Error Occured while updating your post", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void SendUserToMainActivity()
    {
        Intent mainintent = new Intent(UserPostsActivity.this, MainActivity.class);
        mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainintent);
        finish();
    }
}