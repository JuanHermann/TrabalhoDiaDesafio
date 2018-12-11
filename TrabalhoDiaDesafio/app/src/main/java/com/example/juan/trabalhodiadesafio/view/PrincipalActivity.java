package com.example.juan.trabalhodiadesafio.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.juan.trabalhodiadesafio.R;
import com.example.juan.trabalhodiadesafio.controller.InfoUsuarioController;
import com.example.juan.trabalhodiadesafio.model.InfoUsuario;
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

@EActivity(R.layout.activity_principal)
@OptionsMenu(R.menu.menu_principal)
public class PrincipalActivity extends AppCompatActivity {

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
    Button btnEncerrarMonit;

    @ViewById
    ImageButton btnAtualizar;

    private SensorUtil sensorUtil;

    private Date horarioInicio;

    private Date horarioFim;

    private Double latitude;

    private Double longitude;

    private Float dadosAcelerometro;

    private String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        verificaUsuarioLogado();
    }

    private void verificaUsuarioLogado() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn) {
            idUsuario = accessToken.getUserId();
        } else {
            startActivity(new Intent(this, LoginActivity_.class));
            finish();
        }
    }

    @OptionsItem(R.id.mnuLogout)
    void clickLogout() {
        LoginManager.getInstance().logOut();
        startActivity(new Intent(this, LoginActivity_.class));
    }

    @Click(R.id.btnLista)
    void clickLista() {
        startActivity(new Intent(this, RankingActivity_.class));
    }

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
        btnEncerrarMonit.setVisibility(View.VISIBLE);
    }

    @Click(R.id.btnAtualizar)
    void clickAtualizar() {
        btnAtualizar.setEnabled(false);
        dadosAcelerometro = sensorUtil.getVariacao();
        edtTaxa.setText(String.valueOf(dadosAcelerometro));
        btnAtualizar.setEnabled(true);
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

        gravarDados();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        edtInicio.setText(format.format(horarioInicio));
        edtFim.setText(format.format(horarioFim));
        layoutHorario.setVisibility(View.VISIBLE);

        btnAtualizar.setVisibility(View.GONE);
        btnIniciarMonit.setVisibility(View.VISIBLE);
    }

    public void gravarDados() {
        InfoUsuarioController infoUsuarioController = new InfoUsuarioController();

        try {
            InfoUsuario infoUsuario = infoUsuarioController.getByIdUsuario(idUsuario);

            if (infoUsuario != null) {
                infoUsuario.setDatainclusao(horarioFim);
                infoUsuario.setLatitude(String.valueOf(latitude.toString()));
                infoUsuario.setLongitude(String.valueOf(longitude));
                infoUsuario.setDadosacelerometro(String.valueOf(dadosAcelerometro));

                infoUsuarioController.update(infoUsuario);
            } else {
                Toast.makeText(this, R.string.err_usuario_not_found, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity_.class));
                finish();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
