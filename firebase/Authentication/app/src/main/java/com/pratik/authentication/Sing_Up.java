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
import com.google.firebase.auth.FirebaseAuth;

public class Sing_Up extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    String User_id , User_Password;
    private ProgressDialog progressDialog;
    private EditText editTextEmail;
    private EditText editTextpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing__up);

        getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);
        editTextEmail=(EditText)findViewById(R.id.EditText_id);
        editTextpassword=(EditText)findViewById(R.id.EditText_password);
    }
    public void New_Register(View view)
    {
        final String email=editTextEmail.getText().toString().trim();
        String psw=editTextpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            //email is null or not
            Toast.makeText(this, "Please enter Email Id", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(psw))
        {
            //password is null or not
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(psw.length() <= 8)
        {
            //check lenght
            Toast.makeText(this, "Password must be greaterthan Or Equal to 8 characters..", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering User..");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,psw).addOnCompleteListener(this,(task -> {
            if(task.isSuccessful()){
                {
                    //successful Registerd

                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Sing_Up.this, "Registered Successfully.Please check your email for verification", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                            else
                            {
                                progressDialog.cancel();
                                Toast.makeText(Sing_Up.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // Toast.makeText(Main2Activity.this, "Registered SuccessFully", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(Sing_Up.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        }));
    }
}