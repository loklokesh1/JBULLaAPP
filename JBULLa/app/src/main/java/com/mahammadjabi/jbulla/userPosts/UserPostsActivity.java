package com.mahammadjabi.jbulla.userPosts;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
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
import com.mahammadjabi.jbulla.BottomNavbarFragments.HomeFragment;
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
    private TextView UserName;
    private Button SharePostButton;
    private EditText PostDescription,AskTime,AskAmount;
    private ProgressDialog loadingBar;
    private ImageView SelectPostImage;
    private ImageView SelectPostImage1;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, PostsRef,AskHelpPostsRef;
    private StorageReference UserPostImageRef;

    private  Uri ImageUri;

    private String postdescription,askamount,asktime;

    private String UserUserName,UserProfile;

    private String currentUserID, saveCurrentTime, saveCurrentDate, postRandomName, downloadUrl;

    private static final int Gallery_Pick = 1;

    private Button SharePostLocal,AskHelpLocal,AskHelpLocalButton;
    private TextView SharePostText,AskHelpText;
    private LinearLayout AmountTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        AskHelpPostsRef = FirebaseDatabase.getInstance().getReference().child("AskHelpPosts");
        UserPostImageRef = FirebaseStorage.getInstance().getReference();

        ProfileImage = (CircleImageView) findViewById(R.id.click_post_profile_image);
        UserName = (TextView) findViewById(R.id.click_post_user_name);

        loadingBar = new ProgressDialog(this);

        SelectPostImage = (ImageView) findViewById(R.id.click_post_image);
        SelectPostImage1 = (ImageView) findViewById(R.id.click_post_image1);
        PostDescription = (EditText) findViewById(R.id.click_post_description);
        SharePostButton = (Button) findViewById(R.id.click_share_post);

        SharePostLocal = findViewById(R.id.share_post_to_local);
        AskHelpLocal = findViewById(R.id.ask_help_to_near_people);
        AskHelpLocalButton = findViewById(R.id.click_ask_help);
        AskHelpText = findViewById(R.id.ask_help_to_people_text);
        SharePostText = findViewById(R.id.share_post_local_text);
        AmountTime = findViewById(R.id.amount_time);

        AskAmount = findViewById(R.id.amount);
        AskTime = findViewById(R.id.timetocomplete);




        SharePostLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                AmountTime.setVisibility(View.GONE);
                AskHelpLocalButton.setVisibility(View.GONE);
                AskHelpText.setVisibility(View.GONE);
                SharePostText.setVisibility(View.VISIBLE);
                PostDescription.setVisibility(View.VISIBLE);
                SelectPostImage.setVisibility(View.VISIBLE);
                SharePostButton.setVisibility(View.VISIBLE);
                AskHelpLocal.setBackgroundResource(R.drawable.text_logo);
                SharePostLocal.setBackgroundResource(R.drawable.btn);
                AskHelpLocal.setTextColor(getResources().getColor(R.color.blacknavcolar));
                SharePostLocal.setTextColor(getResources().getColor(R.color.white));


            }
        });

        AskHelpLocal.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v)
            {

                SharePostText.setVisibility(View.GONE);
                SelectPostImage.setVisibility(View.GONE);
                SharePostButton.setVisibility(View.GONE);
                SelectPostImage1.setVisibility(View.GONE);
                SelectPostImage1.setImageDrawable(null);
                AskHelpLocalButton.setVisibility(View.VISIBLE);
                AmountTime.setVisibility(View.VISIBLE);
                PostDescription.setVisibility(View.VISIBLE);
                AskHelpText.setVisibility(View.VISIBLE);
                AskHelpLocal.setBackgroundResource(R.drawable.btn);
                SharePostLocal.setBackgroundResource(R.drawable.text_logo);
                SharePostLocal.setTextColor(getResources().getColor(R.color.blacknavcolar));
                AskHelpLocal.setTextColor(getResources().getColor(R.color.white));

                AskHelpLocalButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        postdescription = PostDescription.getText().toString();
                        askamount = AskAmount.getText().toString();
                        asktime = AskTime.getText().toString();

                        if (TextUtils.isEmpty(postdescription))
                            Toast.makeText(UserPostsActivity.this, "Please write your problem", Toast.LENGTH_SHORT).show();
                        else if (TextUtils.isEmpty(askamount))
                            Toast.makeText(UserPostsActivity.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                        else if (TextUtils.isEmpty(asktime ))
                            Toast.makeText(UserPostsActivity.this, "Please enter time", Toast.LENGTH_SHORT).show();
                        else
                            AskUserPost();
                    }
                });

            }
        });

        SelectPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Pick);

            }
        });

        SharePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                postdescription = PostDescription.getText().toString();

                if (ImageUri == null && TextUtils.isEmpty(postdescription))
                    Toast.makeText(UserPostsActivity.this, "Please select Image or\nWrite something..!", Toast.LENGTH_SHORT).show();
                else if (ImageUri == null)
                    Toast.makeText(UserPostsActivity.this, "PLease select Image", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(postdescription))
                    Toast.makeText(UserPostsActivity.this, "Plaese write something", Toast.LENGTH_SHORT).show();
                else
                {
                    ShareUserPost();
                }
            }
        });

//        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if (dataSnapshot.exists())
//                {
//                    if(dataSnapshot.hasChild("username"))
//                    {
//                        String fullname = dataSnapshot.child("username").getValue().toString();
//                        UserName.setText(fullname);
//                    }
//                    if(dataSnapshot.hasChild("profileimage"))
//                    {
//                        String image = dataSnapshot.child("profileimage").getValue().toString();
//                        Picasso.with(UserPostsActivity.this).load(image).placeholder(R.drawable.profile1).into(ProfileImage);
//                    }
//                    else
//                    {
//                        Toast.makeText(UserPostsActivity.this, "Please select post image first", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {}
//        });
    }

    private void AskUserPost()
    {
        loadingBar.setMessage("Sharing post...");
        loadingBar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        loadingBar.show();

        SaveingAskHelpUserPostInformationToDatabase();

    }

    private void SaveingAskHelpUserPostInformationToDatabase()
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
                UserUserName = datasnapshot.child("username").getValue().toString();
                UserProfile = datasnapshot.child("profileimage").getValue().toString();

                HashMap postMap = new HashMap();
                postMap.put("uid",currentUserID);
                postMap.put("date",saveCurrentDate);
                postMap.put("time",saveCurrentTime);
                postMap.put("description",postdescription);
                postMap.put("profileimage",UserProfile);
                postMap.put("username",UserUserName);
                postMap.put("asktime",asktime);
                postMap.put("askamount",askamount);

                AskHelpPostsRef.child(currentUserID + postRandomName).updateChildren(postMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if (task.isSuccessful())
                        {
//                            SendUserToMainActivity();
//                            AppCompatActivity activity = (AppCompatActivity);
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                    new HomeFragment())
                                    .addToBackStack(null).commit();
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
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
           if (ImageUri != null) {
               SelectPostImage1.setVisibility(View.VISIBLE);
               SelectPostImage.setVisibility(View.GONE); 
           }
         }
    }

    private void ShareUserPost()
    {
        loadingBar.setMessage("Uploading post...");
        loadingBar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        loadingBar.show();
        if (ImageUri !=null && postdescription != null)
        {
            StoringImageToFirebaseStorage();
        }
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
//                                  Toast.makeText(UserPostsActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
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

        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot)
            {
                if (datasnapshot.exists())
                {
                    UserUserName = datasnapshot.child("username").getValue().toString();
                    UserProfile = datasnapshot.child("profileimage").getValue().toString();

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
//                              SendUserToMainActivity();

                                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                        new HomeFragment())
                                        .addToBackStack(null).commit();
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

    @Override
    protected void onStart()
    {
        super.onStart();

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
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}