package com.jabiullamahammad.sabol.UserRegister;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import com.jabiullamahammad.sabol.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private EditText UserName, FullName,CountryName;
    private Button SaveInformationButton;
    private CircleImageView ProfileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        UserName = (EditText) findViewById(R.id.userName);
        FullName = (EditText) findViewById(R.id.fullName);
        CountryName = (EditText) findViewById(R.id.country);
        ProfileImage = (CircleImageView) findViewById(R.id.logoimg);
        SaveInformationButton = (Button) findViewById(R.id.saveprofile);

    }
}