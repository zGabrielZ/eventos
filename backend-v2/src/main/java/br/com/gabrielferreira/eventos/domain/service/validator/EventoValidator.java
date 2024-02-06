package br.com.gabrielferreira.eventos.domain.service.validator;

import br.com.gabrielferreira.eventos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.eventos.domain.model.Evento;
import br.com.gabrielferreira.eventos.domain.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventoValidator {

    private final EventoRepository eventoRepository;

    public void validarCampos(Evento evento){
        evento.setNome(evento.getNome().trim());
        evento.setUrl(evento.getUrl().trim());
    }

    public void validarNomeEvento(Long id, String nome){
        if(isNomeExistente(id, nome)){
            throw new RegraDeNegocioException(String.format("Não vai ser possível cadastrar este evento pois o nome '%s' já foi cadastrado", nome));
        }
    }

    public boolean isNomeExistente(Long id, String nome){
        if(id == null){
            return eventoRepository.buscarPorNome(nome)
                    .isPresent();
        } else {
            return eventoRepository.buscarPorNome(nome)
                    .filter(e -> !e.getId().equals(id))
                    .isPresent();
        }
    }
}
