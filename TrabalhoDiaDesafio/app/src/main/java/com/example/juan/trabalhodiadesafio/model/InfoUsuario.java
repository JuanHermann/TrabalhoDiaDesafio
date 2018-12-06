package com.example.juan.trabalhodiadesafio.model;

import java.util.Date;

public class InfoUsuario {

    private Long id;
    private String idusuario;
    private String nome;
    private Date data;
    private String latitude;
    private String longitude;
    private String dadosacelerometro;

    public InfoUsuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idusuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idusuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDadosacelerometro() {
        return dadosacelerometro;
    }

    public void setDadosacelerometro(String dadosacelerometro) {
        this.dadosacelerometro = dadosacelerometro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfoUsuario infoUsuario = (InfoUsuario) o;

        return id.equals(infoUsuario.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
