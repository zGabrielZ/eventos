package br.com.gabrielferreira.eventos.api.mapper;

import br.com.gabrielferreira.eventos.api.model.LoginModel;
import br.com.gabrielferreira.eventos.domain.service.security.model.InformacoesTokenModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    @BeanMapping(builder = @Builder( disableBuilder = true ))
    LoginModel toLoginModel(InformacoesTokenModel informacoesTokenModel);
}
