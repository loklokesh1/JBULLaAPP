package com.mahammadjabi.jbulla.UserRegister;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.mahammadjabi.jbulla.MainActivity;
import com.mahammadjabi.jbulla.R;


public class SignInFragment extends Fragment {

    private EditText email2, password2;
    public TextView createaccounttext,forgottext;
    private FirebaseAuth mfirebaseAuth;
    private Button btnlogin;
    private ProgressBar progess2;
    private FirebaseUser firebaseUser;
    private DatabaseReference UserRef;


    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mfirebaseAuth = FirebaseAuth.getInstance();
        init(view);

        forgottext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity)getActivity()).setFragment(new ForgotPasswordFragment());
            }
        });
        createaccounttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity)getActivity()).setFragment(new CreateAccountFragment());
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

    }

    private void loginUserAccount() {


        String email, password;
        email = email2.getText().toString();
        password = password2.getText().toString();


        if (TextUtils.isEmpty(email)) {
//            Toast.makeText(getContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            email2.setError("Please enter email..");
            return;
        }
        if (TextUtils.isEmpty(password)) {
//            Toast.makeText(getContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            password2.setError("Please enter password..");
            return;
        }
        progess2.setVisibility(View.VISIBLE);
        mfirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            progess2.setVisibility(View.GONE);
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();

                        } else {
                            password2.setError("Login failed! Password Error");
                            progess2.setVisibility(View.INVISIBLE);

                        }
                    }
                });

    }

    private void init(View view)
    {

        email2 = view.findViewById(R.id.email2);
        password2 = view.findViewById(R.id.password2);
        progess2 = view.findViewById(R.id.progess2);
        forgottext = view.findViewById(R.id.forgottext);
        createaccounttext = view.findViewById(R.id.createaccounttext);
        btnlogin = view.findViewById(R.id.btnlogin);
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mfirebaseAuth.getCurrentUser();
        if (currentUser!= null)
        {
            Intent usernameintent = new Intent(getContext(),MainActivity.class);
            startActivity(usernameintent);
            getActivity().finish();

        }
    }

}