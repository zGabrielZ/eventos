package br.com.gabrielferreira.evento.specification;

import br.com.gabrielferreira.evento.entity.Usuario;
import br.com.gabrielferreira.evento.repository.filter.UsuarioFilters;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.evento.utils.DataUtils.UTC;

@RequiredArgsConstructor
public class UsuarioSpecification implements Specification<Usuario> {

    @Serial
    private static final long serialVersionUID = 8394249382388272487L;

    private static final String PERFIL = "perfis";

    private final UsuarioFilters usuarioFilters;

    @Override
    public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();
        if (isQueryCount(query)) {
            root.join(PERFIL, JoinType.INNER);
        } else {
            root.fetch(PERFIL, JoinType.INNER);
        }

        if(usuarioFilters.isIdExistente()){
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), usuarioFilters.getId())));
        }

        if(usuarioFilters.isNomeExistente()){
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("nome"), "%".concat(usuarioFilters.getNome()).concat("%"))));
        }

        if(usuarioFilters.isEmailExistente()){
           predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("email"), usuarioFilters.getEmail())));
        }

        if(usuarioFilters.isIdsPerfisExistente()){
            predicates.add(root.get(PERFIL).get("id").in(usuarioFilters.getIdsPerfis()));
        }

        if(usuarioFilters.isCreatedAtExistente()){
            ZonedDateTime createdAt = usuarioFilters.getCreatedAt().atStartOfDay(UTC);
            predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAt)));
        }

        if(usuarioFilters.isUpdatedAtExistente()){
            ZonedDateTime updatedAt = usuarioFilters.getUpdatedAt().atStartOfDay(UTC);
            predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("updatedAt"), updatedAt)));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private boolean isQueryCount(CriteriaQuery<?> criteriaQuery){
        return criteriaQuery.getResultType() == Long.class || criteriaQuery.getResultType() == long.class;
    }
}
