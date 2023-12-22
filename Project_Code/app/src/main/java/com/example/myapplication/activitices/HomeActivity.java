package com.example.myapplication.activitices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbarIdMain);
        progressBar.setVisibility(View.GONE);

        if(auth.getCurrentUser()!=null)
        {
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Please wait,you are already logged in.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }
    }
    public void login(View view) {
        startActivity(new Intent(HomeActivity.this,loginActivity.class));
    }

    public void registration(View view) {
        startActivity(new Intent(HomeActivity.this,registrationActivity.class));
    }
    public void guest(View view) {

        String userEmail="guest@gmail.com";
        String userPassword="123456";

        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(HomeActivity.this, "You are in guest mode!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(HomeActivity.this, "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}