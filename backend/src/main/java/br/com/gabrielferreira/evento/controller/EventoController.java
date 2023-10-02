package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.dto.params.EventoParamsDTO;
import br.com.gabrielferreira.evento.dto.response.EventoResponseDTO;
import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.mapper.domain.EventoDomainMapper;
import br.com.gabrielferreira.evento.mapper.dto.EventoDTOMapper;
import br.com.gabrielferreira.evento.mapper.filters.EventoFiltersMapper;
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

    private final EventoDTOMapper eventoDTOMapper;

    private final EventoDomainMapper eventoDomainMapper;

    private final EventoFiltersMapper eventoFiltersMapper;

    @PostMapping
    public ResponseEntity<EventoResponseDTO> cadastrarEvento(@RequestBody EventoRequestDTO eventoRequestDTO){
        EventoDomain eventoDomain = eventoService.cadastrarEvento(eventoDomainMapper.toEventoDomain(eventoRequestDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(eventoDomain.getId()).toUri();
        return ResponseEntity.created(uri).body(eventoDTOMapper.toEventoDto(eventoDomain));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarEventoPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(eventoDTOMapper.toEventoDto(eventoService.buscarEventoPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> atualizarEvento(@PathVariable Long id, @RequestBody EventoRequestDTO eventoRequestDTO){
        EventoDomain eventoDomain = eventoService.atualizarEvento(eventoDomainMapper.toEventoDomain(id, eventoRequestDTO));
        return ResponseEntity.ok().body(eventoDTOMapper.toEventoDto(eventoDomain));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEventoPorId(@PathVariable Long id){
        eventoService.deletarEventoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<EventoResponseDTO>> buscarEventos(@PageableDefault(size = 5) Pageable pageable){
        validarPropriedadeInformada(pageable.getSort(), EventoResponseDTO.class);
        return ResponseEntity.ok().body(eventoDTOMapper.toEventosDtos(eventoService.buscarEventos(pageable)));
    }

    @GetMapping("/avancada")
    public ResponseEntity<Page<EventoResponseDTO>> buscarEventosAvancados(EventoParamsDTO params, @PageableDefault(size = 5) Pageable pageable){
        validarPropriedadeInformada(pageable.getSort(), EventoResponseDTO.class);
        EventoFilters eventoFilters = eventoFiltersMapper.toEventoFilters(params);
        return ResponseEntity.ok().body(eventoDTOMapper.toEventosDtos(eventoService.buscarEventosAvancados(eventoFilters, pageable)));
    }
}
