package com.example.sanchez.eatit.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.sanchez.eatit.R;
import com.example.sanchez.eatit.Fragments.PromocionListaFragment;

public class HomeFragment extends Fragment {

    //Declaracion de variables
    private HomeViewModel homeViewModel;
    private FrameLayout fl_main;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Declaro frameLayout
        fl_main = root.findViewById(R.id.fl_home);
        setFrament(new PromocionListaFragment());
        return root;
    }//onCreateView

    //Llamo fragment de menu de productos
    private void setFrament(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(fl_main.getId(), fragment);
        fragmentTransaction.commit();
    }//setFrament
}//HomeFragment