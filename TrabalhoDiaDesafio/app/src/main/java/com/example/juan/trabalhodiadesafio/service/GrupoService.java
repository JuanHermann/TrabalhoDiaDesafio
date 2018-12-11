package com.example.juan.trabalhodiadesafio.service;


import com.example.juan.trabalhodiadesafio.model.Grupo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GrupoService {

    @GET("grupo")
    Call<List<Grupo>> getAll();

    @GET("grupo/{id}")
    Call<Grupo> getOne(@Path("id") Long id);

    @POST("grupo")
    Call<Void> insert(@Body Grupo grupo);

    @PUT("grupo/")
    Call<Void> update(@Body Grupo grupo);

    @DELETE("grupo/{id}")
    Call<Void> delete(@Path("id") Long id);

}
