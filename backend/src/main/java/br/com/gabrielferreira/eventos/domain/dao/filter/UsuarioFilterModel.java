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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioFilterModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1289496390943814618L;

    private Long id;

    private String nome;

    private String email;

    private LocalDate dataCadastro;

    private LocalDate dataAtualizacao;

    public boolean isIdExistente(){
        return this.id != null;
    }

    public boolean isNomeExistente(){
        return StringUtils.isNotBlank(this.nome);
    }

    public boolean isEmailExistente(){
        return StringUtils.isNotBlank(this.email);
    }

    public boolean isDataCadastroExistente(){
        return this.dataCadastro != null;
    }

    public boolean isDataAtualizacaoExistente(){
        return this.dataAtualizacao != null;
    }
}
