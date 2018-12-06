package com.example.juan.trabalhodiadesafio.service;

import com.example.juan.trabalhodiadesafio.model.InfoUsuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InfoUsuarioService {

    @GET("infousuarios")
    Call<List<InfoUsuario>> getAll();

    @GET("infousuario/{id}")
    Call<InfoUsuario> getOne(@Path("id") Long id);

    @GET("infousuariofacebook/{id}")
    Call<InfoUsuario> getOneByIdFacebook(@Path("id") String id);

    @POST("infousuario")
    Call<Void> insert(@Body InfoUsuario infoUsuario);

    @PUT("infousuario/{id}")
    Call<Void> update(@Path("id") String id, @Body InfoUsuario infoUsuario);

    @DELETE("infousuario/{id}")
    Call<Void> delete(@Path("id") Long id);

}

