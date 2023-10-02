package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.entity.Evento;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.mapper.domain.EventoDomainMapper;
import br.com.gabrielferreira.evento.mapper.entity.EventoMapper;
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

    private final EventoDomainMapper eventoDomainMapper;

    @Transactional
    public EventoDomain cadastrarEvento(EventoDomain eventoDomain){
        eventoDomain.setCidade(cidadeService.buscarCidadePorId(eventoDomain.getCidade().getId()));

        Evento evento = eventoMapper.toEvento(eventoDomain);
        evento = eventoRepository.save(evento);
        return eventoDomainMapper.toEventoDomain(evento);
    }

    public EventoDomain buscarEventoPorId(Long id){
        Evento evento = eventoRepository.buscarEventoPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Evento não encontrado"));
        return eventoDomainMapper.toEventoDomain(evento);
    }

    @Transactional
    public EventoDomain atualizarEvento(EventoDomain eventoDomain){
        EventoDomain eventoDomainEncontrado = buscarEventoPorId(eventoDomain.getId());
        eventoDomainEncontrado.setCidade(cidadeService.buscarCidadePorId(eventoDomain.getCidade().getId()));

        eventoDomainMapper.updateEventoDomain(eventoDomainEncontrado, eventoDomain);

        Evento evento = eventoMapper.toEvento(eventoDomainEncontrado);
        evento = eventoRepository.save(evento);
        return eventoDomainMapper.toEventoDomain(evento);
    }

    @Transactional
    public void deletarEventoPorId(Long id){
        EventoDomain eventoDomainEncontrado = buscarEventoPorId(id);
        eventoRepository.deleteById(eventoDomainEncontrado.getId());
    }

    public Page<EventoDomain> buscarEventos(Pageable pageable){
        pageable = validarOrderBy(pageable, atributoDtoToEntity());
        return eventoDomainMapper.toEventosDomains(eventoRepository.buscarEventos(pageable));
    }

    public Page<EventoDomain> buscarEventosAvancados(EventoFilters eventoFilters, Pageable pageable){
        return consultaAvancadaService.buscarEventos(eventoFilters, pageable, atributoDtoToEntity());
    }

    private Map<String, String> atributoDtoToEntity(){
        Map<String, String> atributoDtoToEntity = new HashMap<>();
        atributoDtoToEntity.put("data", "dataEvento");
        return atributoDtoToEntity;
    }

}
