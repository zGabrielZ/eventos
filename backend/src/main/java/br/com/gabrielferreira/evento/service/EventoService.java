package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.entity.Evento;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.mapper.EventoMapper;
import br.com.gabrielferreira.evento.repository.EventoRepository;
import br.com.gabrielferreira.evento.repository.filter.EventoFilters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

import static br.com.gabrielferreira.evento.utils.PageUtils.*;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    private final CidadeService cidadeService;

    private final ConsultaAvancadaService consultaAvancadaService;

    private final EventoMapper eventoMapper;

    @Transactional
    public EventoDomain cadastrarEvento(EventoDomain eventoDomain){
        eventoDomain.setCidade(cidadeService.buscarCidadePorId(eventoDomain.getCidade().getId()));

        Evento evento = eventoMapper.toEvento(eventoDomain);
        evento = eventoRepository.save(evento);
        return eventoMapper.toEventoDomain(evento);
    }

    public EventoDomain buscarEventoPorId(Long id){
        Evento evento = eventoRepository.buscarEventoPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Evento não encontrado"));
        return eventoMapper.toEventoDomain(evento);
    }

    @Transactional
    public EventoDomain atualizarEvento(EventoDomain eventoDomain){
        EventoDomain eventoDomainEncontrado = buscarEventoPorId(eventoDomain.getId());
        eventoDomainEncontrado.setCidade(cidadeService.buscarCidadePorId(eventoDomain.getCidade().getId()));

        updateEvento(eventoDomainEncontrado, eventoDomain);

        Evento evento = eventoMapper.toEvento(eventoDomainEncontrado);
        evento = eventoRepository.save(evento);
        return eventoMapper.toEventoDomain(evento);
    }

    @Transactional
    public void deletarEventoPorId(Long id){
        EventoDomain eventoDomainEncontrado = buscarEventoPorId(id);
        eventoRepository.deleteById(eventoDomainEncontrado.getId());
    }

    public Page<EventoDomain> buscarEventos(Pageable pageable){
        pageable = validarOrderBy(pageable, atributoDtoToEntity());
        Page<Evento> eventoDomains = eventoRepository.buscarEventos(pageable);
        return eventoMapper.toEventosDomains(eventoDomains);
    }

    public Page<EventoDomain> buscarEventosAvancados(EventoFilters eventoFilters, Pageable pageable){
        return consultaAvancadaService.buscarEventos(eventoFilters, pageable, atributoDtoToEntity());
    }

    private Map<String, String> atributoDtoToEntity(){
        Map<String, String> atributoDtoToEntity = new HashMap<>();
        atributoDtoToEntity.put("data", "dataEvento");
        return atributoDtoToEntity;
    }

    private void updateEvento(EventoDomain eventoDomainEncontrado, EventoDomain eventoDomainUpdate){
        if(eventoDomainEncontrado != null && eventoDomainUpdate != null){
            eventoDomainEncontrado.setNome(eventoDomainUpdate.getNome());
            eventoDomainEncontrado.setDataEvento(eventoDomainUpdate.getDataEvento());
            eventoDomainEncontrado.setUrl(eventoDomainUpdate.getUrl());
        }
    }

}
