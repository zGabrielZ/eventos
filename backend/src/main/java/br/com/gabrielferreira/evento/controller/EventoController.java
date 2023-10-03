package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.dto.params.EventoParamsDTO;
import br.com.gabrielferreira.evento.dto.response.EventoResponseDTO;
import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.mapper.EventoMapper;
import br.com.gabrielferreira.evento.repository.filter.EventoFilters;
import br.com.gabrielferreira.evento.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import static br.com.gabrielferreira.evento.utils.PageUtils.*;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    private final EventoMapper eventoMapper;

    @PostMapping
    public ResponseEntity<EventoResponseDTO> cadastrarEvento(@RequestBody EventoRequestDTO eventoRequestDTO){
        EventoDomain eventoDomain = eventoService.cadastrarEvento(eventoMapper.toEventoDomain(eventoRequestDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(eventoDomain.getId()).toUri();
        return ResponseEntity.created(uri).body(eventoMapper.toEventoResponseDto(eventoDomain));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarEventoPorId(@PathVariable Long id){
        EventoDomain eventoDomain = eventoService.buscarEventoPorId(id);
        return ResponseEntity.ok().body(eventoMapper.toEventoResponseDto(eventoDomain));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> atualizarEvento(@PathVariable Long id, @RequestBody EventoRequestDTO eventoRequestDTO){
        EventoDomain eventoDomain = eventoService.atualizarEvento(eventoMapper.toEventoDomain(id, eventoRequestDTO));
        return ResponseEntity.ok().body(eventoMapper.toEventoResponseDto(eventoDomain));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEventoPorId(@PathVariable Long id){
        eventoService.deletarEventoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<EventoResponseDTO>> buscarEventos(@PageableDefault(size = 5) Pageable pageable){
        validarPropriedadeInformada(pageable.getSort(), EventoResponseDTO.class);
        Page<EventoDomain> eventoDomains = eventoService.buscarEventos(pageable);
        return ResponseEntity.ok().body(eventoMapper.toEventosResponsesDto(eventoDomains));
    }

    @GetMapping("/avancada")
    public ResponseEntity<Page<EventoResponseDTO>> buscarEventosAvancados(EventoParamsDTO params, @PageableDefault(size = 5) Pageable pageable){
        validarPropriedadeInformada(pageable.getSort(), EventoResponseDTO.class);
        EventoFilters eventoFilters = eventoMapper.toEventoFilters(params);
        Page<EventoDomain> eventoDomains = eventoService.buscarEventosAvancados(eventoFilters, pageable);
        return ResponseEntity.ok().body(eventoMapper.toEventosResponsesDto(eventoDomains));
    }
}
