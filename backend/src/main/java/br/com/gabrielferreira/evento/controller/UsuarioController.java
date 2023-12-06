package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.dto.request.UsuarioResquestDTO;
import br.com.gabrielferreira.evento.dto.response.UsuarioResponseDTO;
import br.com.gabrielferreira.evento.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import static br.com.gabrielferreira.evento.factory.domain.UsuarioDomainFactory.*;
import static br.com.gabrielferreira.evento.factory.dto.UsuarioDTOFactory.*;


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
}
