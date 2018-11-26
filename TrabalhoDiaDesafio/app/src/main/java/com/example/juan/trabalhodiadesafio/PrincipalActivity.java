package com.example.juan.trabalhodiadesafio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PrincipalActivity extends AppCompatActivity   {


    private static final String PREF_NAME = "pref";
    private Integer idUsuario = -1;
    List<JSONObject> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        pegarIdUsuario();
        verificaUsuarioLogado();

        String urlUser = "http://192.168.2.14:8081/api/infousuario/2"; //mudar id user
        String urlAll = "http://192.168.2.14:8081/api/infousuarios"; //IP!!

        lista = ListarUsuarios(urlAll);

    }

    private void pegarIdUsuario() {
        SharedPreferences settings = this.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        idUsuario = settings.getInt("idusuario",-1);
    }

    private void verificaUsuarioLogado() {
        if(idUsuario == -1){
            Intent intent = new Intent(PrincipalActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), "Usuario Não esta logado "+idUsuario, Toast.LENGTH_LONG).show();

        }else {

            Toast.makeText(getApplicationContext(), "Usuario  logado " + idUsuario, Toast.LENGTH_LONG).show();
        }
    }

    public void btnLogoutOnClick(View view) {
        SharedPreferences settings = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("idusuario", -1);
        editor.commit();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
        pegarIdUsuario();
        Toast.makeText(getApplicationContext(), "Usuario Não esta logado "+idUsuario, Toast.LENGTH_LONG).show();
    }


    public List<JSONObject> ListarUsuarios(String url){
        //Inicio chamar servidor

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("rest response", response.toString()); //response é o conteudo
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

                        try {

                            for (int x = 0; x < lista.size(); x++){
                                System.out.println("===============");
                                System.out.println(lista.get(x).toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
        return lista;
    }

}
