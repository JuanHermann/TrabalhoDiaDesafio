package com.example.juan.trabalhodiadesafio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.juan.trabalhodiadesafio.model.Usuario;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;


public class PrincipalActivity extends AppCompatActivity {

    private Usuario usuario;
    private String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        verificaUsuarioLogado();

        //Intent i = getIntent();
        //usuario = (Usuario)i.getSerializableExtra("usuario");

    }

    private void verificaUsuarioLogado() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn) {
            usuarioId =accessToken.getUserId();
            Toast.makeText(getApplicationContext(), "Usuario Logado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Usuario Não esta logado ", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public void btnLogoutOnClick(View view) {

        LoginManager.getInstance().logOut();
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void btnLista(View view) {
        startActivity(new Intent(this, ListarActivity.class));
    }

}
