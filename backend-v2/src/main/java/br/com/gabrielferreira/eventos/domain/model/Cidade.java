package br.com.gabrielferreira.eventos.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

import static br.com.gabrielferreira.eventos.common.utils.DataUtils.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_CIDADE")
public class Cidade implements Serializable {

    @Serial
    private static final long serialVersionUID = 124441226725634777L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "CEP", nullable = false)
    private String cep;

    @Column(name = "LOGRADOURO", nullable = false)
    private String logradouro;

    @Column(name = "COMPLEMENTO")
    private String complemento;

    @Column(name = "BAIRRO", nullable = false)
    private String bairro;

    @Column(name = "LOCALIDADE", nullable = false)
    private String localidade;

    @Column(name = "UF")
    private String uf;

    @Column(name = "IBGE")
    private String ibge;

    @Column(name = "GIA")
    private String gia;

    @Column(name = "DDD")
    private String ddd;

    @Column(name = "SIAFI")
    private String siafi;

    @Column(name = "DATA_CADASTRO", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "DATA_ATUALIZACAO")
    private ZonedDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt = ZonedDateTime.now(UTC);
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = ZonedDateTime.now(UTC);
    }
}
