package com.example.juan.trabalhodiadesafio.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juan.trabalhodiadesafio.R;
import com.example.juan.trabalhodiadesafio.controller.GrupoController;
import com.example.juan.trabalhodiadesafio.model.Grupo;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

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

            try {
                GrupoController grupoController = new GrupoController();

                grupoController.insert(grupo);

                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.campo_obrigatorio_descricao, Toast.LENGTH_SHORT).show();
        }
    }

}
