package br.com.gabrielferreira.evento.factory.dto;

import br.com.gabrielferreira.evento.domain.LoginDomain;
import br.com.gabrielferreira.evento.dto.response.LoginResponseDTO;

public class LoginDTOFactory {

    private LoginDTOFactory(){}

    public static LoginResponseDTO toLoginResponseDto(LoginDomain loginDomain){
        return new LoginResponseDTO(loginDomain.getIdUsuario(), loginDomain.getTipo(), loginDomain.getToken(), loginDomain.getDataInicio(), loginDomain.getDataFim());
    }
}
