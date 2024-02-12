package br.com.gabrielferreira.eventos.api.mapper;

import br.com.gabrielferreira.eventos.api.model.CepModel;
import br.com.gabrielferreira.eventos.integration.model.CepIntegrationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    CepModel toCepModel(CepIntegrationModel cepIntegrationModel);
}
