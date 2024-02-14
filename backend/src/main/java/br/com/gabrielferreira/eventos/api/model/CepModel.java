package br.com.gabrielferreira.eventos.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class CepModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 3691860136705919358L;

    @Schema(description = "Cep", example = "01451000")
    private String cep;

    @Schema(description = "Logradouro", example = "Avenida Brigadeiro Faria Lima")
    private String logradouro;

    @Schema(description = "Complemento", example = "de 1884 a 3250 - lado par")
    private String complemento;

    @Schema(description = "Bairro", example = "Jardim Paulistano")
    private String bairro;

    @Schema(description = "Localidade", example = "São Paulo")
    private String localidade;

    @Schema(description = "UF", example = "SP")
    private String uf;
}
