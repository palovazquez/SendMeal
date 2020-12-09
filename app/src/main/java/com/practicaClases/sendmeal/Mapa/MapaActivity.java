package com.practicaClases.sendmeal.Mapa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.practicaClases.sendmeal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap myMap;

    LatLng direccionLatitud = null;
    Button btnConfirmarDireccion;
    LatLng ubicacionLocal;
    PolylineOptions linea;
    Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        organizarMapa();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9999 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            organizarMapa();
        }
    }


    @SuppressLint("MissingPermission")
    public void organizarMapa() {
        myMap.setMyLocationEnabled(true);
        myMap.getUiSettings().setMyLocationButtonEnabled(true);
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);
        myMap.getUiSettings().setRotateGesturesEnabled(true);
        myMap.getUiSettings().setTiltGesturesEnabled(true);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d("MAPA", "Latitud LOCATIONMANAGER" + latLng);

        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        //Agregar un marcador en el Local
        ubicacionLocal = crearUbicacion(latLng);
        myMap.addMarker(new MarkerOptions().position(ubicacionLocal).title("Local de Comidas"));


        myMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
               if(linea!=null && polyline!=null){
                   polyline.remove();}

                direccionLatitud = latLng;
                Log.d("MAPA", "Latitud dentro de Listener:" + direccionLatitud);


                //DIBUJAR RUTA
                linea = new PolylineOptions().add(direccionLatitud).add(ubicacionLocal).color(0x66FF0000);
                polyline = myMap.addPolyline(linea); 
                

            }

        });


        btnConfirmarDireccion = findViewById(R.id.button_ConfirmarDireccion_id);
        btnConfirmarDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(direccionLatitud == null)) {
                    Log.d("MAPA", "Esta es la direccion luego del boton confirmar :" + direccionLatitud);
                    Intent i = new Intent();
                    i.putExtra("Ubicacion", direccionLatitud);
                    MapaActivity.this.setResult(Activity.RESULT_OK, i);
                    MapaActivity.this.finish();
                }
            }
        });

    }


    public LatLng crearUbicacion(LatLng posicionOriginal) {
        Random r = new Random();

        // Una direccion aleatoria de 0 a 359 grados
        int direccionRandomEnGrados = r.nextInt(360);

        // Una distancia aleatoria de 100 a 1000 metros
        int distanciaMinima = 100;
        int distanciaMaxima = 1000;
        int distanciaRandomEnMetros = r.nextInt(distanciaMaxima - distanciaMinima) + distanciaMinima;


        LatLng nuevaPosicion = SphericalUtil.computeOffset(
                posicionOriginal,
                distanciaRandomEnMetros,
                direccionRandomEnGrados);

        return nuevaPosicion;
    }

}