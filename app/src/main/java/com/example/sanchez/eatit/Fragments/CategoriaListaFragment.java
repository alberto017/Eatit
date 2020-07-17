package com.example.sanchez.eatit.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.sanchez.eatit.Interface.IItemClickListener;
import com.example.sanchez.eatit.R;
import com.example.sanchez.eatit.ViewHolder.CategoriaViewHolder;
import com.example.sanchez.eatit.Model.CategoriaModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CategoriaListaFragment extends Fragment {

    //Inicializacion de variables
    private FrameLayout flCategoriaLista;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private RecyclerView rvCategoria;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<CategoriaModel, CategoriaViewHolder> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoria_lista, container, false);

        //Enlazamos los componente
        flCategoriaLista = view.findViewById(R.id.flCategoriaLista);
        //lblFullName = view.findViewById(R.id.lblFullName);
        rvCategoria = view.findViewById(R.id.rvCategoria);

        //Conexion e instanciacion a  firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Category");

        //Asignar usuario
        /*
        lblFullName.setText(Common.currentUsuarioModel.getName());
        */

        //Cargar categorias
        rvCategoria.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvCategoria.setLayoutManager(layoutManager);
        cargarCategoria();

        return view;
    }//onCreateView

    private void cargarCategoria() {
        adapter = new FirebaseRecyclerAdapter<CategoriaModel, CategoriaViewHolder>(CategoriaModel.class, R.layout.category_item, CategoriaViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(CategoriaViewHolder menuViewHolder, CategoriaModel categoriaModel, int i) {
                menuViewHolder.lblCategory_title.setText(categoriaModel.getName());
                Picasso.with(getActivity().getBaseContext()).load(categoriaModel.getImage())
                        .into(menuViewHolder.lblCategory_image);

                //Asignacion de la fuente
                Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NABILA.TTF");
                menuViewHolder.lblCategory_title.setTypeface(typeface);

                final CategoriaModel clickItem = categoriaModel;

                menuViewHolder.setItemClickListener(new IItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClik) {

                        ProductoListaFragment productoListaFragment = new ProductoListaFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("categoryID", adapter.getRef(position).getKey());
                        productoListaFragment.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        //fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        fragmentTransaction.replace(flCategoriaLista.getId(), productoListaFragment);
                        fragmentTransaction.commit();
                    }//onClick
                });
            }//populateViewHolder
        };
        rvCategoria.setAdapter(adapter);
    }//cargarCategoria

}//CategoriaListaFragment