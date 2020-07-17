package com.example.sanchez.eatit.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanchez.eatit.Common.Common;
import com.example.sanchez.eatit.Database.Database;
import com.example.sanchez.eatit.MenuLateral;
import com.example.sanchez.eatit.Model.CarritoModel;
import com.example.sanchez.eatit.Model.SolicitudModel;
import com.example.sanchez.eatit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class UbicacionAutomaticaFragment extends Fragment {

    private FrameLayout flUbicacionAutomatica;
    private TextView lblTitulo_SelecionarUbicacion;
    private Button btnUbicacionAutomatica;
    private TextView lblUbicacionManual;
    private TextView lblUbicacion;
    String ubicacion;
    private String orderTotal = "";
    List<CarritoModel> orderList;
    private Calendar c;
    private SimpleDateFormat df;
    private String formattedDate;

    private LocationManager locationManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            orderTotal = bundle.getString("OrderTotal");
            orderList = bundle.getParcelableArrayList("orderList");
        }//if

        //Obtener hora
        c = Calendar.getInstance();

        //Obtener fecha
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
    }//onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ubicacion_manual, container, false);

        //Enlazar controladores
        flUbicacionAutomatica = view.findViewById(R.id.flUbicacionAutomatica);
        lblTitulo_SelecionarUbicacion = view.findViewById(R.id.lblTitulo_SelecionarUbicacion);
        btnUbicacionAutomatica = view.findViewById(R.id.btnUbicacionAutomatica);
        lblUbicacionManual = view.findViewById(R.id.lblUbicacionManual);
        lblUbicacion = view.findViewById(R.id.lblUbicacion);

        //Asignacion de la fuente
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NABILA.TTF");
        lblTitulo_SelecionarUbicacion.setTypeface(typeface);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Request");

        estadoGPS();
        registrarLocalizacion();

        return view;
    }//onCreateView

    private void estadoGPS() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getActivity(), "GPS desactivado", Toast.LENGTH_LONG).show();
        }//if
    }//estadoGPS

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnUbicacionAutomatica.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Enviamos parametros a nuestro constructor SolicitudModel
                SolicitudModel solicitudModel = new SolicitudModel(
                        Common.currentUsuarioModel.getPhone(),
                        Common.currentUsuarioModel.getName(),
                        ubicacion,
                        orderTotal,
                        formattedDate,
                        orderList
                );

                //Asignamos currentMillis como nuestra key en carrito
                databaseReference.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(solicitudModel);

                //Eliminamos carrito
                new Database(getActivity()).clearCart();

                //Llamamos activity
                Intent menuLateral = new Intent(getActivity(), MenuLateral.class);
                startActivity(menuLateral);

            }//onClick
        });

        lblUbicacionManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFrament(new UbicacionManualFragment());
            }//setOnClickListener
        });

        lblTitulo_SelecionarUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFrament(new UbicacionManualFragment());
            }//setOnClickListener
        });
    }//onViewCreated

    private void registrarLocalizacion() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }//if
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, new MiDireccion());
    }//registrarLocalizacion


    private class MiDireccion implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            try {
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                List<Address> direccion = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                ubicacion = direccion.get(0).getAddressLine(0);
                lblUbicacion.setText(direccion.get(0).getAddressLine(0));
            } catch (IOException e) {
                e.printStackTrace();
            }//catch
        }//onLocationChanged

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }//onStatusChanged

        @Override
        public void onProviderEnabled(String s) {

        }//onProviderEnabled

        @Override
        public void onProviderDisabled(String s) {

        }//
    }//MiDireccion

    //Llamo fragment de menu de productos
    private void setFrament(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(flUbicacionAutomatica.getId(), fragment);
        fragmentTransaction.commit();
    }//setFrament

}//UbicacionAutomaticaFragment