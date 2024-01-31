package br.com.gabrielferreira.eventos.tests;

import br.com.gabrielferreira.eventos.api.model.input.PerfilIdInputModel;
import br.com.gabrielferreira.eventos.api.model.input.UsuarioInputModel;

import java.util.ArrayList;
import java.util.List;

public class UsuarioFactory {

    private UsuarioFactory(){}

    public static UsuarioInputModel criarUsuarioInput(){
        List<PerfilIdInputModel> perfis = new ArrayList<>();
        perfis.add(PerfilIdInputModel.builder().id(1L).build());
        perfis.add(PerfilIdInputModel.builder().id(2L).build());

        return UsuarioInputModel.builder()
                .nome("Teste nome")
                .email("teste@email.com")
                .senha("Abc@123")
                .perfis(perfis)
                .build();
    }
}
