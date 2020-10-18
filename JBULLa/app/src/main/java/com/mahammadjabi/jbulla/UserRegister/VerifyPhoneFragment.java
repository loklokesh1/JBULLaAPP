package com.mahammadjabi.jbulla.UserRegister;

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
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mahammadjabi.jbulla.MainActivity;
import com.mahammadjabi.jbulla.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyPhoneFragment extends Fragment {

    private EditText otp;
    private Button verifybtn,resend;
    private ProgressBar ProgressBar;
    private String email,phone,password;
    private Timer timer;
    private TextView textphone;
    private int count=60;
    private String mVerificationId;
    private  PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private FirebaseAuth firebaseAuth;


    public VerifyPhoneFragment() {
        // Required empty public constructor
    }

    public VerifyPhoneFragment(String email, String phone, String password) {
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState){

        super.onViewCreated(view,savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        init(view);
        textphone.setText("Verification cade has been sent to +91"+phone);
        sendOTP();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (count == 0){
                            resend.setText("Resend");
                            resend.setEnabled(true);
                            resend.setAlpha(1f);

                        }
                        else{
                            resend.setText("Rensend in "+count);
                            count--;

                        }
                    }
                });
            }
        },0 , 1000);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendopt();
                resend.setEnabled(false);
                resend.setAlpha(0.5f);
                count =60;
            }
        });

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText() == null || otp.getText().toString().isEmpty()){
                    return;
                }
                otp.setError(null);
                String code = otp.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                signInWithPhoneAuthCredential(credential);
                ProgressBar.setVisibility(View.VISIBLE);

            }
        });

    }
    private void init(View view)
    {
        otp = view.findViewById(R.id.otp);
        ProgressBar = view.findViewById(R.id.progressBar);
        verifybtn = view.findViewById(R.id.verifybtn);
        resend = view.findViewById(R.id.resend);
        textphone = view.findViewById(R.id.textphone);
    }

    private void sendOTP(){
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
//                Log.d(TAG, "onVerificationCompleted:" + credential);
//
//                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                // Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                    otp.setError(e.getMessage());
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    otp.setError(e.getMessage());
                }
                ProgressBar.setVisibility(View.INVISIBLE);

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                //  Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };

        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber("+91" + phone, 60, TimeUnit.SECONDS, getActivity(),
                        mCallback);
    }
    private void resendopt(){

        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber("+91" + phone, 60, TimeUnit.SECONDS, getActivity(),
                        mCallback,mResendToken);
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            AuthCredential authCredential = EmailAuthProvider.getCredential(email,password);

                            user.linkWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent usernameintent = new Intent(getContext(), MainActivity.class);
                                        usernameintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(usernameintent);
                                        getActivity().finish();
                                    }
                                    else
                                    {
                                        String error = "Phone number is already registered try with another number!";
                                        Toast.makeText(getContext(),error,Toast.LENGTH_LONG).show();
                                        ProgressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            // ...
                        }
                        else {
                            // Sign in failed, display a message and update the UI
                            //  Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                otp.setError("Invalid OTP");
                            }
                            ProgressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
   }
}

