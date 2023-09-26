package br.com.gabrielferreira.evento.tests;

import br.com.gabrielferreira.evento.dto.CidadeInsertDTO;
import br.com.gabrielferreira.evento.dto.EventoInsertDTO;
import br.com.gabrielferreira.evento.entities.Cidade;
import br.com.gabrielferreira.evento.entities.Evento;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.evento.utils.DataUtils.*;

public class Factory {

    private Factory(){}

    public static List<Cidade> gerarCidades(){
        List<Cidade> cidades = new ArrayList<>();
        cidades.add(Cidade.builder().id(1L).nome("Manaus").codigo("MANAUS").build());
        cidades.add(Cidade.builder().id(2L).nome("Campinas").codigo("CAMPINAS").build());
        cidades.add(Cidade.builder().id(3L).nome("Belo Horizonte").codigo("BELO_HORIZONTE").build());
        return cidades;
    }

    public static Cidade gerarCidade(){
        return Cidade.builder()
                .id(1L)
                .nome("Manaus")
                .codigo("MANAUS")
                .build();
    }

    public static Evento gerarEvento(){
        return Evento.builder()
                .id(1L)
                .nome("Evento teste")
                .dataEvento(LocalDate.of(2023, 4, 22))
                .url("https://www.google.com.br/?hl=pt-BR")
                .cidade(gerarCidade())
                .createdAt(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)))
                .updatedAt(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)))
                .build();
    }

    public static PageImpl<Evento> gerarPageEventos(){
        List<Evento> eventos = new ArrayList<>();
        eventos.add(gerarEvento());
        return new PageImpl<>(eventos);
    }

    public static EventoInsertDTO criarEventoInsertDto(){
        CidadeInsertDTO cidadeInsertDTO = CidadeInsertDTO.builder().id(1L).build();
        return EventoInsertDTO.builder()
                .nome("evento teste")
                .data(LocalDate.of(2022, 4, 22))
                .url("https://www.google.com.br/?hl=pt-BR")
                .cidade(cidadeInsertDTO)
                .build();
    }

    public static EventoInsertDTO criarEventoUpdate(){
        CidadeInsertDTO cidadeInsertDTO = CidadeInsertDTO.builder().id(2L).build();
        return EventoInsertDTO.builder()
                .nome("evento teste atualizado")
                .data(LocalDate.of(2023, 6, 22))
                .url("teste url")
                .cidade(cidadeInsertDTO)
                .build();
    }
}
