package com.nandbyte.cypherchat;
import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class StartActivity extends AppCompatActivity {

    private TextInputLayout mDisplayName;
    private ProgressDialog mRegProgress;
    private FirebaseUser current_user;

    private Button mLoginBtn;
    private Button mRegBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://cypher-chat-53d4b-default-rtdb.firebaseio.com/");
    private DatabaseReference mDatabase = database.getReference();
    private DatabaseReference userRef = mDatabase.child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mUserDatabase = FirebaseDatabase.getInstance("https://cypher-chat-53d4b-default-rtdb.firebaseio.com/").getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        mDisplayName = findViewById(R.id.display_name);
        mRegBtn = findViewById(R.id.start_reg_btn);
        mRegProgress = new ProgressDialog(this);
        mRegBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(regIntent);
            }
        });
        mLoginBtn = findViewById(R.id.start_login_btn);

//        mLoginBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent loginIntent = new Intent(StartActivity.this,LoginActivity.class);
//                startActivity(loginIntent);
//            }
//        });


        mLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                final String display_name = mDisplayName.getEditText().getText().toString();

                if (!TextUtils.isEmpty(display_name) ){

                    mRegProgress.setTitle("Going Anonymous!");
                    mRegProgress.setMessage("Please wait while we create your account! All the data will be deleted as soon as you logout!");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                try{
                                    current_user = mAuth.getCurrentUser();
                                    String uid = current_user.getUid();

                                    String device_token = FirebaseInstanceId.getInstance().getToken();

                                    HashMap<String,String> userMap = new HashMap<>();
                                    userMap.put("name",display_name);
                                    userMap.put("status"," ");
                                    userMap.put("image","default");
                                    userMap.put("thumb_image","default");
                                    userMap.put("device_token",device_token);
                                    userMap.put("online","true");
                                    userRef.child(uid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            mRegProgress.dismiss();

                                            String current_user_id = mAuth.getCurrentUser().getUid();

                                            String deviceToken = FirebaseInstanceId.getInstance().getToken();

                                            mUserDatabase.child(current_user_id).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Intent mainIntent = new Intent(StartActivity.this,MainActivity.class);
                                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(mainIntent);
                                                    finish();
                                                }
                                            });
                                        }
                                    });
                                }
                                catch (NullPointerException e){
                                    e.printStackTrace();
                                }
                            }
                            else {
                                mRegProgress.hide();
                                Toast.makeText(StartActivity.this,"Cannot Sign in. Please check the form and try again",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
