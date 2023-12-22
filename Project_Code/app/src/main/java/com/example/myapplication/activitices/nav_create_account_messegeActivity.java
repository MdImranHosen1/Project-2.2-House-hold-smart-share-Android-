package com.example.myapplication.activitices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class nav_create_account_messegeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_create_account_messege);


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void registration(View view) {
        Log.d("tag","error");
        startActivity(new Intent(nav_create_account_messegeActivity.this,registrationActivity.class));
    }

    public void login(View view) {
        startActivity(new Intent(nav_create_account_messegeActivity.this,loginActivity.class));
    }
}