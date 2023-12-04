package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.dto.request.CidadeRequestDTO;
import br.com.gabrielferreira.evento.dto.response.CidadeResponseDTO;
import br.com.gabrielferreira.evento.service.CidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static br.com.gabrielferreira.evento.factory.dto.CidadeDTOFactory.*;
import static br.com.gabrielferreira.evento.factory.domain.CidadeDomainFactory.*;

@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor
public class CidadeController {

    private final CidadeService cidadeService;

    @PostMapping
    public ResponseEntity<CidadeResponseDTO> cadastrarCidade(@Valid @RequestBody CidadeRequestDTO cidadeRequestDTO){
        CidadeDomain cidadeDomain = cidadeService.cadastrarCidade(toCreateCidadeDomain(cidadeRequestDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(cidadeDomain.getId()).toUri();
        return ResponseEntity.created(uri).body(toCidadeResponseDto(cidadeDomain));
    }

    @GetMapping
    public ResponseEntity<List<CidadeResponseDTO>> buscarCidades(){
        List<CidadeDomain> cidadeDomains = cidadeService.buscarCidades();
        return ResponseEntity.ok().body(toCidadesResponsesDtos(cidadeDomains));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeResponseDTO> buscarCidadePorId(@PathVariable Long id){
        CidadeDomain cidadeDomain = cidadeService.buscarCidadePorId(id);
        return ResponseEntity.ok().body(toCidadeResponseDto(cidadeDomain));
    }

    @GetMapping("/buscar")
    public ResponseEntity<CidadeResponseDTO> buscarCidadePorCodigo(@RequestParam String codigo){
        CidadeDomain cidadeDomain = cidadeService.buscarCidadePorCodigo(codigo);
        return ResponseEntity.ok().body(toCidadeResponseDto(cidadeDomain));
    }
}
