package com.example.juan.trabalhodiadesafio.service;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GrupoService {

    @GET("infousuarios")
    Call<List<Grupo>> getAll();

    @GET("infousuario/{id}")
    Call<Grupo> getOne(@Path("id") Long id);

    @GET("infousuariofacebook/{id}")
    Call<Grupo> getOneByIdFacebook(@Path("id") String id);

    @POST("infousuario")
    Call<Void> insert(@Body Grupo grupo);

    @PUT("infousuario/")
    Call<Void> update(@Body Grupo grupo);

    @DELETE("infousuario/{id}")
    Call<Void> delete(@Path("id") Long id);
}
