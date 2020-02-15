package com.example.samplestickerapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    //Firebase Auth
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //init
        firebaseAuth = FirebaseAuth.getInstance();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                checkUserStatus();
           //     Intent intent=new Intent(Splash.this, LoginActivity.class);
           //     startActivity(intent);
          //      finish();
            }
        }, 2000);
    }



    private void checkUserStatus(){
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            //user is signed in stay here
            startActivity(new Intent(Splash.this, EntryActivity.class));
            finish();
        }
        else {
            //user not signed in, go to main activity
            startActivity(new Intent(Splash.this, LoginActivity.class));
            finish();
        }
    }

}
