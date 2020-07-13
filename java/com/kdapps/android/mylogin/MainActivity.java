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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

public class MainActivity extends AppCompatActivity {
     /*import FirebaseAuth */
    private EditText musername ;
    private EditText mpassword;
    private Button mLogin;
    private Button msignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        musername = (EditText) findViewById(R.id.User_name);
        mpassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.login);
        String username = musername.getText().toString().trim();
        String password = mpassword.getText().toString().trim();
        mLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Query checkuser = FirebaseDatabase.getInstance().getReference("Member").child((musername.getText().toString().trim()));
                checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String username = snapshot.child("username").getValue(String.class);
                            String systempassword = snapshot.child("password").getValue(String.class);
                            Long number = snapshot.child("number").getValue(Long.class);

                            if(systempassword.equals(mpassword.getText().toString().trim())){
                                /*mpassword.setError(null);*/
                               Intent intent = PhoneAuth.newIntent(MainActivity.this,number);
                                /*Intent intent = new Intent(MainActivity.this,Logout.class);*/
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(MainActivity.this,"wrong password ",Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(MainActivity.this,"wrong password ",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this,"username doesnot exist",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        msignup = (Button) findViewById(R.id.signup);
        msignup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignActivity.class);
                startActivity(intent);
            }
        });
    }
}
