package br.com.gabrielferreira.evento.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CidadeRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 958714108846456044L;

    private Long id;
}
