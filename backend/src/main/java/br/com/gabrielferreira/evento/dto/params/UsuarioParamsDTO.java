package br.com.gabrielferreira.evento.dto.params;

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
public class UsuarioParamsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3550554456478798801L;

    private Long id;

    private String nome;

    private String email;

    private List<Long> idsPerfis = new ArrayList<>();

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
