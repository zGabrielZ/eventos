package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.entity.Evento;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.factory.domain.EventoDomainFactory;
import br.com.gabrielferreira.evento.factory.entity.EventoFactory;
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

    private final EventoDomainFactory eventoDomainFactory;

    private final EventoFactory eventoFactory;

    @Transactional
    public EventoDomain cadastrarEvento(EventoDomain eventoDomain){
        CidadeDomain cidadeDomain = cidadeService.buscarCidadePorId(eventoDomain.getCidade().getId());

        Evento evento = eventoFactory.toEvento(cidadeDomain, eventoDomain);
        evento = eventoRepository.save(evento);
        return eventoDomainFactory.toEventoDomain(evento);
    }

    public EventoDomain buscarEventoPorId(Long id){
        return eventoDomainFactory.toEventoDomain(buscarEvento(id));
    }

    @Transactional
    public EventoDomain atualizarEvento(EventoDomain eventoDomain){
        EventoDomain eventoDomainEncontrado = buscarEventoPorId(eventoDomain.getId());
        CidadeDomain cidadeDomainEncontrada = cidadeService.buscarCidadePorId(eventoDomain.getCidade().getId());

        eventoDomainFactory.toEventoDomain(cidadeDomainEncontrada, eventoDomainEncontrado, eventoDomain);

        Evento evento = eventoFactory.toEvento(eventoDomainEncontrado.getCidade(), eventoDomainEncontrado);
        evento = eventoRepository.save(evento);
        return eventoDomainFactory.toEventoDomain(evento);
    }

    @Transactional
    public void deletarEventoPorId(Long id){
        EventoDomain eventoDomainEncontrado = buscarEventoPorId(id);
        eventoRepository.deleteById(eventoDomainEncontrado.getId());
    }

    public Page<EventoDomain> buscarEventos(Pageable pageable){
        pageable = validarOrderBy(pageable, atributoDtoToEntity());
        return eventoDomainFactory.toEventosDomains(eventoRepository.buscarEventos(pageable));
    }

    public Page<EventoDomain> buscarEventosAvancados(EventoFilters filtros, Pageable pageable){
        return consultaAvancadaService.buscarEventos(filtros, pageable, atributoDtoToEntity());
    }

    private Evento buscarEvento(Long id){
        return eventoRepository.buscarEventoPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Evento não encontrado"));
    }

    private Map<String, String> atributoDtoToEntity(){
        Map<String, String> atributoDtoToEntity = new HashMap<>();
        atributoDtoToEntity.put("data", "dataEvento");
        return atributoDtoToEntity;
    }

}
