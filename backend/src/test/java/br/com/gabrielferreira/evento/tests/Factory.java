package br.com.gabrielferreira.evento.tests;

import br.com.gabrielferreira.evento.entities.Cidade;

import java.util.ArrayList;
import java.util.List;

public class Factory {

    private Factory(){}

    public static List<Cidade> gerarCidades(){
        List<Cidade> cidades = new ArrayList<>();
        cidades.add(Cidade.builder().id(1L).nome("Manaus").codigo("MANAUS").build());
        cidades.add(Cidade.builder().id(2L).nome("Campinas").codigo("CAMPINAS").build());
        cidades.add(Cidade.builder().id(3L).nome("Belo Horizonte").codigo("BELO_HORIZONTE").build());
        return cidades;
    }

    public static Cidade gerarCidade(){
        return Cidade.builder()
                .id(1L)
                .nome("Manaus")
                .codigo("MANAUS")
                .build();
    }
}
