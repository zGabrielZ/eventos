package br.com.gabrielferreira.evento.service.validation;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.repository.EventoRepository;
import br.com.gabrielferreira.evento.repository.projection.EventoProjection;
import br.com.gabrielferreira.evento.service.CidadeService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventoValidator {

    private final CidadeService cidadeService;

    private final EventoRepository eventoRepository;

    public void validarCampos(EventoDomain eventoDomain){
        eventoDomain.setNome(eventoDomain.getNome().trim());
        if(!StringUtils.isBlank(eventoDomain.getUrl())){
            eventoDomain.setUrl(eventoDomain.getUrl().trim());
        }
    }

    public void validarNome(EventoDomain eventoDomain){
        Optional<EventoProjection> eventoEncontrado = eventoRepository.existeNomeEvento(eventoDomain.getNome());
        if(eventoDomain.getId() == null && eventoEncontrado.isPresent()){
            throw new MsgException(String.format("Não vai ser possível cadastrar este evento pois o nome '%s' já foi cadastrado", eventoDomain.getNome()));
        } else if(eventoDomain.getId() != null && eventoEncontrado.isPresent() && !eventoDomain.getId().equals(eventoEncontrado.get().getId())){
            throw new MsgException(String.format("Não vai ser possível atualizar este evento pois o nome '%s' já foi cadastrado", eventoDomain.getNome()));
        }
    }

    public void validarCidade(EventoDomain eventoDomain){
        CidadeDomain cidadeDomain = cidadeService.buscarCidadePorId(eventoDomain.getCidade().getId());
        eventoDomain.setCidade(cidadeDomain);
    }
}
