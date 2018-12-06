package com.example.juan.trabalhodiadesafio.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.juan.trabalhodiadesafio.R;
import com.example.juan.trabalhodiadesafio.SingletonRequestQueue;
import com.example.juan.trabalhodiadesafio.adapter.Adapter;
import com.example.juan.trabalhodiadesafio.adapter.AdapterGrupo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_grupos)
@OptionsMenu(R.menu.menu_grupos)
public class GruposActivity extends AppCompatActivity {

    @ViewById
    ListView lvGrupos;
    private List<JSONObject> lista;
    private List<JSONObject> listaPessoa;
    private List<Integer> listaExercicio;
    private String urlPessoa = "http://192.168.2.14:8081/api/infousuarios";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.grupos));
    }

    @AfterViews
    void init() {
        String urlUser = "http://192.168.2.14:8081/api/infousuario/2"; //URL find by id
        String urlAll = "http://192.168.2.14:8081/api/grupos"; //URL listar todos


        ListarGrupo(urlAll);
    }

//    @OptionsItem(R.id.mnuGrupos)
//    void clickGrupos() {
//        startActivity(new Intent(this, GruposActivity_.class));
//    }

    public void ListarGrupo(final String url) {
        //Inicio chamar servidor
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(this).getRequestQueue();

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("rest response", response.toString());
                        int i = 0;
                        lista = new ArrayList<>(response.length());
                        while (i < response.length()) {
                            try {
                                lista.add(i, response.getJSONObject(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            i++;
                        }
                        pegaPessoa(urlPessoa);
//                        printaTudo();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("rest response", error.toString());
                    }
                }
        );


        requestQueue.add(objectRequest);
    }

    public void pegaPessoa(String url) {
        //Inicio chamar servidor
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(this).getRequestQueue();

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("rest response", response.toString());
                        int i = 0;
                        listaPessoa = new ArrayList<>(response.length());
                        while (i < response.length()) {
                            try {
                                listaPessoa.add(i, response.getJSONObject(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            i++;
                        }
                        fazLista();//chamo o metodo pra printar ver se funcionou e ja envia para o adapter
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("rest response", error.toString());
                    }
                }
        );


        requestQueue.add(objectRequest); //chama o metodo de cima
    }

    public void fazLista(){
        listaExercicio  = new ArrayList<>(lista.size());
        int x = 0;
        for(int j = 0; j < lista.size(); j++){
            listaExercicio.add(x, 0);
            for (int i = 0; i < listaPessoa.size(); i++){
                try {
                    if(listaPessoa.get(i).getInt("idgrupo") == lista.get(x).getInt("idgrupo")){
                        listaExercicio.add(x, Integer.parseInt(listaPessoa.get(i).getString("dadosacelerometro"))+listaExercicio.get(x));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            x++;

        }

        printaTudo();

    }

    public void printaTudo() {
//        for (int x = 0; x < lista.size(); x++) {
//            System.out.println("=== // === // ==="); //printa ver se funcionou
//            System.out.println(lista.get(x).toString());
//            try {
//                System.out.println(lista.get(x).getString("nome"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        //chama adapter
        AdapterGrupo adapter = new AdapterGrupo(this, lista, listaExercicio); //inicia adapter

        lvGrupos.setAdapter(adapter);
    }

}
