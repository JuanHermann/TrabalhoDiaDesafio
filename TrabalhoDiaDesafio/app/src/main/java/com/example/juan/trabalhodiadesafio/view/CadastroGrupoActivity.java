package com.example.juan.trabalhodiadesafio.view;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juan.trabalhodiadesafio.R;
import com.example.juan.trabalhodiadesafio.model.Grupo;
import com.example.juan.trabalhodiadesafio.service.GrupoService;
import com.example.juan.trabalhodiadesafio.service.ServiceGenerator;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;

@EActivity(R.layout.activity_cadastro_grupo)
public class CadastroGrupoActivity extends AppCompatActivity {

    @ViewById
    EditText edtDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.criar_grupo));
    }

    @Click(R.id.btnSalvar)
    void clickSalvar() {
        Grupo grupo = new Grupo();

        if (!edtDescricao.getText().toString().isEmpty()) {
            grupo.setDescricao(edtDescricao.getText().toString());

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            try {
                GrupoService grupoService = ServiceGenerator.createService(GrupoService.class);

                Call<Void> call;

                call = grupoService.insert(grupo);

                call.execute().body();

                Toast.makeText(this, "Registro salvo com sucesso!", Toast.LENGTH_SHORT).show();

                finish();
            } catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }
        } else {
            Toast.makeText(this, "O campo DESCRIÇÃO é de preenchimento obrigatório.", Toast.LENGTH_SHORT).show();
        }
    }

}
