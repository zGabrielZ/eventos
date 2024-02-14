package br.com.gabrielferreira.eventos.api.controller;

import br.com.gabrielferreira.eventos.api.mapper.EnderecoMapper;
import br.com.gabrielferreira.eventos.api.model.CepModel;
import br.com.gabrielferreira.eventos.domain.service.EnderecoService;
import br.com.gabrielferreira.eventos.integration.model.CepIntegrationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Endereço Controller", description = "Endpoints para busca de endereços")
@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    private final EnderecoMapper enderecoMapper;

    @Operation(summary = "Buscar endereço via cep")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CepModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Regra de negócio",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<CepModel> buscarCep(@RequestParam String cep){
        CepIntegrationModel cepIntegrationModel = enderecoService.buscarCep(cep);
        CepModel cepModel = enderecoMapper.toCepModel(cepIntegrationModel);
        return ResponseEntity.ok().body(cepModel);
    }
}
