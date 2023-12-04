package br.com.gabrielferreira.evento.repository;

import br.com.gabrielferreira.evento.entity.Cidade;
import br.com.gabrielferreira.evento.repository.projection.CidadeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    @Query("SELECT c FROM Cidade c ORDER BY c.nome ASC")
    List<Cidade> buscarCidades();

    @Query("SELECT c FROM Cidade c WHERE c.codigo = :codigo")
    Optional<Cidade> buscarCidadePorCodigo(@Param("codigo") String codigo);

    @Query("SELECT c.id as id, c.nome as nome FROM Cidade c " +
            "WHERE c.nome = :nome")
    Optional<CidadeProjection> existeNomeCidade(@Param("nome") String nome);

    @Query("SELECT c.id as id, c.codigo as codigo FROM Cidade c " +
            "WHERE c.codigo = :codigo")
    Optional<CidadeProjection> existeCodigoCidade(@Param("codigo") String nome);
}
