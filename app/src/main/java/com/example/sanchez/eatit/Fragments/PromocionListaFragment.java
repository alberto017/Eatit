package com.example.sanchez.eatit.Fragments;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.sanchez.eatit.Interface.IItemClickListener;
import com.example.sanchez.eatit.R;
import com.example.sanchez.eatit.ViewHolder.PromocionViewHolder;
import com.example.sanchez.eatit.Model.PromocionModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class PromocionListaFragment extends Fragment {

    //Inicializacion de variables
    private FrameLayout flPromociones;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private RecyclerView rvPromocion;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter<PromocionModel, PromocionViewHolder> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }//onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promocion_lista, container, false);

        flPromociones = view.findViewById(R.id.flPromociones);
        rvPromocion = view.findViewById(R.id.rvPromocion);

        //Conexion e instanciacion a  firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Promotion");

        //Cargar platillos
        rvPromocion.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvPromocion.setLayoutManager(layoutManager);
        cargarPromocion();

        return view;
    }//onCreateView

    private void cargarPromocion() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Espere un momento...");
        progressDialog.show();

        //Equivalete a select * from food where MenuID =
        adapter = new FirebaseRecyclerAdapter<PromocionModel, PromocionViewHolder>(PromocionModel.class, R.layout.promotion_item, PromocionViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(PromocionViewHolder promocionViewHolder, PromocionModel promocionModel, int i) {
                promocionViewHolder.lblPromotion_Title.setText(promocionModel.getTitle());
                Picasso.with(getActivity().getBaseContext()).load(promocionModel.getImage())
                        .into(promocionViewHolder.ImgPromotion_image);
                promocionViewHolder.lblPromotion_RestaurantName.setText(promocionModel.getRestaurantName());
                promocionViewHolder.lblPromotion_price.setText(promocionModel.getPrice());
                promocionViewHolder.lblPromotion_DateEnd.setText(promocionModel.getDateEnd());

                progressDialog.dismiss();

                //Asignacion de la fuente
                Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NABILA.TTF");
                promocionViewHolder.lblPromotion_Title.setTypeface(typeface);

                final PromocionModel clickItem = promocionModel;
                promocionViewHolder.setItemClickListener(new IItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        /*
                        PlatilloDetalleFragment platilloDetalleFragment = new PlatilloDetalleFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("FoodID", adapter.getRef(position).getKey());
                        platilloDetalleFragment.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        //fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        fragmentTransaction.replace(flPromociones.getId(), platilloDetalleFragment);
                        fragmentTransaction.commit();
                        */
                    }//onClick
                });
            }//populateViewHolder
        };
        rvPromocion.setAdapter(adapter);

    }//cargarPromocion
}//onCreateView