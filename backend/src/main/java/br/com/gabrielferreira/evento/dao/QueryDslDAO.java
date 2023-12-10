package br.com.gabrielferreira.evento.dao;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class QueryDslDAO {

    private final EntityManager entityManager;

    public <T> T query(Function<JPAQuery<?>, T> function) {
        return function.apply(new JPAQuery<>(this.entityManager, JPQLTemplates.DEFAULT));
    }
}
