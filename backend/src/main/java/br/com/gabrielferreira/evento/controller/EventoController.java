package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.dto.response.EventoResponseDTO;
import br.com.gabrielferreira.evento.dto.filter.EventoFilterDTO;
import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<EventoResponseDTO> cadastrarEvento(@RequestBody EventoRequestDTO eventoRequestDTO){
        EventoResponseDTO eventoResponseDTO = eventoService.cadastrarEvento(eventoRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(eventoResponseDTO.id()).toUri();
        return ResponseEntity.created(uri).body(eventoResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarEventoPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(eventoService.buscarEventoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> atualizarEvento(@PathVariable Long id, @RequestBody EventoRequestDTO eventoRequestDTO){
        return ResponseEntity.ok().body(eventoService.atualizarEvento(id, eventoRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEventoPorId(@PathVariable Long id){
        eventoService.deletarEventoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<EventoResponseDTO>> buscarEventos(@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok().body(eventoService.buscarEventos(pageable));
    }

    @GetMapping("/avancada")
    public ResponseEntity<Page<EventoResponseDTO>> buscarEventosAvancados(EventoFilterDTO filtros, @PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok().body(eventoService.buscarEventosAvancados(filtros, pageable));
    }
}
