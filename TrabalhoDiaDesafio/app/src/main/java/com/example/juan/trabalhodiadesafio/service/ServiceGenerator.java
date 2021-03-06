package com.example.juan.trabalhodiadesafio.service;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String API_BASE_URL = "http://192.168.1.111:8081/api/";

    private static Retrofit retrofit;

    private static Retrofit.Builder builder =
            new Retrofit.Builder().baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()));

    public static <S> S createService(Class<S> serviceClass) {
        retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}