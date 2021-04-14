package com.pratik.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuth extends AppCompatActivity {

    private Button btnLogin;
    private EditText etPhoneNumber;

    private static final String TAG = "PhoneAuth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        btnLogin = findViewById(R.id.btnLogin);
        etPhoneNumber = findViewById(R.id.editPhoneNumber);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = etPhoneNumber.getText().toString();
                if (phoneNumber.isEmpty())
                    Toast.makeText(PhoneAuth.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
                else {
                    //verify phone number
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91"+phoneNumber, 60, TimeUnit.SECONDS, PhoneAuth.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                    signInUser(phoneAuthCredential);
                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {
                                    Log.d(TAG, "onVerificationFailed:"+e.getLocalizedMessage());
                                }

                                @Override
                                public void onCodeSent(final String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(verificationId, forceResendingToken);
                                    //
                                    Dialog dialog = new Dialog(PhoneAuth.this);
                                    dialog.setContentView(R.layout.verify_popup);

                                    final EditText etVerifyCode = dialog.findViewById(R.id.etVerifyCode);
                                    Button btnVerifyCode = dialog.findViewById(R.id.btnVerifyOTP);
                                    btnVerifyCode.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String verificationCode = etVerifyCode.getText().toString();
                                            if(verificationId.isEmpty()) return;
                                            //create a credential
                                            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,verificationCode);
                                            signInUser(credential);
                                        }
                                    });
                                    dialog.show();
                                }
                            });
                }
            }
        });
    }
    private void signInUser(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(PhoneAuth.this, "Welcome..", Toast.LENGTH_SHORT).show();
                        }else {
                            Log.d(TAG, "onComplete:"+task.getException().getLocalizedMessage());
                        }
                    }
                });
    }
}