package com.example.myapplication.activitices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registrationActivity extends AppCompatActivity {

    Button signUp;
    EditText name,email,password;
    TextView signIn;

    Uri resultUri;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    ProgressBar progressBar;

    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        auth=FirebaseAuth.getInstance();
        userModel =new UserModel();

        signUp=findViewById(R.id.signUpIdReg);
        name=findViewById(R.id.nameIdReg);
        email=findViewById(R.id.emailIdReg);
        password=findViewById(R.id.passwordIdReg);
        signIn=findViewById(R.id.signInIdReg);

        progressBar=findViewById(R.id.progressbarIdReg);
        progressBar.setVisibility(View.GONE);




        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registrationActivity.this,loginActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }


    private void createUser() {
        String userName =name.getText().toString();
        String userEmail =email.getText().toString();
        String userPassword =password.getText().toString();
        String userProfileImg=getString(R.string.profileImgLink);
        String userPhoneNumber="None";
        String userAddress="None";

        FirebaseDatabase db= FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Users");



        if(TextUtils.isEmpty(userName))
        {
            Toast.makeText(this,"Name is Empty!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userEmail))
        {
            Toast.makeText(this,"Email is Empty!",Toast.LENGTH_SHORT).show();
            return;
        } if(TextUtils.isEmpty(userPassword))
        {
            Toast.makeText(this,"Password is Empty!",Toast.LENGTH_SHORT).show();
            return;
        } if(userPassword.length()<6)
        {
            Toast.makeText(this,"Password length must be getter then 6 digit!",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            userModel.setName(userName);
                            userModel.setEmail(userEmail);
                            userModel.setPassword(userPassword);
                            userModel.setProfileImg(userProfileImg);
                            userModel.setPhoneNumber(userPhoneNumber);
                            userModel.setAddress(userAddress);

                            databaseReference.child(auth.getCurrentUser().getUid()).setValue(userModel);

                            Toast.makeText(registrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(registrationActivity.this, MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(registrationActivity.this, "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


//        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                databaseReference.setValue(userInfo);
//                Toast.makeText(registrationActivity.this, "Data Added successfully", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

}