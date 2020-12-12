package com.example.listview;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private ListView listlng;

    List<String> pais = new ArrayList<>();
    List<String> latlng = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.listView);



        String url ="https://restcountries.eu/rest/v2/lang/pt";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                               // Pais country = new Pais();
                                pais.add(object.getString("name"));
                                latlng.add(object.getString("latlng"));

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, pais);
                                ArrayAdapter<String> point = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, latlng);

                                lista.setAdapter(adapter);




                            }catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Erro " + error.getMessage(),Toast.LENGTH_LONG);

                    }


                });



            MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);


        }

    @Override
    protected void onResume() {
        super.onResume();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String lat = latlng.get(i);
                Toast.makeText(
                    getApplicationContext(),
                     lat,
                    Toast.LENGTH_SHORT).show();

            }
        });
    }
}
