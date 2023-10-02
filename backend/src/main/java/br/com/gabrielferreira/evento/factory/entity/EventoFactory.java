package br.com.gabrielferreira.evento.factory.entity;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.domain.EventoDomain;
import br.com.gabrielferreira.evento.entity.Evento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventoFactory {

    private final CidadeFactory cidadeFactory;

    public Evento toEvento(CidadeDomain cidadeDomain, EventoDomain eventoDomain){
        if(eventoDomain != null){
            return Evento.builder()
                    .id(eventoDomain.getId())
                    .nome(eventoDomain.getNome())
                    .dataEvento(eventoDomain.getDataEvento())
                    .url(eventoDomain.getUrl())
                    .cidade(cidadeFactory.toCidade(cidadeDomain))
                    .createdAt(eventoDomain.getCreatedAt())
                    .updatedAt(eventoDomain.getUpdatedAt())
                    .build();
        }
        return null;
    }
}
