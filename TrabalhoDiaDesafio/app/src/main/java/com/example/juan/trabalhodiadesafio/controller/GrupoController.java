package com.example.juan.trabalhodiadesafio.controller;

import android.os.StrictMode;

import com.example.juan.trabalhodiadesafio.model.Grupo;
import com.example.juan.trabalhodiadesafio.service.GrupoService;
import com.example.juan.trabalhodiadesafio.service.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class GrupoController {

    private GrupoService grupoService;

    public GrupoController() {
        grupoService = ServiceGenerator.createService(GrupoService.class);
    }

    public List<Grupo> getAll() {
        List<Grupo> lista = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            Call<List<Grupo>> call;

            call = grupoService.getAll();

            lista = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insert(Grupo grupo) throws IOException {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Call<Void> call = grupoService.insert(grupo);
        call.execute().body();
    }

}
