package br.com.gabrielferreira.eventos.domain.repository;

import br.com.gabrielferreira.eventos.domain.model.Usuario;
import br.com.gabrielferreira.eventos.domain.repository.projection.UsuarioMinProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u.id as id, u.email as email FROM Usuario u " +
            "WHERE u.email = :email")
    Optional<UsuarioMinProjection> buscarPorEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u " +
            "JOIN FETCH u.perfis p " +
            "WHERE u.id = :id")
    Optional<Usuario> buscarPorId(@Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(u.id) > 0 THEN true ELSE false END FROM Usuario u " +
            "WHERE u.id = :id")
    Boolean buscarUsuarioExistente(@Param("id") Long id);
}
