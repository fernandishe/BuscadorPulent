package com.fernandishe.buscadormusica;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.fernandishe.buscadormusica.fun.ResponseServicios;


import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Canciones> canciones;
    private EditText etSearchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView audioRv = findViewById(R.id.audio_rv);
        etSearchBox = findViewById(R.id.etSearchBox);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        audioRv.setLayoutManager(linearLayoutManager);
        if(populatePersons())
        {
            final ListAdapter listAdapter = new ListAdapter(canciones);
            audioRv.setAdapter(listAdapter);
            etSearchBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    listAdapter.getFilter().filter(s.toString());
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Boolean populatePersons()
    {
        boolean reto =false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        String responseServicio;
        if (networkInfo != null && networkInfo.isConnected()) {
            responseServicio = ResponseServicios.retoResponse("itunes",
                    getBaseContext(), "EndPoint.properties", null, "GET");
            if(!responseServicio.equals("")) {
                try {
                    ObjectMapper mapper = new ObjectMapper();

                    JSONObject json = new JSONObject(responseServicio);
                    JSONArray array = json.getJSONArray("results");


                    canciones = new ArrayList<>();
                    for(int x=0;x<array.length();x++)
                    {
                        JSONObject jsonObject = array.getJSONObject(x);

                        Results results = mapper.readValue(jsonObject.toString(), Results.class);
                        List listaDetalle = new ArrayList();
                        listaDetalle.add(0, results.getArtworkUrl100());
                        listaDetalle.add(1, results.getCollectionName() + " - " + results.getArtistName());
                        listaDetalle.add(2, results.getPreviewUrl());
                        listaDetalle.add(3, results.getCollectionViewUrl());

                        canciones.add(new Canciones(results.getArtistName(), results.getCollectionCensoredName(),
                                results.getArtworkUrl60(), listaDetalle, this));
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                }catch (Exception ex)
                {
                    ex.getStackTrace();
                }
                reto = true;
            }
        } else {
            Toast.makeText(getApplicationContext(),"No hay conexión a Internet, intente mas tarde", Toast.LENGTH_LONG).show();
            // Acá deberia ir a buscar a la base local, no alcance! sorry =(
        }
        return reto;
    }
}
