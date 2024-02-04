package br.com.gabrielferreira.eventos.domain.service;

import br.com.gabrielferreira.eventos.domain.dao.QueryDslDAO;
import br.com.gabrielferreira.eventos.domain.dao.filter.PerfilFilterModel;
import br.com.gabrielferreira.eventos.domain.dao.projection.PerfilProjection;
import br.com.gabrielferreira.eventos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.eventos.domain.model.QPerfil;
import br.com.gabrielferreira.eventos.domain.model.QUsuario;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaPerfisUsuarioService {

    private final UsuarioService usuarioService;

    private final QueryDslDAO queryDslDAO;

    public Page<PerfilProjection> buscarPerfisPorUsuario(Long idUsuario, Pageable pageable, PerfilFilterModel filtro){
        if(usuarioService.isUsuarioNaoExistente(idUsuario)){
            throw new NaoEncontradoException("Usuário não encontrado");
        }

        QPerfil qPerfil = QPerfil.perfil;
        QUsuario qUsuario = QUsuario.usuario;
        BooleanBuilder query = new BooleanBuilder();
        montarQuery(query, filtro, qPerfil, qUsuario, idUsuario);

        List<PerfilProjection> perfis = queryDslDAO.query(q -> q.select(Projections.constructor(
                        PerfilProjection.class,
                        qPerfil.id,
                        qPerfil.descricao,
                        qPerfil.autoriedade
                ))).from(qPerfil)
                .innerJoin(qPerfil.usuarios, qUsuario)
                .where(query)
                .orderBy(montarOrderBy(pageable.getSort(), qPerfil))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(perfis, pageable, perfis.size());
    }

    private void montarQuery(BooleanBuilder query, PerfilFilterModel filtro, QPerfil qPerfil, QUsuario qUsuario, Long idUsuario){
        if(filtro.isIdExistente()){
            query.and(qPerfil.id.eq(filtro.getId()));
        }

        if(filtro.isDescricaoExistente()){
            query.and(qPerfil.descricao.likeIgnoreCase(Expressions.asString("%").concat(filtro.getDescricao().trim()).concat("%")));
        }

        if(filtro.isAutoriedadeExistente()){
            query.and(qPerfil.autoriedade.likeIgnoreCase(Expressions.asString("%").concat(filtro.getAutoriedade().trim()).concat("%")));
        }

        query.and(qUsuario.id.eq(idUsuario));
    }

    private OrderSpecifier<?>[] montarOrderBy(Sort sorts, QPerfil qPerfil){
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        if(sorts.isEmpty()){
            OrderSpecifier<?> orderSpecifier = qPerfil.descricao.asc();
            orderSpecifiers.add(orderSpecifier);
        } else {
            sorts.forEach(sort -> {
                String propriedade = sort.getProperty();
                String direcao = sort.getDirection().name();

                Order order = "asc".equalsIgnoreCase(direcao)? Order.ASC : Order.DESC;
                if(propriedade.equals("id")){
                    orderSpecifiers.add(new OrderSpecifier<>(order, qPerfil.id));
                }

                if(propriedade.equals("descricao")){
                    orderSpecifiers.add(new OrderSpecifier<>(order, qPerfil.descricao));
                }

                if(propriedade.equals("autoriedade")){
                    orderSpecifiers.add(new OrderSpecifier<>(order, qPerfil.autoriedade));
                }
            });
        }

        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }
}
