package br.com.gabrielferreira.eventos.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import static br.com.gabrielferreira.eventos.common.utils.DataUtils.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"cidade"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_EVENTO")
public class Evento implements Serializable {

    @Serial
    private static final long serialVersionUID = 1167877349138602363L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "DATA_EVENTO", nullable = false)
    private LocalDate dataEvento;

    @Column(name = "URL", nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CIDADE", nullable = false)
    private Cidade cidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @Column(name = "DATA_CADASTRO", nullable = false)
    private ZonedDateTime dataCadastro;

    @Column(name = "DATA_ATUALIZACAO")
    private ZonedDateTime dataAtualizacao;

    @PrePersist
    public void prePersist(){
        dataCadastro = ZonedDateTime.now(UTC);
    }

    @PreUpdate
    public void preUpdate(){
        dataAtualizacao = ZonedDateTime.now(UTC);
    }
}
