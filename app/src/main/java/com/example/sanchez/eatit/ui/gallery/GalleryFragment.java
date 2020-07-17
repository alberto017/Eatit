package com.example.sanchez.eatit.ui.gallery;

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
import com.example.sanchez.eatit.Fragments.RestaurantListaFragment;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FrameLayout fl_gallery;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        //Declaro frameLayout
        fl_gallery = view.findViewById(R.id.fl_gallery);
        //setFrament(new CategoriaListaFragment());
        setFrament(new RestaurantListaFragment());
        return view;
    }

    //Llamo fragment de menu de productos
    private void setFrament(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(fl_gallery.getId(), fragment);
        fragmentTransaction.commit();
    }//setFrament

    /*
    @Override
    public void enviarDatos(String dato) {
        ProductoListaFragment productoListaFragment = getActivity().getFragmentManager().fifindFragmentById(R.id.flPlatilloLista);
    }//enviarDatos
     */
}//GalleryFragment