package com.es.reportverse.model;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
public class Universitario extends Usuario{

    public Universitario(String nome, String email, String senha) {
        super(nome, email, senha);
    }

}