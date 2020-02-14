package com.example.googlemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask, AdapterView.OnItemClickListener {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.lvPaises);
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://www.geognos.com/api/en/countries/info/all.json", datos, MainActivity.this, MainActivity.this  );
        ws.execute();

        listView.setOnItemClickListener(this);
    }



    @Override
    public void processFinish(String result) throws JSONException {
        Log.i("processFinish",result);
        List<Pais> lpais = new ArrayList<>();
        JSONObject object = new JSONObject(result);
        JSONObject resultados = object.getJSONObject("Results");
        JSONArray paises = resultados.toJSONArray(resultados.names());


        for(int i=0; i<paises.length();i++){
            Pais pais = new Pais();
            JSONObject c=paises.getJSONObject(i);
            pais.setNombres(c.getString("Name"));

            JSONObject bandera=c.getJSONObject("CountryCodes");
            pais.setCodigoISO(bandera.getString("iso2"));
            pais.setImg(bandera.getString("iso2"));

            lpais.add(pais);

        }


        AdaptadorPaises adaptadorpaises = new AdaptadorPaises(this,lpais);
        listView.setAdapter(adaptadorpaises);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, MapaPais.class);
        Bundle b = new Bundle();
        b.putString("iso2", ((Pais)parent.getItemAtPosition(position)).getCodigoISO());
        intent.putExtras(b);
        startActivity(intent);
    }

}
