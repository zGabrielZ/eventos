package br.com.gabrielferreira.eventos.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 150854081687212908L;

    @Schema(description = "Id do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome do usuário", example = "Gabriel Ferreira")
    private String nome;

    @Schema(description = "E-mail do usuário", example = "teste@email.com")
    private String email;

    @Schema(description = "Perfis do usuário")
    private List<PerfilModel> perfis = new ArrayList<>();

    @Schema(description = "Data cadastro do usuário", example = "2024-02-11T16:49:23.177681-03:00")
    private ZonedDateTime dataCadastro;

    @Schema(description = "Data atualização do usuário", example = "2024-02-11T16:49:23.177681-03:00")
    private ZonedDateTime dataAtualizacao;
}
