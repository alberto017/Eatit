package com.example.sanchez.eatit.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.hoang8f.widget.FButton;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sanchez.eatit.Adapter.CartAdapter;
import com.example.sanchez.eatit.Database.Database;
import com.example.sanchez.eatit.Model.CarritoModel;
import com.example.sanchez.eatit.Model.SolicitudModel;
import com.example.sanchez.eatit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarritoListaFragment extends Fragment {

    FrameLayout flCart;
    TextView lblCardTotal;
    TextView cart_total;
    FButton btnCardAdd;
    RecyclerView rvCart;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<CarritoModel> orderModelList;
    CartAdapter cartAdapter;
    CarritoModel carritoModel;
    int total = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }//onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carrito_lista, container, false);

        flCart = view.findViewById(R.id.flCart);
        rvCart = view.findViewById(R.id.rvCart);
        lblCardTotal = view.findViewById(R.id.lblCardTotal);
        cart_total = view.findViewById(R.id.cart_total);
        btnCardAdd = view.findViewById(R.id.btnCardAdd);

        rvCart.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvCart.setLayoutManager(layoutManager);

        //Asignacion de la fuente
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NABILA.TTF");
        cart_total.setTypeface(typeface);
        lblCardTotal.setTypeface(typeface);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Request");

        loadListFood();

        return view;
    }//onCreateView

    private void loadListFood() {
        orderModelList = new Database(getActivity()).getCart();
        cartAdapter = new CartAdapter(orderModelList, getActivity());
        rvCart.setAdapter(cartAdapter);

        for (CarritoModel carritoModel : orderModelList) {
            total += (Integer.parseInt(carritoModel.getPrice())) * (Integer.parseInt(carritoModel.getQuantity()));
            Locale locale = new Locale("es", "US");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

            lblCardTotal.setText(numberFormat.format(total));
        }//for

    }//loadListFood

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCardAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UbicacionAutomaticaFragment ubicacionAutomaticaFragment = new UbicacionAutomaticaFragment();
                Bundle bundle = new Bundle();
                bundle.putString("OrderTotal", String.valueOf(total));
                bundle.putParcelableArrayList("orderList", (ArrayList<? extends Parcelable>) orderModelList);
                ubicacionAutomaticaFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(flCart.getId(), ubicacionAutomaticaFragment);
                fragmentTransaction.commit();
            }//onClick
        });

    }//onViewCreated

}//CarritoListaFragment