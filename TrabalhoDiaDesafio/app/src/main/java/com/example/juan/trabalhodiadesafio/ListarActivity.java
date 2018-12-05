package com.example.juan.trabalhodiadesafio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListarActivity extends AppCompatActivity {

    private ListView lvRegistros;
    private List<JSONObject> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        lvRegistros = (ListView) findViewById(R.id.lvRegistros);

        String urlUser = "http://192.168.2.14:8081/api/infousuario/2"; //URL find by id
        String urlAll = "http://172.30.14.191:8081/api/infousuarios"; //URL listar todos

        ListarUsuarios(urlAll);

    }
    public void ListarUsuarios(String url){
        //Inicio chamar servidor
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("rest response", response.toString());
                        int i = 0;
                        lista = new ArrayList<>(response.length());
                        while (i < response.length()){
                            try {
                                lista.add(i, response.getJSONObject(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            i++;
                        }
                        printaTudo();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("rest response", error.toString());
                    }
                }
        );

        requestQueue.add(objectRequest); //chama o metodo de cima
        System.out.println("teste222");
//        objectRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public void printaTudo(){
        for (int x = 0; x < lista.size(); x++){
            System.out.println("=== // === // ==="); //printa ver se funcionou
            System.out.println(lista.get(x).toString());
            try {
                System.out.println(lista.get(x).getString("nome"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //chama adapter
        Adapter adapter = new Adapter(this, lista, 1); //inicia adapter

        lvRegistros.setAdapter(adapter);
    }
}
