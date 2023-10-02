package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.dto.response.CidadeResponseDTO;
import br.com.gabrielferreira.evento.factory.dto.CidadeResponseDTOFactory;
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

    private final CidadeResponseDTOFactory cidadeResponseDTOFactory;

    @GetMapping
    public ResponseEntity<List<CidadeResponseDTO>> buscarCidades(){
        List<CidadeResponseDTO> cidadeResponseDTOS = cidadeResponseDTOFactory.toCidadesResponsesDtos(cidadeService.buscarCidades());
        return ResponseEntity.ok().body(cidadeResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeResponseDTO> buscarCidadePorId(@PathVariable Long id){
        CidadeResponseDTO cidadeResponseDTO = cidadeResponseDTOFactory.toCidadeResponseDto(cidadeService.buscarCidadePorId(id));
        return ResponseEntity.ok().body(cidadeResponseDTO);
    }

    @GetMapping("/buscar")
    public ResponseEntity<CidadeResponseDTO> buscarCidadePorCodigo(@RequestParam String codigo){
        CidadeResponseDTO cidadeResponseDTO = cidadeResponseDTOFactory.toCidadeResponseDto(cidadeService.buscarCidadePorCodigo(codigo));
        return ResponseEntity.ok().body(cidadeResponseDTO);
    }
}
