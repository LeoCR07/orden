package com.example.orden.Splash;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.orden.Login.LoginActivity;
import com.example.orden.Principal.MainActivity;
import com.example.orden.R;

public class SplashActivity extends AppCompatActivity {

    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        window = getWindow();

        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        window.setStatusBarColor(Color.parseColor("#F8C3C2C2"));



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
            }
        },1000);
    }
}