package br.com.gabrielferreira.eventos.api.controller;

import br.com.gabrielferreira.eventos.api.mapper.PerfilMapper;
import br.com.gabrielferreira.eventos.api.model.PerfilModel;
import br.com.gabrielferreira.eventos.api.model.params.PerfilParamsModel;
import br.com.gabrielferreira.eventos.domain.dao.filter.PerfilFilterModel;
import br.com.gabrielferreira.eventos.domain.dao.projection.PerfilProjection;
import br.com.gabrielferreira.eventos.domain.service.ConsultaPerfisUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Usuário Perfil Controller", description = "Endpoints para realizar consulta de perfis por usuário")
@RestController
@RequestMapping("/usuarios/{idUsuario}/perfis")
@RequiredArgsConstructor
public class UsuarioPerfilController {

    private final ConsultaPerfisUsuarioService consultaPerfisUsuarioService;

    private final PerfilMapper perfilMapper;

    @Operation(summary = "Buscar perfis paginados por usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfis encontrados",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PerfilModel.class)) }),
            @ApiResponse(responseCode = "403", description = "Não permitido para realizar requisição",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<PerfilModel>> buscarPerfisPorUsuario(@PathVariable Long idUsuario, @PageableDefault(size = 5) Pageable pageable, @Valid PerfilParamsModel params){
        PerfilFilterModel perfilFilterModel = perfilMapper.toPerfilFilterModel(params);
        Page<PerfilProjection> perfis = consultaPerfisUsuarioService.buscarPerfisPorUsuario(idUsuario, pageable, perfilFilterModel);
        Page<PerfilModel> perfilModels = perfilMapper.toPerfisModels(perfis);

        return ResponseEntity.ok().body(perfilModels);
    }
}
