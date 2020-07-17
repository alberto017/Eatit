package com.example.sanchez.eatit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {

    //Declaracion de variables
    private TextView lblSlogan;
    private Button btnSignUp;
    private Button btnSignIn;
    private TextView lblRecovery;

    private boolean locationPermissionGranted;
    private static final int PERMISSION_REQUEST_CODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Enlazar controladores
        lblSlogan = findViewById(R.id.lblSlogan);
        btnSignUp = findViewById(R.id.btnSignUp_main);
        btnSignIn = findViewById(R.id.btnSignIn_main);
        lblRecovery = findViewById(R.id.lblRecovery);

        //Asignacion de la fuente
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        lblSlogan.setTypeface(typeface);

        if(locationPermissionGranted){
            Toast.makeText(Inicio.this,"¡Permisos activados!",Toast.LENGTH_LONG).show();
        }else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
                }//if
            }//if
        }//else
    }//onCreate

    //Evento onClick
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp_main:
                Intent signUp = new Intent(Inicio.this,SignUp.class);
                startActivity(signUp);
                break;
            case R.id.btnSignIn_main:
                Intent signIn = new Intent(Inicio.this,SignIn.class);
                startActivity(signIn);
                break;
            case R.id.lblRecovery:
                Intent recovery = new Intent(Inicio.this, ReestablecerUsuario.class);
                startActivity(recovery);
                break;
        }//onClick
    }//onClick

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            locationPermissionGranted = true;
            Toast.makeText(Inicio.this,"¡Permisos concedidos!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(Inicio.this,"¡Permisos denegados!",Toast.LENGTH_LONG).show();
        }
    }//onRequestPermissionsResult
}//Inicio