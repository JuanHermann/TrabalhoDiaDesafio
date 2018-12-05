package com.example.juan.trabalhodiadesafio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.juan.trabalhodiadesafio.utils.GPSUtil;
import com.example.juan.trabalhodiadesafio.utils.SensorUtil;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
    @EActivity
    Indica que a activity vai usar Android Annotations e já seta o layout que será
    utilizado pela mesma através do ID do layout em questão passado como parâmetro.
 */
@EActivity(R.layout.activity_principal)
/*
    @OptionsMenu
    Informa o layout do menu (da toolbar) que será utilizado pela activity.
 */
@OptionsMenu(R.menu.menu_principal)
public class PrincipalActivity extends AppCompatActivity {

    /*
        @ViewById
        Injeta a view correspondente.
        Elimina a necessidade de inicializar os componentes com "x = findViewById(...)".
        O nome do componente tem que ser exatamente igual ao ID correspondente no layout.
        O componente não pode ser private.
     */
    @ViewById
    LinearLayout layoutTaxa;

    @ViewById
    LinearLayout layoutHorario;

    @ViewById
    EditText edtTaxa;

    @ViewById
    EditText edtInicio;

    @ViewById
    EditText edtFim;

    @ViewById
    Button btnIniciarMonit;

    @ViewById
    Button btnPausarMonit;

    @ViewById
    Button btnRetomarMonit;

    @ViewById
    Button btnEncerrarMonit;

    @ViewById
    ImageButton btnAtualizar;

    private static final String PREF_NAME = "pref";

    private Integer idUsuario = -1;

    private SensorUtil sensorUtil;

    private Date horarioInicio;

    private Date horarioFim;

    private Double latitude;

    private Double longitude;

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //verificaUsuarioLogado();
    }

    private void pegarIdUsuario() {
        SharedPreferences settings = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        idUsuario = settings.getInt("idusuario", -1);
    }

    private void verificaUsuarioLogado() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn) {
            Toast.makeText(getApplicationContext(), "Usuario Logado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Usuario Não esta logado " + idUsuario, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    /*
        @OptionsItem
        Indica que o método anotado deve ser acionado quando o item de menu passado como parâmetro
        for clicado.
     */
    @OptionsItem(R.id.mnuLogout)
    void clickLogout() {
        LoginManager.getInstance().logOut();
        /*
            Em todas as Intents, o nome da classe deve estar seguido de "_" para indicar que a
            classe em questão é a gerada pelo Android Annotations. No Manifest as classes devem
            estar nomeadas assim também.
         */
        startActivity(new Intent(this, LoginActivity_.class));
    }

    /*
        @Click
        Indica que o método anotado deve ser acionado quando a view passada como parâmetro for
        clicada. Os métodos anotados não podem ser private.
     */
    @Click(R.id.btnIniciarMonit)
    void clickIniciatMonit() {
        btnIniciarMonit.setVisibility(View.GONE);
        layoutHorario.setVisibility(View.GONE);

        horarioInicio = Calendar.getInstance().getTime();

        if (sensorUtil == null) {
            sensorUtil = new SensorUtil(this);
        }

        layoutTaxa.setVisibility(View.VISIBLE);

        clickAtualizar();

        btnAtualizar.setVisibility(View.VISIBLE);
        btnPausarMonit.setVisibility(View.VISIBLE);
        btnEncerrarMonit.setVisibility(View.VISIBLE);
    }

    @Click(R.id.btnAtualizar)
    void clickAtualizar() {
        btnAtualizar.setEnabled(false);
        edtTaxa.setText(String.valueOf(sensorUtil.getVariacao()));
        btnAtualizar.setEnabled(true);
    }

    @Click(R.id.btnPausarMonit)
    void clickPausarMonit() {
        btnPausarMonit.setVisibility(View.GONE);
        sensorUtil.pausarMonitoramento();
        btnRetomarMonit.setVisibility(View.VISIBLE);
    }

    @Click(R.id.btnRetomarMonit)
    void clickRetomarMonit() {
        btnRetomarMonit.setVisibility(View.GONE);
        sensorUtil.retomarMonitoramento();
        btnPausarMonit.setVisibility(View.VISIBLE);
    }

    @Click(R.id.btnEncerrarMonit)
    void clickEncerrarMonit() {
        btnEncerrarMonit.setVisibility(View.GONE);

        clickAtualizar();

        horarioFim = Calendar.getInstance().getTime();

        sensorUtil.encerrarMonitoramento();
        sensorUtil = null;

        GPSUtil gpsUtil = new GPSUtil(this);

        latitude = gpsUtil.getLatitude();
        longitude = gpsUtil.getLongitude();

        edtInicio.setText(format.format(horarioInicio));
        edtFim.setText(format.format(horarioFim));
        layoutHorario.setVisibility(View.VISIBLE);

        btnAtualizar.setVisibility(View.GONE);
        btnPausarMonit.setVisibility(View.GONE);
        btnRetomarMonit.setVisibility(View.GONE);
        btnIniciarMonit.setVisibility(View.VISIBLE);
    }

    @Click(R.id.btnLista)
    void clickLista() {
        startActivity(new Intent(this, ListarActivity_.class));
    }

}
