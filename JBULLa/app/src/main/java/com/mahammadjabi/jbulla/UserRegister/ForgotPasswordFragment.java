package com.mahammadjabi.jbulla.UserRegister;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mahammadjabi.jbulla.R;

import static com.mahammadjabi.jbulla.UserRegister.CreateAccountFragment.VALID_EMAIL_ADDRESS_REGEX;

public class ForgotPasswordFragment  extends Fragment {

    private EditText email;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private Button btnresetpassword;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgotpassword, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState){

        super.onViewCreated(view,savedInstanceState);
        init(view);

        btnresetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setError(null);
                if (VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText().toString()).find()){
                    progressBar.setVisibility(View.VISIBLE);
                    btnresetpassword.setEnabled(false);
                    firebaseAuth =FirebaseAuth.getInstance();
                    firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        progressBar.setVisibility(View.VISIBLE);
                                        Toast.makeText(getContext(),"Password reset email sent successfully",Toast.LENGTH_LONG).show();
                                        getActivity().onBackPressed();
                                    }
                                    else
                                    {
                                        String error = task.getException().getMessage();
                                        email.setError(error);
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                    btnresetpassword.setEnabled(true);
                                }
                            });
                }
                else
                {
                    email.setError("Please provide valid email");
                }
            }
        });

    }
    private void init(View view)
    {
        email = view.findViewById(R.id.email);
        progressBar = view.findViewById(R.id.progressBar);
        btnresetpassword = view.findViewById(R.id.btnresetpassword);
    }
}
