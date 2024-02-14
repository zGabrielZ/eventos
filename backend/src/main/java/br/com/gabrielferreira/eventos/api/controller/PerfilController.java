package br.com.gabrielferreira.eventos.api.controller;

import br.com.gabrielferreira.eventos.api.mapper.PerfilMapper;
import br.com.gabrielferreira.eventos.api.model.PerfilModel;
import br.com.gabrielferreira.eventos.domain.model.Perfil;
import br.com.gabrielferreira.eventos.domain.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Perfil Controller", description = "Endpoints para realizar consulta de perfil")
@RestController
@RequestMapping("/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    private final PerfilMapper perfilMapper;

    @Operation(summary = "Buscar perfis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfis encontrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PerfilModel.class)) })
    })
    @GetMapping
    public ResponseEntity<List<PerfilModel>> buscarPerfis(){
        List<Perfil> perfis = perfilService.buscarPerfis();
        List<PerfilModel> perfilModels = perfilMapper.toPerfisModels(perfis);

        return ResponseEntity.ok().body(perfilModels);
    }

    @Operation(summary = "Buscar perfil por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PerfilModel.class)) }),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PerfilModel> buscarPerfilPorId(@PathVariable Long id){
        Perfil perfil = perfilService.buscarPerfilPorId(id);
        PerfilModel perfilModel = perfilMapper.toPerfilModel(perfil);

        return ResponseEntity.ok().body(perfilModel);
    }
}
