package br.com.gabrielferreira.evento.tests;

import br.com.gabrielferreira.evento.entity.Perfil;

import java.util.ArrayList;
import java.util.List;

public class PerfilFactory {

    private PerfilFactory(){}

    public static Perfil gerarPerfil(){
        return Perfil.builder()
                .id(1L)
                .descricao("ROLE_ADMIN")
                .tipo("Adminstrador")
                .build();
    }

    public static List<Perfil> gerarPerfis(){
        List<Perfil> perfis = new ArrayList<>();
        perfis.add(Perfil.builder().id(1L).descricao("ROLE_ADMIN").tipo("Adminstrador").build());
        perfis.add(Perfil.builder().id(2L).descricao("ROLE_CLIENT").tipo("Cliente").build());
        return perfis;
    }
}
