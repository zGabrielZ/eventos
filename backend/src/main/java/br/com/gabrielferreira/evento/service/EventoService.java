package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.dto.params.EventoParamsDTO;
import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.entity.Evento;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.EventoRepository;
import br.com.gabrielferreira.evento.repository.filter.EventoFilters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

import static br.com.gabrielferreira.evento.factory.domain.EventoDomainFactory.*;
import static br.com.gabrielferreira.evento.factory.entity.EventoFactory.*;
import static br.com.gabrielferreira.evento.factory.filters.EventoFiltersFactory.*;
import static br.com.gabrielferreira.evento.utils.PageUtils.*;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    private final CidadeService cidadeService;

    private final ConsultaAvancadaService consultaAvancadaService;

    @Transactional
    public EventoDomain cadastrarEvento(EventoRequestDTO eventoRequestDTO){
        EventoDomain eventoDomain = createEventoDomain(eventoRequestDTO);
        eventoDomain.setCidade(cidadeService.buscarCidadePorId(eventoRequestDTO.getCidade().getId()));

        Evento evento = createEvento(eventoDomain);
        evento = eventoRepository.save(evento);
        return toEventoDomain(evento);
    }

    public EventoDomain buscarEventoPorId(Long id){
        Evento evento = eventoRepository.buscarEventoPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Evento não encontrado"));
        return toEventoDomain(evento);
    }

    @Transactional
    public EventoDomain atualizarEvento(Long id, EventoRequestDTO eventoRequestDTO){
        EventoDomain eventoDomainEncontrado = buscarEventoPorId(id);
        eventoDomainEncontrado.setCidade(cidadeService.buscarCidadePorId(eventoRequestDTO.getCidade().getId()));

        EventoDomain eventoDomainUpdate = createEventoDomain(eventoRequestDTO);

        updateEventoDomain(eventoDomainEncontrado, eventoDomainUpdate);

        Evento evento = updateEvento(eventoDomainEncontrado);
        evento = eventoRepository.save(evento);
        return toEventoDomain(evento);
    }

    @Transactional
    public void deletarEventoPorId(Long id){
        EventoDomain eventoDomainEncontrado = buscarEventoPorId(id);
        eventoRepository.deleteById(eventoDomainEncontrado.getId());
    }

    public Page<EventoDomain> buscarEventos(Pageable pageable){
        pageable = validarOrderBy(pageable, atributoDtoToEntity());
        return toEventosDomains(eventoRepository.buscarEventos(pageable));
    }

    public Page<EventoDomain> buscarEventosAvancados(EventoParamsDTO params, Pageable pageable){
        EventoFilters eventoFilters = createEventoFilters(params);
        return consultaAvancadaService.buscarEventos(eventoFilters, pageable, atributoDtoToEntity());
    }

    private Map<String, String> atributoDtoToEntity(){
        Map<String, String> atributoDtoToEntity = new HashMap<>();
        atributoDtoToEntity.put("data", "dataEvento");
        return atributoDtoToEntity;
    }

}
