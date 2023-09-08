package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.dao.QueryDslDAO;
import br.com.gabrielferreira.evento.dto.CidadeDTO;
import br.com.gabrielferreira.evento.dto.EventoDTO;
import br.com.gabrielferreira.evento.dto.EventoFiltroDTO;
import br.com.gabrielferreira.evento.dto.EventoInsertDTO;
import br.com.gabrielferreira.evento.entities.Cidade;
import br.com.gabrielferreira.evento.entities.Evento;
import br.com.gabrielferreira.evento.entities.QEvento;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.model.consulta.EventoDTOConsulta;
import br.com.gabrielferreira.evento.model.ModelConsulta;
import br.com.gabrielferreira.evento.repository.EventoRepository;
import com.querydsl.core.types.Projections;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.gabrielferreira.evento.dto.factory.EventoDTOFactory.*;
import static br.com.gabrielferreira.evento.entities.factory.EventoFactory.*;
import static br.com.gabrielferreira.evento.model.factory.ConsultaFactory.*;
import static br.com.gabrielferreira.evento.validate.ValidacaoConsulta.*;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    private final CidadeService cidadeService;

    private final QueryDslDAO queryDslDAO;

    @Transactional
    public EventoDTO cadastrarEvento(EventoInsertDTO eventoInsertDTO){
        Cidade cidade = cidadeService.buscarCidade(eventoInsertDTO.getCidade().getId());

        Evento evento = toEvento(cidade, eventoInsertDTO);
        evento = eventoRepository.save(evento);
        return toEventoDto(evento);
    }

    public EventoDTO buscarEventoPorId(Long id){
        return toEventoDto(buscarEvento(id));
    }

    @Transactional
    public EventoDTO atualizarEvento(Long id, EventoInsertDTO eventoInsertDTO){
        Evento eventoEncontrado = buscarEvento(id);

        Cidade cidadeEncontrada = cidadeService.buscarCidade(eventoInsertDTO.getCidade().getId());

        toEvento(cidadeEncontrada, eventoEncontrado, eventoInsertDTO);

        eventoEncontrado = eventoRepository.save(eventoEncontrado);

        return toEventoDto(eventoEncontrado);
    }

    @Transactional
    public void deletarEventoPorId(Long id){
        Evento eventoEncontrado = buscarEvento(id);
        eventoRepository.delete(eventoEncontrado);
    }

    public Page<EventoDTO> buscarEventos(Pageable pageable){
        ModelConsulta consulta = criar(EventoDTOConsulta.class);
        pageable = validarOrderBy(pageable, consulta);
        return toEventosDtos(eventoRepository.buscarEventos(pageable));
    }

    public Page<EventoDTO> buscarEventosAvancados(EventoFiltroDTO filtros, Pageable pageable){
        ModelConsulta consulta = criar(EventoDTOConsulta.class);
        pageable = validarOrderBy(pageable, consulta);

        QEvento qEvento = QEvento.evento;

        List<EventoDTO> eventoDTOS = queryDslDAO.query(q -> q.select(Projections.constructor(
                EventoDTO.class,
                qEvento.id,
                qEvento.nome,
                qEvento.dataEvento,
                qEvento.url,
                Projections.constructor(
                        CidadeDTO.class,
                        qEvento.cidade.id,
                        qEvento.cidade.nome,
                        qEvento.cidade.codigo
                ),
                qEvento.createdAt,
                qEvento.updatedAt
        ))).from(qEvento)
                .innerJoin(qEvento.cidade)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(eventoDTOS, pageable, eventoDTOS.size());
    }

    private Evento buscarEvento(Long id){
        return eventoRepository.buscarEventoPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Evento não encontrado"));
    }

}
