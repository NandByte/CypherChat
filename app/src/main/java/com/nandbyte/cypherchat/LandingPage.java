package com.nandbyte.cypherchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LandingPage extends AppCompatActivity {

    private Button mRegBtn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        mRegBtn = (Button) findViewById(R.id.needAccBtn);
        Button mAnon = (Button) findViewById(R.id.annon_login);
        mAuth = FirebaseAuth.getInstance();

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_intent = new Intent(LandingPage.this, RegActivity.class);
                startActivity(reg_intent);
            }
        });

        mAnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annon_login();
            }
        });
    }
    private void annon_login() {
        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent mainIntent = new Intent(LandingPage.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else{
                    Toast.makeText(LandingPage.this, "You've got some error!!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}