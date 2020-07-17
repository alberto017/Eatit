package com.example.sanchez.eatit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.sanchez.eatit.Fragments.UbicacionAutomaticaFragment;
import com.example.sanchez.eatit.ui.home.HomeViewModel;

public class Ubicacion extends AppCompatActivity {

    //Declaracion de variables
    private HomeViewModel homeViewModel;
    private FrameLayout flUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        //Declaro frameLayout
        flUbicacion = findViewById(R.id.flUbicacion);
        setFrament(new UbicacionAutomaticaFragment());
    }

    //Llamo fragment de menu de productos
    private void setFrament(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(flUbicacion.getId(), fragment);
        fragmentTransaction.commit();
    }//setFrament
}