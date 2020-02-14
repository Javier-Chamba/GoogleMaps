package com.example.googlemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MapaPais extends AppCompatActivity implements Asynchtask, OnMapReadyCallback{

    private ImageView ivbandera;
    private TextView tvpais;

    GoogleMap mapa;
    String oeste, este, norte, sur;
    Double latitud, longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_pais);

        ivbandera = findViewById(R.id.ivBandera);
        tvpais = findViewById(R.id.tvPais);

        Bundle bundle = this.getIntent().getExtras();
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://www.geognos.com/api/en/countries/info/"+bundle.getString("iso2")+".json", datos, MapaPais.this, MapaPais.this  );
        ws.execute();
        Glide.with(this).load("http://www.geognos.com/api/en/countries/flag/"+bundle.getString("iso2")+".png").into(ivbandera);
    }



    @Override
    public void processFinish(String result) throws JSONException {
        Log.i("processFinish",result);
        JSONObject jsonObj= new JSONObject(result);
        JSONObject paises = jsonObj.getJSONObject("Results");
        tvpais.setText(paises.getString("Name"));
        JSONObject rectangulo = paises.getJSONObject("GeoRectangle");
        oeste = rectangulo.getString("West");
        este = rectangulo.getString("East");
        norte = rectangulo.getString("North");
        sur = rectangulo.getString("South");
        JSONArray punto = paises.getJSONArray("GeoPt");
        latitud=punto.getDouble(0);
        longitud=punto.getDouble(1);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa=googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.getUiSettings().setZoomControlsEnabled(true);
        CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 4);
        mapa.moveCamera(camUpd1);
        LatLng punto = new LatLng(latitud, longitud);
        mapa.addMarker(new MarkerOptions().position(punto).title(String.valueOf(tvpais)));
        PolygonOptions figura = new PolygonOptions().add(
                new LatLng(Double.valueOf(norte),Double.valueOf(oeste)),
                new LatLng(Double.valueOf(sur),Double.valueOf(oeste)),
                new LatLng(Double.valueOf(sur),Double.valueOf(este)),
                new LatLng(Double.valueOf(norte),Double.valueOf(este)));
        figura.strokeWidth(10);
        figura.strokeColor(Color.RED);
        mapa.addPolygon(figura);
    }
}
