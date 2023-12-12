package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.dto.params.UsuarioParamsDTO;
import br.com.gabrielferreira.evento.dto.request.UsuarioResquestDTO;
import br.com.gabrielferreira.evento.dto.request.UsuarioUpdateResquestDTO;
import br.com.gabrielferreira.evento.dto.response.UsuarioPaginacaoResponseDTO;
import br.com.gabrielferreira.evento.dto.response.UsuarioResponseDTO;
import br.com.gabrielferreira.evento.repository.filter.UsuarioFilters;
import br.com.gabrielferreira.evento.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import static br.com.gabrielferreira.evento.factory.domain.UsuarioDomainFactory.*;
import static br.com.gabrielferreira.evento.factory.dto.UsuarioDTOFactory.*;
import static br.com.gabrielferreira.evento.factory.filter.UsuarioFilterFactory.*;


@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@Valid @RequestBody UsuarioResquestDTO usuarioResquestDTO){
        UsuarioDomain usuarioDomain = usuarioService.cadastrarUsuario(toCreateUsuarioDomain(usuarioResquestDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(usuarioDomain.getId()).toUri();
        return ResponseEntity.created(uri).body(toUsuarioResponseDto(usuarioDomain));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Long id){
        UsuarioDomain usuarioDomain = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok().body(toUsuarioResponseDto(usuarioDomain));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateResquestDTO usuarioUpdateResquestDTO){
        UsuarioDomain usuarioDomain = usuarioService.atualizarUsuario(toUpdateUsuarioDomain(id, usuarioUpdateResquestDTO));
        return ResponseEntity.ok().body(toUsuarioResponseDto(usuarioDomain));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPorId(@PathVariable Long id){
        usuarioService.deletarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioPaginacaoResponseDTO>> buscarUsuarios(UsuarioParamsDTO params, @PageableDefault(size = 5) Pageable pageable){
        UsuarioFilters usuarioFilters = toUsuarioFilters(params);
        Page<UsuarioDomain> usuarioDomains = usuarioService.buscarUsuarios(usuarioFilters, pageable);
        return ResponseEntity.ok().body(toUsuariosResponsesDtos(usuarioDomains));
    }
}
