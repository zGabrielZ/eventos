package br.com.gabrielferreira.evento.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static br.com.gabrielferreira.evento.utils.DataUtils.AMERICA_SAO_PAULO;

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

    @Column(name = "URL")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CIDADE", nullable = false)
    private Cidade cidade;

    @Column(name = "CREATED_AT", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private ZonedDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt = ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO));
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO));
    }
}
