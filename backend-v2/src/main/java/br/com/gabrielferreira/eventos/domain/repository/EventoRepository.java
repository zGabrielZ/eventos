package br.com.gabrielferreira.eventos.domain.repository;

import br.com.gabrielferreira.eventos.domain.model.Evento;
import br.com.gabrielferreira.eventos.domain.repository.projection.EventoMinProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query("SELECT e.id as id, e.nome as nome FROM Evento e " +
            "WHERE e.nome = :nome")
    Optional<EventoMinProjection> buscarPorNome(@Param("nome") String nome);
}
