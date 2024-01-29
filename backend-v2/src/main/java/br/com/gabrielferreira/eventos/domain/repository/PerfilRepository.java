package br.com.gabrielferreira.eventos.domain.repository;

import br.com.gabrielferreira.eventos.domain.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    @Query("SELECT p FROM Perfil p " +
            "ORDER BY p.descricao ASC")
    List<Perfil> buscarPerfis();
}
