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

@EActivity(R.layout.activity_listar)
@OptionsMenu(R.menu.menu_listar)
public class ListarActivity extends AppCompatActivity {

    @ViewById
    ListView lvRegistros;
    private List<JSONObject> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.ranking));
    }

    @AfterViews
    void init() {
        String urlUser = "http://192.168.2.14:8081/api/infousuario/2"; //URL find by id
        String urlAll = "http://192.168.2.14:8081/api/infousuarios"; //URL listar todos

        ListarUsuarios(urlAll);
    }

    @OptionsItem(R.id.mnuGrupos)
    void clickGrupos() {
        startActivity(new Intent(this, GruposActivity_.class));
    }

    public void ListarUsuarios(String url) {
        //Inicio chamar servidor
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(this).getRequestQueue(); //parada pra chamar banco

        JsonArrayRequest objectRequest = new JsonArrayRequest( //metodo pra pega a lista do banco e salvar em array json
                Request.Method.GET, url, null, //passa o metodo (GET), a url do servidor, um jsonRequest(null), o listener (response) e o listener error
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("rest response", response.toString()); //response é o conteudo
                        int i = 0;
                        lista = new ArrayList<>(response.length()); //dentro do response eu inicializo uma lista com o mesmo tamanho do response
                        while (i < response.length()) { // adiciono todos os registros do banco na lista em JsonObject
                            try {
                                lista.add(i, response.getJSONObject(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            i++;
                        }
                        printaTudo();//chamo o metodo pra printar ver se funcionou e ja envia para o adapter
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("rest response", error.toString());
                    }
                }
        );

        /*VVVVVV COMECA COM ESSE REQUEST QUEUE VVVVVVV
        * */
        requestQueue.add(objectRequest); //chama o metodo de cima
    }

    public void printaTudo() {
        for (int x = 0; x < lista.size(); x++) {
            System.out.println("=== // === // ==="); //printa ver se funcionou
            System.out.println(lista.get(x).toString());
            try {
                System.out.println(lista.get(x).getString("nome"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //chama adapter
        Adapter adapter = new Adapter(this, lista); //inicia adapter

        lvRegistros.setAdapter(adapter);
    }

}
