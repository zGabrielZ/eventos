package br.com.gabrielferreira.evento.factory.domain;

import br.com.gabrielferreira.evento.domain.LoginDomain;

import java.time.ZonedDateTime;

public class LoginDomainFactory {

    private LoginDomainFactory(){}

    public static LoginDomain toLoginDomain(Long idUsuario, String tipo, String token, ZonedDateTime dataInicio, ZonedDateTime dataFim){
        return LoginDomain.builder()
                .idUsuario(idUsuario)
                .tipo(tipo)
                .token(token)
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .build();
    }


}
