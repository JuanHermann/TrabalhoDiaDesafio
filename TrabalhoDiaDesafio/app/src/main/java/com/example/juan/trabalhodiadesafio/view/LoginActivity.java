package com.example.juan.trabalhodiadesafio.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.juan.trabalhodiadesafio.R;
import com.example.juan.trabalhodiadesafio.model.InfoUsuario;
import com.example.juan.trabalhodiadesafio.model.Usuario;
import com.example.juan.trabalhodiadesafio.service.InfoUsuarioService;
import com.example.juan.trabalhodiadesafio.service.ServiceGenerator;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.androidannotations.annotations.EActivity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        executeGraphRequest(loginResult.getAccessToken().getUserId());
//                        Toast.makeText(getApplicationContext(), "Usuario Logado com Sucesso ", Toast.LENGTH_LONG).show();
                    }


                    @Override
                    public void onCancel() {
                        alertaCancel();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        alertaErro();
                        System.out.println(exception.toString());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void executeGraphRequest(final String userId) {
        usuario = new Usuario();

//        Toast.makeText(getApplicationContext(), "dados ", Toast.LENGTH_LONG).show();

        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(), userId, null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                usuario = new Gson().fromJson(response.getJSONObject().toString(), Usuario.class);

                mostrar();

                gravarDados();

                Intent intent = new Intent(LoginActivity.this, PrincipalActivity_.class);
                startActivity(intent);
            }
        });

        request.executeAsync();
    }

    public void gravarDados() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            InfoUsuario infoUsuario = new InfoUsuario();

            infoUsuario.setNome(usuario.getName());
            infoUsuario.setIdUsuario(String.valueOf(usuario.getId()));
            InfoUsuarioService infoUsuarioService = ServiceGenerator.createService(InfoUsuarioService.class);

            Call<Void> call;

            if (infoUsuario.getId() == null) {
                if (verificaUsuario(infoUsuario.getIdUsuario()) == null) {
                    call = infoUsuarioService.insert(infoUsuario);
                    call.execute().body();
                }
            }

//            Toast.makeText(this, "Registro salvo com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
//            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    public InfoUsuario verificaUsuario(String usuarioId) {
        InfoUsuarioService infoUsuarioService = ServiceGenerator.createService(InfoUsuarioService.class);

        Call<List<InfoUsuario>> call;
        call = infoUsuarioService.getAll();

        try {
            List<InfoUsuario> usuarios = call.execute().body();

            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getIdUsuario().equals(usuarioId)) {
                    return usuarios.get(i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void mostrar() {
        System.out.println("=== // === // ===");
        System.out.println("ID = " + usuario.getId());
        System.out.println("Nome = " + usuario.getName());
        System.out.println("=== // === // ===");
    }

    private void alertaErro() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        alerta.setTitle("Erro");
        alerta.setMessage("Não foi possível realizar o login");
        alerta.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alerta.show();
    }

    private void alertaCancel() {
        Toast.makeText(this, "Login com o Facebook cancelado.", Toast.LENGTH_SHORT).show();
    }

}
