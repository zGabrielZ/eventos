package br.com.gabrielferreira.eventos.domain.service;

import br.com.gabrielferreira.eventos.domain.dao.QueryDslDAO;
import br.com.gabrielferreira.eventos.domain.dao.filter.UsuarioFilterModel;
import br.com.gabrielferreira.eventos.domain.dao.projection.UsuarioProjection;
import br.com.gabrielferreira.eventos.domain.model.QUsuario;
import br.com.gabrielferreira.eventos.domain.service.security.UsuarioAutenticacaoService;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.eventos.common.utils.DataUtils.*;

@Service
@RequiredArgsConstructor
public class ConsultaUsuarioService {

    private final QueryDslDAO queryDslDAO;

    private final UsuarioAutenticacaoService usuarioAutenticacaoService;

    public Page<UsuarioProjection> buscarUsuariosPaginados(Pageable pageable, UsuarioFilterModel filtro){
        usuarioAutenticacaoService.validarAdmin();
        QUsuario qUsuario = QUsuario.usuario;
        BooleanBuilder query = new BooleanBuilder();
        montarQuery(query, filtro, qUsuario);

        List<UsuarioProjection> usuarios = queryDslDAO.query(q -> q.select(Projections.constructor(
                UsuarioProjection.class,
                qUsuario.id,
                qUsuario.nome,
                qUsuario.email,
                qUsuario.dataCadastro,
                qUsuario.dataAtualizacao
        ))).from(qUsuario)
                .where(query)
                .orderBy(montarOrderBy(pageable.getSort(), qUsuario))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(usuarios, pageable, usuarios.size());
    }

    private void montarQuery(BooleanBuilder query, UsuarioFilterModel filtro, QUsuario qUsuario){
        if(filtro.isIdExistente()){
            query.and(qUsuario.id.eq(filtro.getId()));
        }

        if(filtro.isNomeExistente()){
            query.and(qUsuario.nome.likeIgnoreCase(Expressions.asString("%").concat(filtro.getNome().trim()).concat("%")));
        }

        if(filtro.isEmailExistente()){
            query.and(qUsuario.email.eq(filtro.getEmail()));
        }

        if(filtro.isDataCadastroExistente()){
            LocalDate dataCadastro = filtro.getDataCadastro();

            ZonedDateTime dataCadastroInicio = ZonedDateTime.of(dataCadastro, LocalTime.of(0, 0, 0), FUSO_HORARIO_PADRAO_SISTEMA);
            ZonedDateTime dataCadastroFim = ZonedDateTime.of(dataCadastro, LocalTime.of(23, 59, 59), FUSO_HORARIO_PADRAO_SISTEMA);

            query.and(qUsuario.dataCadastro.between(dataCadastroInicio, dataCadastroFim));
        }

        if(filtro.isDataAtualizacaoExistente()){
            LocalDate dataAtualizacao = filtro.getDataAtualizacao();

            ZonedDateTime dataAtualizacaoInicio = ZonedDateTime.of(dataAtualizacao, LocalTime.of(0, 0, 0), FUSO_HORARIO_PADRAO_SISTEMA);
            ZonedDateTime dataAtualizacaoFim = ZonedDateTime.of(dataAtualizacao, LocalTime.of(23, 59, 59), FUSO_HORARIO_PADRAO_SISTEMA);

            query.and(qUsuario.dataAtualizacao.between(dataAtualizacaoInicio, dataAtualizacaoFim));
        }
    }

    private OrderSpecifier<?>[] montarOrderBy(Sort sorts, QUsuario qUsuario){
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        if(sorts.isEmpty()){
            orderSpecifiers.add(orderByDataCadastroDesc(qUsuario));
        } else {
            orderBy(sorts, orderSpecifiers, qUsuario);
        }

        if(orderSpecifiers.isEmpty()){
            orderSpecifiers.add(orderByDataCadastroDesc(qUsuario));
        }

        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }

    private void orderBy(Sort sorts, List<OrderSpecifier<?>> orderSpecifiers, QUsuario qUsuario){
        sorts.forEach(sort -> {
            String propriedade = sort.getProperty();
            String direcao = sort.getDirection().name();

            Order order = "asc".equalsIgnoreCase(direcao)? Order.ASC : Order.DESC;
            if(propriedade.equals("id")){
                orderSpecifiers.add(orderById(order, qUsuario));
            }

            if(propriedade.equals("nome")){
                orderSpecifiers.add(orderByNome(order, qUsuario));
            }

            if(propriedade.equals("email")){
                orderSpecifiers.add(orderByEmail(order, qUsuario));
            }

            if(propriedade.equals("dataCadastro")){
                orderSpecifiers.add(orderByDataCadastro(order, qUsuario));
            }

            if(propriedade.equals("dataAtualizacao")){
                orderSpecifiers.add(orderByDataAtualizacao(order, qUsuario));
            }
        });
    }

    private OrderSpecifier<?> orderByDataCadastroDesc(QUsuario qUsuario){
        return qUsuario.dataCadastro.desc();
    }

    private OrderSpecifier<?> orderById(Order order, QUsuario qUsuario){
        return new OrderSpecifier<>(order, qUsuario.id);
    }

    private OrderSpecifier<?> orderByNome(Order order, QUsuario qUsuario){
        return new OrderSpecifier<>(order, qUsuario.nome);
    }

    private OrderSpecifier<?> orderByEmail(Order order, QUsuario qUsuario){
        return new OrderSpecifier<>(order, qUsuario.email);
    }

    private OrderSpecifier<?> orderByDataCadastro(Order order, QUsuario qUsuario){
        return new OrderSpecifier<>(order, qUsuario.dataCadastro);
    }

    private OrderSpecifier<?> orderByDataAtualizacao(Order order, QUsuario qUsuario){
        return new OrderSpecifier<>(order, qUsuario.dataAtualizacao);
    }

}
