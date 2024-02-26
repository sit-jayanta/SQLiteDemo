package com.example.newtasktest.activity;

import static com.example.newtasktest.util.appConstants.Flag;
import static com.example.newtasktest.util.appConstants.My_preference;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.newtasktest.R;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //we are configuring our window to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            SharedPreferences sh= getSharedPreferences(My_preference,MODE_PRIVATE);
            int saved_flag= sh.getInt(Flag,0);

            //to skip login if already login
            if(saved_flag==1){
                finish();
                Intent intent1= new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent1);

            }else {
                Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }

        },2000);


    }
}