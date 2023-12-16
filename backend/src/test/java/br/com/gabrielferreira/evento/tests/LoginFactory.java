package br.com.gabrielferreira.evento.tests;

import br.com.gabrielferreira.evento.dto.request.LoginRequestDTO;

public class LoginFactory {

    private LoginFactory(){}

    public static LoginRequestDTO criarLogin(String email, String senha){
        return LoginRequestDTO.builder()
                .email(email)
                .senha(senha)
                .build();
    }
}
