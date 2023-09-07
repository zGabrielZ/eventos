package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.dto.EventoDTO;
import br.com.gabrielferreira.evento.dto.EventoInsertDTO;
import br.com.gabrielferreira.evento.entities.Cidade;
import br.com.gabrielferreira.evento.entities.Evento;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.model.AbstractModelConsulta;
import br.com.gabrielferreira.evento.model.consulta.EventoDTOConsulta;
import br.com.gabrielferreira.evento.model.ModelConsulta;
import br.com.gabrielferreira.evento.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.gabrielferreira.evento.dto.factory.EventoDTOFactory.*;
import static br.com.gabrielferreira.evento.entities.factory.EventoFactory.*;
import static br.com.gabrielferreira.evento.model.factory.ConsultaFactory.*;
import static br.com.gabrielferreira.evento.utils.ConstantesUtils.*;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    private final CidadeService cidadeService;

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
        List<Sort.Order> sorts = pageable.getSort().stream().collect(Collectors.toList());

        boolean isHouveMudanca = false;
        for (Sort.Order sort : sorts) {
            String propriedade = sort.getProperty();

            List<String> propriedades = pageable.getSort().stream().map(Sort.Order::getProperty).toList();
            validarListaRepetidaString(propriedades, propriedade, String.format("A propriedade informada %s está sendo informada mais de uma vez", propriedade));

            AbstractModelConsulta consultaEncontrada = buscarModelConsultaPorPropriedade(consulta.getAbstractsModelsConsultas(), propriedade);
            validarPropriedadeNaoEncontrada(consultaEncontrada, propriedade);

            if(!consultaEncontrada.getAtributoEntidade().equals(propriedade)){
                isHouveMudanca = true;
                Sort.Order novoSort = new Sort.Order(sort.getDirection(), consultaEncontrada.getAtributoEntidade());
                sorts.set(sorts.indexOf(sort), novoSort);
            }
        }

        if(isHouveMudanca){
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));
        }

        return toEventosDtos(eventoRepository.buscarEventos(pageable));
    }

    private Evento buscarEvento(Long id){
        return eventoRepository.buscarEventoPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Evento não encontrado"));
    }

}
