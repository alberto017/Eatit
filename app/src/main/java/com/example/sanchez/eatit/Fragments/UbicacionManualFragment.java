package com.example.sanchez.eatit.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanchez.eatit.HttpDataHandler;
import com.example.sanchez.eatit.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UbicacionManualFragment extends Fragment implements OnMapReadyCallback {

    //Declaracion de variables
    private GoogleMap mMap;
    private TextView lblTituloMaps;
    private MapView mvMaps;
    private EditText edtObservacionesMaps;
    private EditText edtDireccionMaps;
    private Button btnAgregarMaps;
    private ImageButton btnBuscarMaps;

    private LocationManager locationManager;
    private String provider;
    double lat, lng;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ubicacion_automatica, container, false);

        //Referencia de controladores
        lblTituloMaps = view.findViewById(R.id.lblTituloMapa);
        mvMaps = view.findViewById(R.id.mvMaps);
        edtDireccionMaps = view.findViewById(R.id.edtDireccionMaps);
        edtObservacionesMaps = view.findViewById(R.id.edtObservacionesMaps);
        btnBuscarMaps = view.findViewById(R.id.imgBuscarMaps);
        btnAgregarMaps = view.findViewById(R.id.btnAgregarMaps);

        //Asignacion del tipo de fuente
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/NABILA.TTF");
        lblTituloMaps.setTypeface(typeface);

        //Iniciamos ViewMap
        mvMaps.getMapAsync(this);
        mvMaps.onCreate(savedInstanceState);

        return view;

    }//onCreateView

    //Se obtiene la ubicacion actual asi mismo obtenemos los permisos
    private void getLocation() {

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }//if

        final Location location = locationManager.getLastKnownLocation(provider);
        if (location == null) {
            Toast.makeText(getContext(), "¡Error en ubicacion!", Toast.LENGTH_LONG).show();
        }//if
    }//getLocation


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAgregarMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Direccion asignada", Toast.LENGTH_LONG).show();
            }//onClick
        });

        btnBuscarMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetAddress getAddress = new GetAddress();
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                String locationName = edtDireccionMaps.getText().toString();
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                try {
                    List<Address> addressList = geocoder.getFromLocationName(locationName, 1);
                    if (addressList.size() > 0) {
                        Address address = addressList.get(0);
                        gotoLocation(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(), address.getLongitude())));
                        //Toast.makeText(getActivity(), address.getLocality(), Toast.LENGTH_LONG).show();
                        getAddress.execute(String.format("%.4f,%.4f", lat, lng));
                    }//if
                } catch (IOException e) {
                    e.printStackTrace();
                }//catch

            }//onClick
        });

    }//onViewCreated

    //Obtiene la ubicacion actual
    private void gotoLocation(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mMap.moveCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }//gotoLocation

    //Metodo princilap del MapView
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }//permisos

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        coordenadas(googleMap);
    }//onMapReady

    //Agregamos las coordenadas iniciales asi mismo agregamos un marcador
    private void coordenadas(GoogleMap googleMap) {
        // Add a marker in Sydney and move the camera
        LatLng place = new LatLng(21.04067931517163, -105.25004307307772);
        mMap.addMarker(new MarkerOptions().position(place).title("Centro"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 30));
    }//coordenadas


    //Configuramos comportamiento del fragment asi mismo del ViewMap
    @Override
    public void onStart() {
        super.onStart();
        mvMaps.onStart();
    }//onStart

    @Override
    public void onResume() {
        super.onResume();
        mvMaps.onResume();
    }//onResume

    @Override
    public void onPause() {
        super.onPause();
        mvMaps.onPause();
    }//onPause

    @Override
    public void onStop() {
        super.onStop();
        mvMaps.onStop();
    }//onStop

    @Override
    public void onDestroy() {
        super.onDestroy();
        mvMaps.onDestroy();
    }//onDestroy


    //Hilo secundario
    private class GetAddress extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        //Se ejecuta antes de iniciar el hilo secundario
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Cargando coordenadas...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }//onPreExecute

        //Pertenece al hilo secundario
        @Override
        protected String doInBackground(String... strings) {

            try {
                double lat = Double.parseDouble(strings[0].split(",")[0]);
                double lng = Double.parseDouble(strings[0].split(",")[1]);
                String response;
                HttpDataHandler httpDataHandler = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%.4f,%4f&sensor=false", lat, lng);
                response = httpDataHandler.getHTTPData(url);
                return response;
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
            }//catch
            return null;
        }//doInBackground

        //Se ejecuta posteriormente al hilo secundario
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                String address = ((JSONArray) jsonObject.get("results")).getJSONObject(0).get("formatted_address").toString();
                Toast.makeText(getActivity(), address, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }//catch
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }//else
        }//onPostExecute
    }//GeoCoordinates
}//UbicacionManualFragment