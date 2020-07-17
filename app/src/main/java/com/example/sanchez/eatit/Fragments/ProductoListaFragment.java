package com.example.sanchez.eatit.Fragments;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sanchez.eatit.Interface.IItemClickListener;
import com.example.sanchez.eatit.R;
import com.example.sanchez.eatit.ViewHolder.PlatillosViewHolder;
import com.example.sanchez.eatit.Model.PlatillosModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ProductoListaFragment extends Fragment {

    //Inicializacion de variables
    private FrameLayout flPlatilloLista;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private TextView lblCategoryID;
    private String categoryID = "";

    private RecyclerView rvPlatillo;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter<PlatillosModel, PlatillosViewHolder> adapter;

    private FirebaseRecyclerAdapter<PlatillosModel, PlatillosViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    private MaterialSearchBar searchBar;


    public ProductoListaFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = this.getArguments();
        if (data != null) {
            categoryID = data.getString("categoryID");
        }//if
    }//onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_producto_lista, container, false);

        flPlatilloLista = view.findViewById(R.id.flPlatilloLista);
        searchBar = view.findViewById(R.id.searchBar);
        rvPlatillo = view.findViewById(R.id.rvPlatillo);

        //Conexion e instanciacion a  firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Food");

        //Cargar platillos
        rvPlatillo.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvPlatillo.setLayoutManager(layoutManager);

        cargarPlatillo(categoryID);

        //Search
        searchBar.setHint("Inserta el platillo");

        loadSuggest();
        searchBar.setLastSuggestions(suggestList);
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }//beforeTextChanged

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<>();
                for (String search : suggestList) {
                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase())) {
                        suggest.add(search);
                    }//if
                }//for
            }//onTextChanged

            @Override
            public void afterTextChanged(Editable editable) {

            }//afterTextChanged
        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //Restauramos el adaptador cuando la busqueda se cierra
                if (!enabled) {
                    rvPlatillo.setAdapter(adapter);
                }//if

            }//onSearchStateChanged

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //Mostramos resultado cuando la busqueda termina
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        return view;
    }//onCreateView

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<PlatillosModel, PlatillosViewHolder>(
                PlatillosModel.class,
                R.layout.food_item,
                PlatillosViewHolder.class,
                databaseReference.orderByChild("Name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(PlatillosViewHolder platillosViewHolder, PlatillosModel platillosModel, int i) {

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Espere un momento...");
                progressDialog.show();

                platillosViewHolder.lblFood_title.setText(platillosModel.getName());
                Picasso.with(getActivity().getBaseContext()).load(platillosModel.getImage())
                        .into(platillosViewHolder.lblFood_image);
                platillosViewHolder.lblFood_price.setText(platillosModel.getPrice());
                platillosViewHolder.lblFood_discount.setText(platillosModel.getDiscount());
                platillosViewHolder.lblFood_status.setText(platillosModel.getStatus());

                progressDialog.dismiss();

                //Asignacion de la fuente
                Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NABILA.TTF");
                platillosViewHolder.lblFood_title.setTypeface(typeface);

                final PlatillosModel platillos = platillosModel;
                platillosViewHolder.setItemClickListener(new IItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        PlatilloDetalleFragment platilloDetalleFragment = new PlatilloDetalleFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("FoodID", searchAdapter.getRef(position).getKey());
                        platilloDetalleFragment.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        //fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        fragmentTransaction.replace(flPlatilloLista.getId(), platilloDetalleFragment);
                        fragmentTransaction.commit();
                    }//onClick
                });
            }//populateViewHolder
        };
        rvPlatillo.setAdapter(searchAdapter);
    }//startSearch

    private void loadSuggest() {

        databaseReference.orderByChild("menuID").equalTo(categoryID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            PlatillosModel item = postSnapshot.getValue(PlatillosModel.class);
                            suggestList.add(item.getName());
                        }//for
                    }//onDataChange

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }//onCancelled
                });

    }//loadSuggest


    private void cargarPlatillo(String categoryID) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Espere un momento...");
        progressDialog.show();

        //Equivalete a select * from food where MenuID =
        adapter = new FirebaseRecyclerAdapter<PlatillosModel, PlatillosViewHolder>(PlatillosModel.class, R.layout.food_item, PlatillosViewHolder.class, databaseReference.orderByChild("menuID").equalTo(categoryID)) {
            @Override
            protected void populateViewHolder(PlatillosViewHolder platillosViewHolder, PlatillosModel platillosModel, int i) {
                platillosViewHolder.lblFood_title.setText(platillosModel.getName());
                Picasso.with(getActivity().getBaseContext()).load(platillosModel.getImage())
                        .into(platillosViewHolder.lblFood_image);
                platillosViewHolder.lblFood_price.setText(platillosModel.getPrice());
                platillosViewHolder.lblFood_discount.setText(platillosModel.getDiscount());
                platillosViewHolder.lblFood_status.setText(platillosModel.getStatus());

                progressDialog.dismiss();

                //Asignacion de la fuente
                Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NABILA.TTF");
                platillosViewHolder.lblFood_title.setTypeface(typeface);

                final PlatillosModel platillos = platillosModel;
                platillosViewHolder.setItemClickListener(new IItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        PlatilloDetalleFragment platilloDetalleFragment = new PlatilloDetalleFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("FoodID", adapter.getRef(position).getKey());
                        platilloDetalleFragment.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        //fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        fragmentTransaction.replace(flPlatilloLista.getId(), platilloDetalleFragment);
                        fragmentTransaction.commit();

                    }//onClick
                });
            }//populateViewHolder
        };
        rvPlatillo.setAdapter(adapter);
    }//cargarPlatillo

}//ProductoListaFragment