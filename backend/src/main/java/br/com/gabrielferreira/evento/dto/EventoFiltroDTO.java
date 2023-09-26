package br.com.gabrielferreira.evento.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventoFiltroDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3550554456478798801L;

    private Long id;

    private String nome;

    private LocalDate data;

    private String url;

    private Long idCidade;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @JsonIgnore
    public boolean isIdExistente(){
        return this.id != null;
    }

    @JsonIgnore
    public boolean isNomeExistente(){
        return StringUtils.isNotBlank(this.nome);
    }

    @JsonIgnore
    public boolean isDataExistente(){
        return this.data != null;
    }

    @JsonIgnore
    public boolean isUrlExistente(){
        return StringUtils.isNotBlank(this.url);
    }

    @JsonIgnore
    public boolean isIdCidadeExistente(){
        return this.idCidade != null;
    }

    @JsonIgnore
    public boolean isCreatedAtExistente(){
        return this.createdAt != null;
    }

    @JsonIgnore
    public boolean isUpdatedAtExistente(){
        return this.updatedAt != null;
    }
}
