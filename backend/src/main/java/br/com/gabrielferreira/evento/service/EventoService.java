package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.entity.Evento;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.EventoRepository;
import br.com.gabrielferreira.evento.repository.filter.EventoFilters;
import br.com.gabrielferreira.evento.service.validation.EventoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

import static br.com.gabrielferreira.evento.utils.PageUtils.*;
import static br.com.gabrielferreira.evento.factory.domain.EventoDomainFactory.*;
import static br.com.gabrielferreira.evento.factory.entity.EventoFactory.*;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    private final ConsultaAvancadaService consultaAvancadaService;

    private final EventoValidator eventoValidator;

    @Transactional
    public EventoDomain cadastrarEvento(EventoDomain eventoDomain){
        eventoValidator.validarCampos(eventoDomain);
        eventoValidator.validarNome(eventoDomain);
        eventoValidator.validarCidade(eventoDomain);

        Evento evento = toCreateEvento(eventoDomain);
        evento = eventoRepository.save(evento);
        return toEventoDomain(evento);
    }

    public EventoDomain buscarEventoPorId(Long id){
        Evento evento = eventoRepository.buscarEventoPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Evento não encontrado"));
        return toEventoDomain(evento);
    }

    @Transactional
    public EventoDomain atualizarEvento(EventoDomain eventoDomain){
        eventoValidator.validarCampos(eventoDomain);
        eventoValidator.validarNome(eventoDomain);
        eventoValidator.validarCidade(eventoDomain);

        EventoDomain eventoDomainEncontrado = buscarEventoPorId(eventoDomain.getId());

        Evento evento = toUpdateEvento(eventoDomainEncontrado, eventoDomain);
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
        Page<Evento> eventoDomains = eventoRepository.buscarEventos(pageable);
        return toEventosDomains(eventoDomains);
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
