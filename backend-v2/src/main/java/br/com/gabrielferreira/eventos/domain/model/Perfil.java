package br.com.gabrielferreira.eventos.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_PERFIL")
public class Perfil implements Serializable {

    @Serial
    private static final long serialVersionUID = 124441226725634777L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @Column(name = "AUTORIEDADE", nullable = false, unique = true)
    private String autoriedade;

    public boolean isContemPerfil(List<Perfil> perfis){
        return perfis.stream().anyMatch(p -> p.getId().equals(this.id));
    }

    public boolean isNaoContemPerfil(List<Perfil> perfis){
        return !isContemPerfil(perfis);
    }
}
