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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegActivity extends AppCompatActivity {

    private TextInputLayout mName;
    private TextInputLayout mEmail;
    private TextInputLayout mPass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        mAuth = FirebaseAuth.getInstance();

        mName = (TextInputLayout) findViewById(R.id.reg_input_name);
        mEmail = (TextInputLayout) findViewById(R.id.reg_input_email);
        mPass = (TextInputLayout) findViewById(R.id.reg_input_pass);
        Button mRegister = (Button) findViewById((R.id.Regester_btn));

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String display_name = mName.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPass.getEditText().getText().toString();
//              Toast.makeText(RegActivity.this, "Inside Reg function!!",Toast.LENGTH_LONG).show();
                register_user(display_name,email,password);
            }
        });
    }


    private void register_user(String display_name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent mainIntent = new Intent(RegActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else{
                    Toast.makeText(RegActivity.this, "You've got some error!!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}