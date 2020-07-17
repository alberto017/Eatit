package com.example.sanchez.eatit.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.sanchez.eatit.Common.Common;
import com.example.sanchez.eatit.Model.SolicitudModel;
import com.example.sanchez.eatit.R;
import com.example.sanchez.eatit.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firestore.v1beta1.StructuredQuery;


public class OrderListaFragment extends Fragment {

    //Inicializacion de variables
    public RecyclerView rvOrden;
    public RecyclerView.LayoutManager layoutManager;

    private FrameLayout flCategoriaLista;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    FirebaseRecyclerAdapter<SolicitudModel, OrderViewHolder> adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }//onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_lista, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Request");

        rvOrden = view.findViewById(R.id.rvOrden);
        rvOrden.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvOrden.setLayoutManager(layoutManager);

        cargarOrdenes(Common.currentUsuarioModel.getPhone());

        return view;
    }//onCreateView

    private void cargarOrdenes(String phone) {
        adapter = new FirebaseRecyclerAdapter<SolicitudModel, OrderViewHolder>(
                SolicitudModel.class,
                R.layout.order_item,
                OrderViewHolder.class,
                databaseReference.orderByChild("phone")
                        .equalTo(phone)
        ) {

            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, SolicitudModel solicitudModel, int i) {
                orderViewHolder.lblOrderItemName.setText(adapter.getRef(i).getKey());
                orderViewHolder.lblOrderItemStatus.setText(convertCodeToStatus(solicitudModel.getStatus()));
                orderViewHolder.lblOrderItemAddress.setText(solicitudModel.getAddress());
                orderViewHolder.lblOrderItemPhone.setText(solicitudModel.getPhone());
                orderViewHolder.lblOrderItemDate.setText(solicitudModel.getDate());
            }//populateViewHolder
        };
        rvOrden.setAdapter(adapter);
    }//loadOrdenes

    private String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Enviada";
        else if (status.equals("1"))
            return "En camino";
        else
            return "Finalizada";
    }//convertCodeToStatus


}//OrderListaFragment