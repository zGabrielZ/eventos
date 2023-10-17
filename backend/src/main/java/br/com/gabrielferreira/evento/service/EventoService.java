package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.entity.Evento;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.EventoRepository;
import br.com.gabrielferreira.evento.repository.filter.EventoFilters;
import br.com.gabrielferreira.evento.repository.projection.EventoProjection;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static br.com.gabrielferreira.evento.utils.PageUtils.*;
import static br.com.gabrielferreira.evento.factory.domain.EventoDomainFactory.*;
import static br.com.gabrielferreira.evento.factory.entity.EventoFactory.*;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    private final CidadeService cidadeService;

    private final ConsultaAvancadaService consultaAvancadaService;

    @Transactional
    public EventoDomain cadastrarEvento(EventoDomain eventoDomain){
        validarCamposEvento(eventoDomain);
        validarNomeEvento(eventoDomain);

        eventoDomain.setCidade(cidadeService.buscarCidadePorId(eventoDomain.getCidade().getId()));

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
        EventoDomain eventoDomainEncontrado = buscarEventoPorId(eventoDomain.getId());
        validarCamposEvento(eventoDomain);
        validarNomeEvento(eventoDomain);

        eventoDomainEncontrado.setCidade(cidadeService.buscarCidadePorId(eventoDomain.getCidade().getId()));

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

    public void validarNomeEvento(EventoDomain eventoDomain){
        Optional<EventoProjection> eventoEncontrado = eventoRepository.existeNomeEvento(eventoDomain.getNome());
        if(eventoDomain.getId() == null && eventoEncontrado.isPresent()){
            throw new MsgException(String.format("Não vai ser possível cadastrar este evento pois o nome '%s' já foi cadastrado", eventoDomain.getNome()));
        } else if(eventoDomain.getId() != null && eventoEncontrado.isPresent() && !eventoDomain.getId().equals(eventoEncontrado.get().getId())){
            throw new MsgException(String.format("Não vai ser possível atualizar este evento pois o nome '%s' já foi cadastrado", eventoDomain.getNome()));
        }
    }

    private void validarCamposEvento(EventoDomain eventoDomain){
        eventoDomain.setNome(eventoDomain.getNome().trim());
        if(!StringUtils.isBlank(eventoDomain.getUrl())){
            eventoDomain.setUrl(eventoDomain.getUrl().trim());
        }
    }

    private Map<String, String> atributoDtoToEntity(){
        Map<String, String> atributoDtoToEntity = new HashMap<>();
        atributoDtoToEntity.put("data", "dataEvento");
        return atributoDtoToEntity;
    }

}
