package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.domain.PerfilDomain;
import br.com.gabrielferreira.evento.dto.response.PerfilResponseDTO;
import br.com.gabrielferreira.evento.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static br.com.gabrielferreira.evento.factory.dto.PerfilDTOFactory.*;

@RestController
@RequestMapping("/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> buscarPerfilPorId(@PathVariable Long id){
        PerfilDomain perfilDomain = perfilService.buscarPerfilPorId(id);
        return ResponseEntity.ok().body(toPerfilResponseDto(perfilDomain));
    }

    @GetMapping
    public ResponseEntity<List<PerfilResponseDTO>> buscarPerfis(){
        List<PerfilDomain> perfilDomains = perfilService.buscarPerfis();
        return ResponseEntity.ok().body(toPerfisResponseDtos(perfilDomains));
    }

    @GetMapping("/buscar")
    public ResponseEntity<PerfilResponseDTO> buscarPerfilPorDescricao(@RequestParam String descricao){
        PerfilDomain perfilDomain = perfilService.buscarPerfilPorDescricao(descricao);
        return ResponseEntity.ok().body(toPerfilResponseDto(perfilDomain));
    }

    @GetMapping("/usuarios/{idUsuario}")
    public ResponseEntity<List<PerfilResponseDTO>> buscarPerfisPorUsuario(@PathVariable Long idUsuario){
        List<PerfilDomain> perfilDomains = perfilService.buscarPerfisPorUsuario(idUsuario);
        return ResponseEntity.ok().body(toPerfisResponseDtos(perfilDomains));
    }
}
