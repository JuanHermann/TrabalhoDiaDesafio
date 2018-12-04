package com.example.juan.trabalhodiadesafio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan.trabalhodiadesafio.model.Usuario;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        executeGraphRequest(loginResult.getAccessToken().getUserId());

                        login();
                        Toast.makeText(getApplicationContext(), "Usuario Logado com Sucesso ", Toast.LENGTH_LONG).show();
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

    public void login() {
        Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
    }

    private void executeGraphRequest(final String userId) {
        usuario = new Usuario();
        Toast.makeText(getApplicationContext(), "dados ", Toast.LENGTH_LONG).show();
        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(), userId, null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {

                usuario = new Gson().fromJson(response.getJSONObject().toString(), Usuario.class);
                mostrar();
                Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
        request.executeAsync();


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
        Toast.makeText(this, "Login com o Facebook Cancelado", Toast.LENGTH_SHORT).show();
    }

}
