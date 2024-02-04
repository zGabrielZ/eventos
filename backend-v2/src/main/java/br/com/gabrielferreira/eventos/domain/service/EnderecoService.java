package br.com.gabrielferreira.eventos.domain.service;

import br.com.gabrielferreira.eventos.domain.service.validator.EnderecoValidator;
import br.com.gabrielferreira.eventos.integration.client.ViaCepClient;
import br.com.gabrielferreira.eventos.integration.model.CepIntegrationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final ViaCepClient viaCepClient;

    private final EnderecoValidator enderecoValidator;

    public CepIntegrationModel buscarCep(String cep){
        enderecoValidator.validarCep(cep);
        CepIntegrationModel cepIntegrationModel = viaCepClient.buscarCep(cep);
        cepIntegrationModel.setCep(cepIntegrationModel.getCep().replaceAll("[^\\d ]", ""));
        return cepIntegrationModel;
    }
}
