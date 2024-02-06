package br.com.gabrielferreira.eventos.domain.repository;

import br.com.gabrielferreira.eventos.domain.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

}
