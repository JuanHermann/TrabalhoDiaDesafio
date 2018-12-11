package com.example.juan.trabalhodiadesafio.model;

import java.util.Date;

public class InfoUsuario {

    private Long id;
    private String idusuario;
    private String nome;
    private Date datainclusao;
    private String latitude;
    private String longitude;
    private String dadosacelerometro;
    private Long idgrupo;

    public InfoUsuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDatainclusao() {
        return datainclusao;
    }

    public void setDatainclusao(Date datainclusao) {
        this.datainclusao = datainclusao;
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

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public Long getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(Long idgrupo) {
        this.idgrupo = idgrupo;
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
