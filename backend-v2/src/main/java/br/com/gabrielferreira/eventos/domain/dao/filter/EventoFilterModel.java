package br.com.gabrielferreira.eventos.domain.dao.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventoFilterModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -507023954304201242L;

    private Long id;

    private String nome;

    private LocalDate data;

    private String url;

    private LocalDate dataCadastro;

    private LocalDate dataAtualizacao;

    private String localidade;

    private String uf;

    public boolean isIdExistente(){
        return this.id != null;
    }

    public boolean isNomeExistente(){
        return StringUtils.isNotBlank(this.nome);
    }

    public boolean isDataExistente(){
        return this.data != null;
    }

    public boolean isUrlExistente(){
        return StringUtils.isNotBlank(this.url);
    }

    public boolean isDataCadastroExistente(){
        return this.dataCadastro != null;
    }

    public boolean isDataAtualizacaoExistente(){
        return this.dataAtualizacao != null;
    }

    public boolean isLocalidadeExistente(){
        return StringUtils.isNotBlank(this.localidade);
    }

    public boolean isUfExistente(){
        return StringUtils.isNotBlank(this.uf);
    }
}
