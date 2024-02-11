package br.com.gabrielferreira.eventos.tests;

import br.com.gabrielferreira.eventos.api.model.input.LoginInputModel;

public class LoginFactory {

    private LoginFactory(){}

    public static LoginInputModel criarLogin(String email, String senha){
        return LoginInputModel.builder()
                .email(email)
                .senha(senha)
                .build();
    }
}
