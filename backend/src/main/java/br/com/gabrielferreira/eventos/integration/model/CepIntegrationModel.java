package br.com.gabrielferreira.eventos.integration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CepIntegrationModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -604863608592945548L;

    private String cep;

    private String logradouro;

    private String complemento;

    private String bairro;

    private String localidade;

    private String uf;

    private String ibge;

    private String gia;

    private String ddd;

    private String siafi;

    private boolean erro;
}
