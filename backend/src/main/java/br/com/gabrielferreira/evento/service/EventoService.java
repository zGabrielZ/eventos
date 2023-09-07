package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.dto.EventoDTO;
import br.com.gabrielferreira.evento.dto.EventoInsertDTO;
import br.com.gabrielferreira.evento.entities.Cidade;
import br.com.gabrielferreira.evento.entities.Evento;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.gabrielferreira.evento.dto.factory.EventoDTOFactory.*;
import static br.com.gabrielferreira.evento.entities.factory.EventoFactory.*;

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
        return toEventosDtos(eventoRepository.buscarEventos(pageable));
    }

    private Evento buscarEvento(Long id){
        return eventoRepository.buscarEventoPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Evento não encontrado"));
    }

}
