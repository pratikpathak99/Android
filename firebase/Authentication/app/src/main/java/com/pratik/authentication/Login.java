package com.pratik.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText emailid;
    private EditText password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);
        emailid=(EditText)findViewById(R.id.EditText_id);
        password=(EditText)findViewById(R.id.EditText_password);
    }

    public void Sing_in(View view) {
        //sing in the user..
        final String email = emailid.getText().toString().trim();
        String psd = password.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter Email Id", Toast.LENGTH_SHORT).show();
            emailid.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(psd))
        {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        }
        progressDialog.setMessage("Login User Please Wait..");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,psd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    // Toast.makeText(MainActivity.this, "Hello User!!", Toast.LENGTH_SHORT).show();

                    //verified email id

                    if(firebaseAuth.getCurrentUser().isEmailVerified())
                    {
                        progressDialog.cancel();
                        Intent nextActivity = new Intent(getApplicationContext(),Welcome_Page.class);
                        startActivity(nextActivity);
                    }
                    else
                    {
                        progressDialog.cancel();
                        Toast.makeText(Login.this, "Please Verify your email address", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                }
            }
        });
    }

    public void New_Register(View view) {
        Intent nextActivity = new Intent(getApplicationContext(),Sing_Up.class);
        startActivity(nextActivity);
    }
}