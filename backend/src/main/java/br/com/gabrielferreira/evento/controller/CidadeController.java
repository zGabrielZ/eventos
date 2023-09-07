package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.dto.CidadeDTO;
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

    @GetMapping
    public ResponseEntity<List<CidadeDTO>> buscarCidades(){
        return ResponseEntity.ok().body(cidadeService.buscarCidades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeDTO> buscarCidadePorId(@PathVariable Long id){
        return ResponseEntity.ok().body(cidadeService.buscarCidadePorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<CidadeDTO> buscarCidadePorCodigo(@RequestParam String codigo){
        return ResponseEntity.ok().body(cidadeService.buscarCidadePorCodigo(codigo));
    }
}
