package com.example.juan.trabalhodiadesafio.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.juan.trabalhodiadesafio.R;
import com.example.juan.trabalhodiadesafio.model.InfoUsuario;
import com.example.juan.trabalhodiadesafio.service.InfoUsuarioService;
import com.example.juan.trabalhodiadesafio.service.ServiceGenerator;
import com.example.juan.trabalhodiadesafio.utils.GPSUtil;
import com.example.juan.trabalhodiadesafio.utils.SensorUtil;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

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
    Button btnEncerrarMonit;

    @ViewById
    ImageButton btnAtualizar;

    private SensorUtil sensorUtil;

    private Date horarioInicio;

    private Date horarioFim;

    private Double latitude;

    private Double longitude;

    private Float dadosAcelerometro;

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

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
//            System.out.println(verificaUsuario(usuarioId).getNome());
            Toast.makeText(getApplicationContext(), "Usuario logado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Usuario não está logado ", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity_.class));
            finish();
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

    @Click(R.id.btnLista)
    void clickLista() {
        startActivity(new Intent(this, ListarActivity_.class));
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

        edtInicio.setText(format.format(horarioInicio));
        edtFim.setText(format.format(horarioFim));
        layoutHorario.setVisibility(View.VISIBLE);

        btnAtualizar.setVisibility(View.GONE);
        btnIniciarMonit.setVisibility(View.VISIBLE);
    }

    public void gravarDados() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            InfoUsuario user = verificaUsuario(usuarioId);

            InfoUsuarioService infoUsuarioService = ServiceGenerator.createService(InfoUsuarioService.class);

            Call<Void> call;

            if (user != null) {
                user.setData(horarioFim);
                user.setLatitude(String.valueOf(latitude));
                user.setLongitude(String.valueOf(longitude));
                user.setDadosacelerometro(String.valueOf(dadosAcelerometro));

                call = infoUsuarioService.update(user);

                call.execute().body();

                Toast.makeText(this, "Registro salvo com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity_.class));
                finish();
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

}
