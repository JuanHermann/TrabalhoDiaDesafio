package com.example.juan.trabalhodiadesafio.view;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.juan.trabalhodiadesafio.R;
import com.example.juan.trabalhodiadesafio.model.InfoUsuario;
import com.example.juan.trabalhodiadesafio.model.Usuario;
import com.example.juan.trabalhodiadesafio.service.InfoUsuarioService;
import com.example.juan.trabalhodiadesafio.service.ServiceGenerator;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import retrofit2.Call;


public class PrincipalActivity extends AppCompatActivity {


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
            usuarioId = accessToken.getUserId();
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
        //startActivity(new Intent(this, ListarActivity.class));
        gravarDados();
    }

    public void gravarDados() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            InfoUsuario infoUsuario = new InfoUsuario();

            infoUsuario.setNome("Juan");
            infoUsuario.setIdUsuario(usuarioId);
            System.out.println("!" + usuarioId + "!");
            InfoUsuarioService infoUsuarioService = ServiceGenerator.createService(InfoUsuarioService.class);
            Call<Void> call;
            if (infoUsuario.getId() == null) {
                Call<InfoUsuario> callFace = infoUsuarioService.getOneByIdFacebook(infoUsuario.getIdUsuario());
                InfoUsuario user = callFace.execute().body();
                System.out.println(user.getIdUsuario());
                if (user == null) {
                    call = infoUsuarioService.insert(infoUsuario);
                    call.execute().body();
                }
            }

            Toast.makeText(this, "Registro salvo com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }
}
