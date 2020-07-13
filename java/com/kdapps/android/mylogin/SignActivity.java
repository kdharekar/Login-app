package com.kdapps.android.mylogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignActivity extends AppCompatActivity {
    private EditText mUsername ;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mPassword;
    private Button mRegister;
    Member member;
    DatabaseReference reff
            ;
    public boolean exist = false;
    DatabaseReference refnum
            ;
    DatabaseReference refemail;
            ;
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
        reff = FirebaseDatabase.getInstance().getReference("Member");
        refnum = FirebaseDatabase.getInstance().getReference().child("number");
        refemail = FirebaseDatabase.getInstance().getReference().child("email");
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
                if(Checkphone(phone))
                {
                    Query checknum = FirebaseDatabase.getInstance().getReference("number").child(phone.toString());
                    Query checkemail = FirebaseDatabase.getInstance().getReference("email").child(Email);
                    checknum.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                exist = true;
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    checkemail.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                exist = true;
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    if(exist==false) {
                        reff.child(username).setValue(member);
                        refnum.child((phone.toString())).setValue(phone.toString());
                        refemail.child(Email).setValue(Email);

                        Intent intent = Auth.emailIntent(SignActivity.this,Email,password);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(SignActivity.this, "already have a account", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(SignActivity.this, "number Incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public Boolean Checkphone(long num){
        if(!(num <  9999999999L ) ){
            return false ;
        }
        else if((num < 1000000000)){
            return false ;
        }
        return true;

    }

}
