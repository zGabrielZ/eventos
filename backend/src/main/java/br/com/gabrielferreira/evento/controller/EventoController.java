package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.dto.response.EventoResponseDTO;
import br.com.gabrielferreira.evento.dto.params.EventoParamsDTO;
import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.factory.domain.EventoDomainFactory;
import br.com.gabrielferreira.evento.factory.dto.EventoResponseDTOFactory;
import br.com.gabrielferreira.evento.factory.filters.EventoFiltersFactory;
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

    private final EventoDomainFactory eventoDomainFactory;

    private final EventoResponseDTOFactory eventoResponseDTOFactory;

    private final EventoFiltersFactory eventoFiltersFactory;

    @PostMapping
    public ResponseEntity<EventoResponseDTO> cadastrarEvento(@RequestBody EventoRequestDTO eventoRequestDTO){
        EventoDomain eventoDomain = eventoService.cadastrarEvento(eventoDomainFactory.toEventoDomain(eventoRequestDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(eventoDomain.getId()).toUri();
        return ResponseEntity.created(uri).body(eventoResponseDTOFactory.toEventoDto(eventoDomain));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarEventoPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(eventoResponseDTOFactory.toEventoDto(eventoService.buscarEventoPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> atualizarEvento(@PathVariable Long id, @RequestBody EventoRequestDTO eventoRequestDTO){
        EventoDomain eventoDomain = eventoService.atualizarEvento(eventoDomainFactory.toEventoDomain(id, eventoRequestDTO));
        return ResponseEntity.ok().body(eventoResponseDTOFactory.toEventoDto(eventoDomain));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEventoPorId(@PathVariable Long id){
        eventoService.deletarEventoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<EventoResponseDTO>> buscarEventos(@PageableDefault(size = 5) Pageable pageable){
        validarPropriedadeInformada(pageable.getSort(), EventoResponseDTO.class);
        return ResponseEntity.ok().body(eventoResponseDTOFactory.toEventosDtos(eventoService.buscarEventos(pageable)));
    }

    @GetMapping("/avancada")
    public ResponseEntity<Page<EventoResponseDTO>> buscarEventosAvancados(EventoParamsDTO params, @PageableDefault(size = 5) Pageable pageable){
        validarPropriedadeInformada(pageable.getSort(), EventoResponseDTO.class);
        return ResponseEntity.ok().body(eventoResponseDTOFactory.toEventosDtos(eventoService.buscarEventosAvancados(eventoFiltersFactory.toEventoFilters(params), pageable)));
    }
}
