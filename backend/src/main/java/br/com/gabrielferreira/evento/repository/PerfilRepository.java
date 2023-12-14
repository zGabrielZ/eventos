package br.com.gabrielferreira.evento.repository;

import br.com.gabrielferreira.evento.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    @Query("SELECT p FROM Perfil p ORDER BY p.tipo ASC")
    List<Perfil> buscarPerfil();

    @Query("SELECT p FROM Perfil p WHERE p.descricao = :descricao")
    Optional<Perfil> buscarPerfilPorDescricao(@Param("descricao") String codigo);

    @Query("SELECT p FROM Usuario u " +
            "JOIN u.perfis p " +
            "WHERE u.id = :idUsuario")
    List<Perfil> buscarPerfisPorUsuarioId(@Param("idUsuario") Long idUsuario);

}
