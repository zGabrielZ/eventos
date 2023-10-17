package br.com.gabrielferreira.evento.repository;

import br.com.gabrielferreira.evento.entity.Evento;
import br.com.gabrielferreira.evento.repository.projection.EventoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query("SELECT e FROM Evento e JOIN FETCH e.cidade c " +
            "WHERE e.id = :id")
    Optional<Evento> buscarEventoPorId(@Param("id") Long id);

    @Query("SELECT e FROM Evento e JOIN FETCH e.cidade c ")
    Page<Evento> buscarEventos(Pageable pageable);

    @Query("SELECT e.id as id, e.nome as nome FROM Evento e " +
            "WHERE e.nome = :nome")
    Optional<EventoProjection> existeNomeEvento(@Param("nome") String nome);

}
