package com.example.sanchez.eatit.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.sanchez.eatit.Database.Database;
import com.example.sanchez.eatit.Model.CarritoModel;
import com.example.sanchez.eatit.R;
import com.example.sanchez.eatit.Model.PlatillosModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class PlatilloDetalleFragment extends Fragment {

    private ImageView imgFoodDetalle;
    private TextView lblNombreDetalle;
    private TextView lblPrecioDetalle;
    private TextView lblDescuentoDetalle;
    private TextView lblIngredientesDetalle;
    private TextView lblTituloCantidadDetalle;

    private TextView lblTituloPrecioDetalle;
    private TextView lblTituloDescuentoDetalle;
    private TextView lblTituloIngredientesDetalle;

    private ElegantNumberButton spinnerCantidadDetalle;
    private FloatingActionButton btnAgregarDetalle;
    private CollapsingToolbarLayout collapsingDetalle;
    private AppBarLayout app_bar_layout;
    private String platilloID = "";
    String SpinnerValue = "";

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    PlatillosModel platillosModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = this.getArguments();
        if (data != null) {
            platilloID = data.getString("FoodID");
            //Toast.makeText(getActivity(),platilloID,Toast.LENGTH_LONG).show();
        }//if
    }//onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_platillo_detalle, container, false);

        collapsingDetalle = view.findViewById(R.id.collapsingDetalle);
        app_bar_layout = view.findViewById(R.id.app_bar_layout);
        imgFoodDetalle = view.findViewById(R.id.imgFoodDetalle);

        lblTituloPrecioDetalle = view.findViewById(R.id.lblTituloPrecioDetalle);
        lblTituloDescuentoDetalle = view.findViewById(R.id.lblTituloDescuentoDetalle);
        lblTituloIngredientesDetalle = view.findViewById(R.id.lblTituloIngredientesDetalle);
        lblTituloCantidadDetalle = view.findViewById(R.id.lblTituloCantidadDetalle);

        lblNombreDetalle = view.findViewById(R.id.lblNombreDetalle);
        lblPrecioDetalle = view.findViewById(R.id.lblPrecioDetalle);
        lblDescuentoDetalle = view.findViewById(R.id.lblDescuentoDetalle);
        lblIngredientesDetalle = view.findViewById(R.id.lblIngredientesDetalle);

        spinnerCantidadDetalle = view.findViewById(R.id.spinnerCantidadDetalle);
        btnAgregarDetalle = view.findViewById(R.id.btnAgregarDetalle);

        //Asignacion de la fuente
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NABILA.TTF");

        lblNombreDetalle.setTypeface(typeface);

        //Conexion e instanciacion a  firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Food");

        cargarDetalle(platilloID);

        return view;
    }//onCreateView

    private void cargarDetalle(String platilloID) {
        databaseReference.child(platilloID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                platillosModel = dataSnapshot.getValue(PlatillosModel.class);

                //Set Image
                Picasso.with(getActivity().getBaseContext()).load(platillosModel.getImage())
                        .into(imgFoodDetalle);

                collapsingDetalle.setTitle(platillosModel.getName());
                lblNombreDetalle.setText(platillosModel.getName());
                lblPrecioDetalle.setText(platillosModel.getPrice());
                lblDescuentoDetalle.setText(platillosModel.getDiscount());
                lblIngredientesDetalle.setText(platillosModel.getDescription());
            }//onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }//cargarDetalle


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lblIngredientesDetalle.setText(platilloID);

        btnAgregarDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getActivity().getBaseContext()).addToCart(new CarritoModel(
                        platilloID,
                        platillosModel.getName(),
                        spinnerCantidadDetalle.getNumber(),
                        platillosModel.getPrice(),
                        platillosModel.getDiscount()
                ));
                Toast.makeText(getActivity(), "Agregado al carrito", Toast.LENGTH_LONG).show();
            }//onClick
        });

    }//onViewCreated

}//PlatilloDetalleFragment