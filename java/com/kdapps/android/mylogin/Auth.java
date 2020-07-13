package com.kdapps.android.mylogin;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Auth extends AppCompatActivity {
    private static final String emailvalue = "emailvarify";
    private static final String passwordvalue = "passwordverify";
    public String CodeBySystem;
    public Button verify_btn;
    private FirebaseAuth mAuth;

    String phone;
    public EditText phoneNoEnteredByTheUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(getIntent().getStringExtra(emailvalue), getIntent().getStringExtra(passwordvalue))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Auth.this, "Email sent...",
                                                Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(Auth.this, task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Auth.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public static Intent emailIntent(Context context , String email,String password) {
        Intent intent = new Intent(context, Auth.class);
        intent.putExtra(emailvalue,email);
        intent.putExtra(passwordvalue,password);
        return intent ;
    }


    }

