package com.example.juan.trabalhodiadesafio.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.juan.trabalhodiadesafio.R;
import com.example.juan.trabalhodiadesafio.controller.InfoUsuarioController;
import com.example.juan.trabalhodiadesafio.model.InfoUsuario;
import com.example.juan.trabalhodiadesafio.model.Usuario;
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

import java.util.List;

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

        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(), userId, null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                usuario = new Gson().fromJson(response.getJSONObject().toString(), Usuario.class);

                gravarDados();

                startActivity(new Intent(LoginActivity.this, PrincipalActivity_.class));
            }
        });

        request.executeAsync();
    }

    public void gravarDados() {
        try {
            InfoUsuario infoUsuario = new InfoUsuario();

            infoUsuario.setNome(usuario.getName());
            infoUsuario.setIdusuario(String.valueOf(usuario.getId()));

            if (infoUsuario.getId() == null) {
                if (verificaUsuario(infoUsuario.getIdusuario()) == null) {
                    new InfoUsuarioController().insert(infoUsuario);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public InfoUsuario verificaUsuario(String usuarioId) {
        try {
            List<InfoUsuario> usuarios = new InfoUsuarioController().getAll();

            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getIdusuario().equals(usuarioId)) {
                    return usuarios.get(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void alertaErro() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        alerta.setTitle(R.string.erro);
        alerta.setMessage(R.string.login_nao_realizado);
        alerta.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alerta.show();
    }

    private void alertaCancel() {
        Toast.makeText(this, R.string.login_cancelado, Toast.LENGTH_SHORT).show();
    }

}
