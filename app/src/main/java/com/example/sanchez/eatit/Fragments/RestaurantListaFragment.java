package com.example.sanchez.eatit.Fragments;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sanchez.eatit.Interface.IItemClickListener;
import com.example.sanchez.eatit.R;
import com.example.sanchez.eatit.SignUp;
import com.example.sanchez.eatit.ViewHolder.RestauranteViewModel;
import com.example.sanchez.eatit.Model.RestauranteModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class RestaurantListaFragment extends Fragment {

    //Inicializacion de variables
    private FrameLayout flRestauranteLista;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private TextView lblCategoryID;
    private String categoryID = "";

    private RecyclerView rvRestaurante;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter<RestauranteModel, RestauranteViewModel> adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }//onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_lista, container, false);

        flRestauranteLista = view.findViewById(R.id.flRestauranteLista);
        rvRestaurante = view.findViewById(R.id.rvRestaurante);

        //Conexion e instanciacion a  firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Restaurant");

        //Cargar platillos
        rvRestaurante.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvRestaurante.setLayoutManager(layoutManager);
        cargarRestaurante();

        return view;
    }//onCreateView

    private void cargarRestaurante() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Espere un momento...");
        progressDialog.show();

        adapter = new FirebaseRecyclerAdapter<RestauranteModel, RestauranteViewModel>(RestauranteModel.class, R.layout.restaurant_item, RestauranteViewModel.class, databaseReference) {
            @Override
            protected void populateViewHolder(RestauranteViewModel menuViewHolder, RestauranteModel restauranteModel, int i) {
                menuViewHolder.lblRestaurant_Title.setText(restauranteModel.getName());
                Picasso.with(getActivity().getBaseContext()).load(restauranteModel.getImage())
                        .into(menuViewHolder.imgRestaurant_image);
                menuViewHolder.lblRestaurant_Address.setText(restauranteModel.getAddress());
                menuViewHolder.lblRestaurant_Description.setText(restauranteModel.getDescription());
                menuViewHolder.lblRestaurant_City.setText(restauranteModel.getCity());
                menuViewHolder.lblRestaurant_Schedule.setText(restauranteModel.getSchedule());

                progressDialog.dismiss();

                //Asignacion de la fuente
                Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NABILA.TTF");
                menuViewHolder.lblRestaurant_Title.setTypeface(typeface);

                final RestauranteModel clickItem = restauranteModel;
                menuViewHolder.setItemClickListener(new IItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClik) {

                        CategoriaListaFragment categoriaListaFragment = new CategoriaListaFragment();
                        /*
                        Bundle bundle = new Bundle();
                        bundle.putString("categoryID", adapter.getRef(position).getKey());
                        restaurantListaFragment.setArguments(bundle);
                         */
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        //fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        fragmentTransaction.replace(flRestauranteLista.getId(),categoriaListaFragment);
                        fragmentTransaction.commit();
                    }//onClick
                });
            }//populateViewHolder
        };
        rvRestaurante.setAdapter(adapter);
    }//cargarRestaurante

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }//onViewCreated
}//RestaurantListaFragment