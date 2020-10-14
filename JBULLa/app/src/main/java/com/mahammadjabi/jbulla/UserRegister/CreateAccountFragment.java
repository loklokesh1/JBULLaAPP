package com.mahammadjabi.jbulla.UserRegister;


//////////////////////////
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.mahammadjabi.jbulla.MainActivity;
import com.mahammadjabi.jbulla.R;

import java.util.regex.Pattern;

public class CreateAccountFragment extends Fragment {
    private EditText email,phone,password,confirmpassword;
    private Button btncreateaccount;
    private TextView logintext;
    private ProgressBar progess;
    private FirebaseAuth firebaseAuth;

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view,@NonNull Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        init(view);

        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity)getContext()).setFragment(new SignInFragment());
            }
        });
        firebaseAuth =FirebaseAuth.getInstance();
        btncreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setError(null);
                password.setError(null);
                confirmpassword.setError(null);
                if (email.getText().toString().isEmpty()){
                    email.setError("Please enter your email..");
                    return ;
                }
                if (phone.getText().toString().isEmpty()){
                    phone.setError("Please enter your phone..");
                    return ;
                }
                if (password.getText().toString().isEmpty()){
                    password.setError("Please enter your password..");
                    return ;
                }
                if (confirmpassword.getText().toString().isEmpty()){
                    confirmpassword.setError("Please enter your password..");
                    return ;
                }
                if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText().toString()).find()){
                    email.setError("Please enter a vallid email");
                    return ;
                }
                if (phone.getText().toString().length() != 10){
                    phone.setError("Please enter a valid Phone number");
                    return ;
                }
                if (!password.getText().toString().equals(confirmpassword.getText().toString())){
                    confirmpassword.setError("Password mismatched");
                    return ;
                }
                CreateAccount();

            }
        });
    }
    private void init(View view){
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        password = view.findViewById(R.id.password);
        confirmpassword = view.findViewById(R.id.confirmpassword);
        progess = view.findViewById(R.id.progess);
        btncreateaccount =view.findViewById(R.id.btncreateaccount);
        logintext =view.findViewById(R.id.logintextlink);

    }
    public void CreateAccount()
    {
        progess.setVisibility(View.VISIBLE);
        firebaseAuth.fetchSignInMethodsForEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if(task.isSuccessful())
                {
                    if (task.getResult().getSignInMethods().isEmpty()){
                        ((RegisterActivity)getActivity()).setFragment(new VerifyPhoneFragment(email.getText().toString(),phone.getText().toString(),password.getText().toString()));
                    }
                    else{
                        email.setError("Email Already exists");
                        progess.setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    String error = task.getException().getMessage();
                    Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                }
                progess.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser!= null)
        {
            Intent usernameintent = new Intent(getContext(),MainActivity.class);
            usernameintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(usernameintent);
            getActivity().finish();

        }
    }

}
////////////////////////////