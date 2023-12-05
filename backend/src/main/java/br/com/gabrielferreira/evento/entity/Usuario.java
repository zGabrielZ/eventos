package br.com.gabrielferreira.evento.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.evento.utils.DataUtils.UTC;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"perfis"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_USUARIO")
public class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 124441226725634777L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NOME", nullable = false, unique = true)
    private String nome;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "SENHA", nullable = false)
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TB_USUARIO_PERFIL",
            joinColumns = @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID", table = "TB_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID", table = "TB_PERFIL"))
    private List<Perfil> perfis = new ArrayList<>();

    @Column(name = "CREATED_AT", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "UPDATED_AT")
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
