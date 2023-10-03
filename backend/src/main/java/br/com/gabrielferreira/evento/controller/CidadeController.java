package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.dto.response.CidadeResponseDTO;
import br.com.gabrielferreira.evento.mapper.CidadeMapper;
import br.com.gabrielferreira.evento.service.CidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor
public class CidadeController {

    private final CidadeService cidadeService;

    private final CidadeMapper cidadeMapper;

    @GetMapping
    public ResponseEntity<List<CidadeResponseDTO>> buscarCidades(){
        List<CidadeDomain> cidadeDomains = cidadeService.buscarCidades();
        return ResponseEntity.ok().body(cidadeMapper.toCidadesResponsesDtos(cidadeDomains));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeResponseDTO> buscarCidadePorId(@PathVariable Long id){
        CidadeDomain cidadeDomain = cidadeService.buscarCidadePorId(id);
        return ResponseEntity.ok().body(cidadeMapper.toCidadeResponseDto(cidadeDomain));
    }

    @GetMapping("/buscar")
    public ResponseEntity<CidadeResponseDTO> buscarCidadePorCodigo(@RequestParam String codigo){
        CidadeDomain cidadeDomain = cidadeService.buscarCidadePorCodigo(codigo);
        return ResponseEntity.ok().body(cidadeMapper.toCidadeResponseDto(cidadeDomain));
    }
}
