package br.com.gabrielferreira.eventos.domain.repository;

import br.com.gabrielferreira.eventos.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
