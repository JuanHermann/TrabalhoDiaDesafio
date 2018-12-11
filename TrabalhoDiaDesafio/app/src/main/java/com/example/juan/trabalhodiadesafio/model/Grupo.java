package com.example.juan.trabalhodiadesafio.model;

import java.io.Serializable;

public class Grupo implements Serializable {

    private Long idgrupo;
    private String descricao;

    public Long getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(Long idgrupo) {
        this.idgrupo = idgrupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
