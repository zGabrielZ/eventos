package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.dto.response.EventoResponseDTO;
import br.com.gabrielferreira.evento.dto.filter.EventoFilterDTO;
import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.entities.Cidade;
import br.com.gabrielferreira.evento.entities.Evento;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

import static br.com.gabrielferreira.evento.dto.response.factory.EventoResponseDTOFactory.*;
import static br.com.gabrielferreira.evento.entities.factory.EventoFactory.*;
import static br.com.gabrielferreira.evento.utils.PageUtils.*;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    private final CidadeService cidadeService;

    private final ConsultaAvancadaService consultaAvancadaService;

    @Transactional
    public EventoResponseDTO cadastrarEvento(EventoRequestDTO eventoRequestDTO){
        Cidade cidade = cidadeService.buscarCidade(eventoRequestDTO.getCidade().getId());

        Evento evento = toEvento(cidade, eventoRequestDTO);
        evento = eventoRepository.save(evento);
        return toEventoDto(evento);
    }

    public EventoResponseDTO buscarEventoPorId(Long id){
        return toEventoDto(buscarEvento(id));
    }

    @Transactional
    public EventoResponseDTO atualizarEvento(Long id, EventoRequestDTO eventoRequestDTO){
        Evento eventoEncontrado = buscarEvento(id);

        Cidade cidadeEncontrada = cidadeService.buscarCidade(eventoRequestDTO.getCidade().getId());

        toEvento(cidadeEncontrada, eventoEncontrado, eventoRequestDTO);

        eventoEncontrado = eventoRepository.save(eventoEncontrado);

        return toEventoDto(eventoEncontrado);
    }

    @Transactional
    public void deletarEventoPorId(Long id){
        Evento eventoEncontrado = buscarEvento(id);
        eventoRepository.delete(eventoEncontrado);
    }

    public Page<EventoResponseDTO> buscarEventos(Pageable pageable){
        validarPropriedadeInformada(pageable.getSort(), EventoResponseDTO.class);
        pageable = validarOrderBy(pageable, atributoDtoToEntity());
        return toEventosDtos(eventoRepository.buscarEventos(pageable));
    }

    public Page<EventoResponseDTO> buscarEventosAvancados(EventoFilterDTO filtros, Pageable pageable){
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
