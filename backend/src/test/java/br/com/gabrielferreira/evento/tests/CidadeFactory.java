package br.com.gabrielferreira.evento.tests;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.dto.request.CidadeRequestDTO;
import br.com.gabrielferreira.evento.entity.Cidade;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.evento.utils.DataUtils.*;

public class CidadeFactory {

    private CidadeFactory(){}

    public static List<Cidade> gerarCidades(){
        List<Cidade> cidades = new ArrayList<>();
        cidades.add(Cidade.builder().id(1L).nome("Manaus").codigo("MANAUS").createdAt(ZonedDateTime.now(UTC)).updatedAt(ZonedDateTime.now(UTC)).build());
        cidades.add(Cidade.builder().id(2L).nome("Campinas").codigo("CAMPINAS").createdAt(ZonedDateTime.now(UTC)).updatedAt(ZonedDateTime.now(UTC)).build());
        cidades.add(Cidade.builder().id(3L).nome("Belo Horizonte").codigo("BELO_HORIZONTE").createdAt(ZonedDateTime.now(UTC)).updatedAt(ZonedDateTime.now(UTC)).build());
        return cidades;
    }

    public static Cidade gerarCidade(){
        return Cidade.builder()
                .id(1L)
                .nome("Manaus")
                .codigo("MANAUS")
                .createdAt(ZonedDateTime.now(UTC))
                .updatedAt(ZonedDateTime.now(UTC))
                .build();
    }

    public static CidadeRequestDTO criarCidadeInsertDto(){
        return CidadeRequestDTO.builder()
                .nome("Ribeirão Preto")
                .codigo("RIBEIRAO_PRETO")
                .build();
    }

    public static CidadeDomain criarCidadeDomainInsert(CidadeRequestDTO cidadeRequestDTO){
        return CidadeDomain.builder()
                .nome(cidadeRequestDTO.getNome())
                .codigo(cidadeRequestDTO.getCodigo())
                .build();
    }

    public static Cidade criarCidadeInsert(CidadeDomain cidadeDomain){
        return Cidade.builder()
                .id(cidadeDomain.getId())
                .nome(cidadeDomain.getNome())
                .codigo(cidadeDomain.getCodigo())
                .createdAt(ZonedDateTime.now(UTC))
                .build();
    }

    public static CidadeRequestDTO criarCidadeUpdateDto(){
        return CidadeRequestDTO.builder()
                .nome("Alagoas")
                .codigo("ALAGOAS")
                .build();
    }

    public static CidadeDomain criarCidadeDomainUpdate(Long id, CidadeRequestDTO cidadeRequestDTO){
        return CidadeDomain.builder()
                .id(id)
                .nome(cidadeRequestDTO.getNome())
                .codigo(cidadeRequestDTO.getCodigo())
                .build();
    }

    public static Cidade criarCidadeUpdate(CidadeDomain cidadeDomain){
        return Cidade.builder()
                .id(cidadeDomain.getId())
                .nome(cidadeDomain.getNome())
                .codigo(cidadeDomain.getCodigo())
                .updatedAt(ZonedDateTime.now(UTC))
                .build();
    }
}
