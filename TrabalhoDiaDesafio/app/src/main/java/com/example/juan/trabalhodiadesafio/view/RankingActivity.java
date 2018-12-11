package com.example.juan.trabalhodiadesafio.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.juan.trabalhodiadesafio.R;
import com.example.juan.trabalhodiadesafio.adapter.RankingAdapter;
import com.example.juan.trabalhodiadesafio.controller.InfoUsuarioController;
import com.example.juan.trabalhodiadesafio.model.Grupo;
import com.example.juan.trabalhodiadesafio.model.InfoUsuario;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_ranking)
@OptionsMenu(R.menu.menu_listar)
public class RankingActivity extends AppCompatActivity {

    @ViewById
    ListView lvRegistros;

    @Extra
    Grupo grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.ranking));
    }

    @AfterViews
    void init() {
        InfoUsuarioController infoUsuarioController = new InfoUsuarioController();

        List<InfoUsuario> lista = infoUsuarioController.getAll();

        if (grupo != null) {
            lista = infoUsuarioController.getByGrupo(grupo.getIdgrupo());
        }

        RankingAdapter adapter = new RankingAdapter(this, lista);
        lvRegistros.setAdapter(adapter);
    }

    @OptionsItem(R.id.mnuGrupos)
    void clickGrupos() {
        startActivity(new Intent(this, GruposActivity_.class));
        finish();
    }

}
