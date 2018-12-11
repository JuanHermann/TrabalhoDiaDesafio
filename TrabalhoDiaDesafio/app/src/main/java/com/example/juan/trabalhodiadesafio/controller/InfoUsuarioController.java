package com.example.juan.trabalhodiadesafio.controller;

import android.os.StrictMode;

import com.example.juan.trabalhodiadesafio.model.InfoUsuario;
import com.example.juan.trabalhodiadesafio.service.InfoUsuarioService;
import com.example.juan.trabalhodiadesafio.service.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class InfoUsuarioController {

    private InfoUsuarioService infoUsuarioService;

    public InfoUsuarioController() {
        infoUsuarioService = ServiceGenerator.createService(InfoUsuarioService.class);
    }

    public List<InfoUsuario> getAll() {
        List<InfoUsuario> lista = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            Call<List<InfoUsuario>> call;

            call = infoUsuarioService.getAll();

            lista = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<InfoUsuario> getByGrupo(Long idGrupo) {
        List<InfoUsuario> lista = getAll();
        List<InfoUsuario> listaByGrupo = new ArrayList<>();

        if (lista != null) {
            for (InfoUsuario usuario : lista) {
                if (usuario.getIdgrupo().equals(idGrupo)) {
                    listaByGrupo.add(usuario);
                }
            }
        }

        return listaByGrupo;
    }

    public InfoUsuario getByIdUsuario(String idUsuario) {
        try {
            List<InfoUsuario> usuarios = getAll();

            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getIdusuario().equals(idUsuario)) {
                    return usuarios.get(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(InfoUsuario infoUsuario) throws IOException {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Call<Void> call = infoUsuarioService.update(infoUsuario.getIdusuario(), infoUsuario);
        call.execute().body();
    }

    public void insert(InfoUsuario infoUsuario) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

}
