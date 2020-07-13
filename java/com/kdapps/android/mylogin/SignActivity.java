package com.kdapps.android.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignActivity extends AppCompatActivity {
    private EditText mUsername ;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mPassword;
    private Button mRegister;
    Member member;
    DatabaseReference reff ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);
        mEmail = (EditText) findViewById(R.id.email);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mPhone = (EditText) findViewById(R.id.phone);
        mRegister = (Button) findViewById(R.id.register);
        member = new Member();
        Long phone = Long.parseLong(mPhone.getText().toString().trim());
        reff = FirebaseDatabase.getInstance().getReference("Member");
        mRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String username = (mUsername.getText().toString().trim());
                String Email = (mEmail.getText().toString().trim());
                String password = (mPassword.getText().toString().trim());
                Long phone = Long.parseLong((mPhone.getText().toString().trim()));
                member.setUsername(username);
                member.setEmail(Email);
                member.setNumber(phone);
                member.setPassword(password);
                reff.child(username).setValue(member);

                Toast.makeText(SignActivity.this,"Data Inserted" , Toast.LENGTH_LONG).show();
            }
        });


    }
}
