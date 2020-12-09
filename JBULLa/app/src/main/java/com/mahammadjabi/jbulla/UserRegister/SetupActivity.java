package com.mahammadjabi.jbulla.UserRegister;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private CircleImageView ProfileImage;
    private ImageView cameraIcon;
    private EditText UserName,CountryName,FullName;
    private Button SaveProfileInformationButton;
    private ProgressDialog progressDialogImage;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private StorageReference UserProfileImageRef;
    private ProgressBar progressBar;
    String currentUserID ;
    final static int Gallery_Pick = 1;
//    static int photo = 10;
    static boolean photo = true;
    static boolean checkusername = true;

    public static final Pattern VALID_USERNAME_REGEX =
            Pattern.compile("^[a-zA-Z0-9_.]{7,20}$", Pattern.CASE_INSENSITIVE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("ProfileImage");

        cameraIcon = findViewById(R.id.cameraicon);
        ProfileImage = (CircleImageView) findViewById(R.id.logoimg);
        UserName = (EditText) findViewById(R.id.userName);
        FullName = (EditText) findViewById(R.id.fullName);
        CountryName = (EditText) findViewById(R.id.country);
        SaveProfileInformationButton = (Button) findViewById(R.id.setup_saveprofile);
        progressBar = findViewById(R.id.progress);
        progressDialogImage = new ProgressDialog(this);

        SaveProfileInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SaveAccountSetupInformation();

            }
        });

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(SetupActivity.this);

            }
        });

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.hasChild("profileimage"))
                    {
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.with(SetupActivity.this).load(image).placeholder(R.drawable.profile1).into(ProfileImage);
                    }
                    else
                    {
                        Toast.makeText(SetupActivity.this, "Please select profile image first", Toast.LENGTH_SHORT).show();
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
            Uri ImageUri = data.getData();
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK)
            {
                Uri resultUrl = result.getUri();

                progressDialogImage.setTitle("Please wait...");
                progressDialogImage.setMessage("Loading your image...");
                progressDialogImage.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialogImage.show();

                StorageReference filePath = UserProfileImageRef.child(currentUserID + ".jpg");

                filePath.putFile(resultUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot)
                    {
                        final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                        firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri)
                            {
                                final String downloadUrl = uri.toString();
                                UsersRef.child("profileimage").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if (task.isSuccessful())
                                        {
//                                            Toast.makeText(SetupActivity.this, "profile image stored to firebase database successfully", Toast.LENGTH_SHORT).show();
                                            cameraIcon.setVisibility(View.GONE);
//                                            photo = 9;
                                            photo = false;
                                            progressDialogImage.dismiss();
                                        }
                                        else
                                        {
                                            String message = task.getException().getMessage();
                                            Toast.makeText(SetupActivity.this, "Error Occured: "+ message, Toast.LENGTH_SHORT).show();
                                            progressDialogImage.dismiss();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
            else
            {
                Toast.makeText(this,  "Error occured: Image can not be croped Try Again", Toast.LENGTH_SHORT).show();
                progressDialogImage.dismiss();
            }
        }
    }

    private void SaveAccountSetupInformation()
    {

        final String Username = UserName.getText().toString();
        final String fullname = FullName.getText().toString();
        final String Countryname = CountryName.getText().toString();

        if (TextUtils.isEmpty(Username)) {
            UserName.setError("UserName con't be Empty");
        }
        else if (!VALID_USERNAME_REGEX.matcher(UserName.getText().toString()).find()) {
            UserName.setError("Please Username aleast 7-20 characters\nuse,\"a-z\"\"A-Z\",\"0-9\" ,Ex:_ja_B.iulla9_.");
        }
        else if (checkusername)
        {
            CheckUsername(Username);
        }
        else if (TextUtils.isEmpty(fullname)) {
            FullName.setError("FullName con't be Empty");
        }
        else if (TextUtils.isEmpty(Countryname)) {
            CountryName.setError("CountryName con't be Empty");
        }
        else if (photo)
        {
            Toast.makeText(this, "Please select Profile Image", Toast.LENGTH_LONG).show();
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            HashMap userMap = new HashMap();
            userMap.put("username",Username);
            userMap.put("fullname",fullname);
            userMap.put("countryname",Countryname);
            userMap.put("status","none");
            userMap.put("gender","none");
            userMap.put("dob","none");
            userMap.put("relationship","none");
            userMap.put("phonenumber","none");
            userMap.put("email","none");
            UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful())
                    {
                        SendUserToMainActivity();
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SetupActivity.this, "Your Account is created Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String msg = task.getException().getMessage();
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SetupActivity.this, "Error occured:" + msg, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        /////////////////////

    }
    private void CheckUsername(String Username)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
            ref.orderByChild("username").equalTo(Username).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        UserName.setError("Username already exists!!!");
                    }
                    else
                    {
                        checkusername = false;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
    }

    private void SendUserToMainActivity()
    {
        Intent mainintent = new Intent(SetupActivity.this, MainActivity.class);
        mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainintent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SetupActivity.this.finish();
//        System.exit(0);
    }
}