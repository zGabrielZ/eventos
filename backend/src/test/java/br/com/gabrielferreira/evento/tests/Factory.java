package br.com.gabrielferreira.evento.tests;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.dto.request.CidadeRequestDTO;
import br.com.gabrielferreira.evento.dto.request.EventoRequestDTO;
import br.com.gabrielferreira.evento.entity.Cidade;
import br.com.gabrielferreira.evento.entity.Evento;
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

    public static Cidade gerarCidade2(){
        return Cidade.builder()
                .id(2L)
                .nome("São Paulo")
                .codigo("SAO_PAULO")
                .build();
    }

    public static CidadeDomain gerarCidadeDomain(){
        Cidade cidade = gerarCidade();
        return CidadeDomain.builder()
                .id(cidade.getId())
                .nome(cidade.getNome())
                .codigo(cidade.getCodigo())
                .build();
    }

    public static CidadeDomain gerarCidadeDomain2(){
        Cidade cidade = gerarCidade2();
        return CidadeDomain.builder()
                .id(cidade.getId())
                .nome(cidade.getNome())
                .codigo(cidade.getCodigo())
                .build();
    }

    public static PageImpl<Evento> gerarPageEventos(){
        List<Evento> eventos = new ArrayList<>();
        eventos.add(gerarEventoInsert());
        return new PageImpl<>(eventos);
    }

    public static EventoRequestDTO criarEventoInsertDto(){
        CidadeRequestDTO cidadeRequestDTO = CidadeRequestDTO.builder().id(gerarCidade().getId()).build();
        return EventoRequestDTO.builder()
                .nome("evento teste")
                .data(LocalDate.of(2022, 4, 22))
                .url("https://www.google.com.br/?hl=pt-BR")
                .cidade(cidadeRequestDTO)
                .build();
    }

    public static EventoRequestDTO criarEventoUpdateDto(){
        CidadeRequestDTO cidadeRequestDTO = CidadeRequestDTO.builder().id(gerarCidade2().getId()).build();
        return EventoRequestDTO.builder()
                .nome("evento teste atualizado")
                .data(LocalDate.of(2023, 6, 22))
                .url("teste url")
                .cidade(cidadeRequestDTO)
                .build();
    }

    public static Evento gerarEventoInsert(){
        EventoRequestDTO eventoRequestDTO = criarEventoInsertDto();
        Cidade cidade = Cidade.builder().id(eventoRequestDTO.getCidade().getId()).build();
        return Evento.builder()
                .id(1L)
                .nome(eventoRequestDTO.getNome())
                .dataEvento(eventoRequestDTO.getData())
                .url(eventoRequestDTO.getUrl())
                .cidade(cidade)
                .createdAt(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)))
                .updatedAt(null)
                .build();
    }

    public static Evento gerarEventoUpdate(){
        EventoRequestDTO eventoRequestDTO = criarEventoUpdateDto();
        Cidade cidade = Cidade.builder().id(eventoRequestDTO.getCidade().getId()).build();
        return Evento.builder()
                .id(1L)
                .nome(eventoRequestDTO.getNome())
                .dataEvento(eventoRequestDTO.getData())
                .url(eventoRequestDTO.getUrl())
                .cidade(cidade)
                .createdAt(null)
                .updatedAt(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)))
                .build();
    }
}
