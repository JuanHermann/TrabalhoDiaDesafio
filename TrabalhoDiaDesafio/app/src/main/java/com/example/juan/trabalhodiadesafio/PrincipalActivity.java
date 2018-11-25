package com.example.juan.trabalhodiadesafio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class PrincipalActivity extends AppCompatActivity   {


    private static final String PREF_NAME = "pref";
    private Integer idUsuario = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        pegarIdUsuario();
        verificaUsuarioLogado();
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



}
