package com.example.juan.trabalhodiadesafio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;


public class PrincipalActivity extends AppCompatActivity {


    private static final String PREF_NAME = "pref";
    private Integer idUsuario = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //verificaUsuarioLogado();

    }

    private void pegarIdUsuario() {
        SharedPreferences settings = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        idUsuario = settings.getInt("idusuario", -1);
    }

    private void verificaUsuarioLogado() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if(isLoggedIn){
            Toast.makeText(getApplicationContext(), "Usuario Logado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Usuario Não esta logado " + idUsuario, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void btnLogoutOnClick(View view) {
        LoginManager.getInstance().logOut();
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void btnLista(View view) {
        startActivity(new Intent(this, ListarActivity.class));
    }

    public void btnGrupo(View view) {
        startActivity(new Intent(this, ListarGruposActivity.class));
    }
}
