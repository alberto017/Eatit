package com.example.sanchez.eatit;

import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Acceso = new Intent(SplashScreen.this, Inicio.class);
                startActivity(Acceso);
                finish();
            }
        },2000);

    }//onCreate
}//SplashScreen