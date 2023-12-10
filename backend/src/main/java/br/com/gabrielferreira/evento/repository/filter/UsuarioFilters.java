package br.com.gabrielferreira.evento.repository.filter;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioFilters implements Serializable {

    @Serial
    private static final long serialVersionUID = -3550554456478798801L;

    private Long id;

    private String nome;

    private String email;

    private List<Long> idsPerfis = new ArrayList<>();

    private LocalDate createdAt;

    private LocalDate updatedAt;

    public boolean isIdExistente(){
        return this.id != null;
    }

    public boolean isNomeExistente(){
        return StringUtils.isNotBlank(this.nome);
    }

    public boolean isEmailExistente(){
        return StringUtils.isNotBlank(this.email);
    }

    public boolean isIdsPerfisExistente(){
        return !this.idsPerfis.isEmpty();
    }

    public boolean isCreatedAtExistente(){
        return this.createdAt != null;
    }

    public boolean isUpdatedAtExistente(){
        return this.updatedAt != null;
    }
}
