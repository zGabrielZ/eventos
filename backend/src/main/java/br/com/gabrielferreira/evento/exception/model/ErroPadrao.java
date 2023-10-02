package br.com.gabrielferreira.evento.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErroPadrao implements Serializable {

    @Serial
    private static final long serialVersionUID = 4722847680686864711L;

    private ZonedDateTime momento;

    private Integer status;

    private String erro;

    private String mensagem;

    private String caminhoUrl;
}
