package br.com.gabrielferreira.evento.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PerfilDomain implements Serializable, GrantedAuthority {

    @Serial
    private static final long serialVersionUID = 8860351152148956020L;

    @EqualsAndHashCode.Include
    private Long id;

    private String descricao;

    private String tipo;

    @Override
    public String getAuthority() {
        return this.descricao;
    }
}
