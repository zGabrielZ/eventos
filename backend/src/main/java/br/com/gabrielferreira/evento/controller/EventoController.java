package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.dto.EventoDTO;
import br.com.gabrielferreira.evento.dto.EventoFiltroDTO;
import br.com.gabrielferreira.evento.dto.EventoInsertDTO;
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
    public ResponseEntity<EventoDTO> cadastrarEvento(@RequestBody EventoInsertDTO eventoInsertDTO){
        EventoDTO eventoDTO = eventoService.cadastrarEvento(eventoInsertDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(eventoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(eventoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscarEventoPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(eventoService.buscarEventoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> atualizarEvento(@PathVariable Long id, @RequestBody EventoInsertDTO eventoInsertDTO){
        return ResponseEntity.ok().body(eventoService.atualizarEvento(id, eventoInsertDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEventoPorId(@PathVariable Long id){
        eventoService.deletarEventoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<EventoDTO>> buscarEventos(@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok().body(eventoService.buscarEventos(pageable));
    }

    @GetMapping("/avancada")
    public ResponseEntity<Page<EventoDTO>> buscarEventosAvancados(EventoFiltroDTO filtros, @PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok().body(eventoService.buscarEventosAvancados(filtros, pageable));
    }
}
