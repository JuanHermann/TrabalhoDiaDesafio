package com.example.juan.trabalhodiadesafio.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.juan.trabalhodiadesafio.R;
import com.example.juan.trabalhodiadesafio.adapter.GrupoAdapter;
import com.example.juan.trabalhodiadesafio.controller.GrupoController;
import com.example.juan.trabalhodiadesafio.controller.InfoUsuarioController;
import com.example.juan.trabalhodiadesafio.model.Grupo;
import com.example.juan.trabalhodiadesafio.model.InfoUsuario;
import com.facebook.AccessToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemLongClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_grupos)
@OptionsMenu(R.menu.menu_grupos)
public class GruposActivity extends AppCompatActivity {

    @ViewById
    ListView lvGrupos;

    private Grupo grupo;

    private String idUsuario;

    private InfoUsuario infoUsuario;

    @OptionsMenuItem
    MenuItem mnuEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.grupos));
    }

    @AfterViews
    void init() {
        GrupoController grupoController = new GrupoController();

        List<Grupo> lista = grupoController.getAll();

        GrupoAdapter grupoAdapter = new GrupoAdapter(this, lista);

        lvGrupos.setAdapter(grupoAdapter);

        registerForContextMenu(lvGrupos);

        verificaUsuarioLogado();
    }

    @OptionsItem(R.id.mnuCriarGrupo)
    void clickCriarGrupo() {
        startActivity(new Intent(this, CadastroGrupoActivity_.class));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu_grupos, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuVerRanking:
                clickVerRanking();
                break;

            case R.id.mnuEntrar:
                clickEntrar();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @ItemLongClick(R.id.lvGrupos)
    Boolean clickLista(Grupo grupoSelecionado) {
        grupo = grupoSelecionado;

        if (grupo.getIdgrupo().equals(infoUsuario.getIdgrupo())) {
            mnuEntrar.setTitle(R.string.sair_do_grupo);
        } else {
            mnuEntrar.setTitle(R.string.entrar_no_grupo);
        }

        return false;
    }

    private void clickEntrar() {
        if (grupo.getIdgrupo().equals(infoUsuario.getIdgrupo())) {
            infoUsuario.setIdgrupo(null);
        } else {
            infoUsuario.setIdgrupo(grupo.getIdgrupo());
        }

        gravarDados();
    }

    private void clickVerRanking() {
        Intent intent = new Intent(GruposActivity.this, RankingActivity_.class);
        intent.putExtra("grupo", grupo);
        startActivity(intent);
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

        infoUsuario = new InfoUsuarioController().getByIdUsuario(idUsuario);
    }

    public void gravarDados() {
        InfoUsuarioController infoUsuarioController = new InfoUsuarioController();

        try {
            if (infoUsuario != null) {
                infoUsuarioController.update(infoUsuario);
                Toast.makeText(this, "Você entrou para o grupo " + grupo.getDescricao() + "!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.err_usuario_not_found, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity_.class));
                finish();
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

}