package br.com.gabrielferreira.eventos.api.controller;

import br.com.gabrielferreira.eventos.api.mapper.EnderecoMapper;
import br.com.gabrielferreira.eventos.api.model.CepModel;
import br.com.gabrielferreira.eventos.domain.service.EnderecoService;
import br.com.gabrielferreira.eventos.integration.model.CepIntegrationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    private final EnderecoMapper enderecoMapper;

    @GetMapping
    public ResponseEntity<CepModel> buscarCep(@RequestParam String cep){
        CepIntegrationModel cepIntegrationModel = enderecoService.buscarCep(cep);
        CepModel cepModel = enderecoMapper.toCepModel(cepIntegrationModel);
        return ResponseEntity.ok().body(cepModel);
    }
}
