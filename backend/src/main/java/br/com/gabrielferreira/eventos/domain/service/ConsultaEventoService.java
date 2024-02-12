package br.com.gabrielferreira.eventos.domain.service;

import br.com.gabrielferreira.eventos.domain.dao.QueryDslDAO;
import br.com.gabrielferreira.eventos.domain.dao.filter.EventoFilterModel;
import br.com.gabrielferreira.eventos.domain.dao.projection.EventoProjection;
import br.com.gabrielferreira.eventos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.eventos.domain.model.QCidade;
import br.com.gabrielferreira.eventos.domain.model.QEvento;
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

import static br.com.gabrielferreira.eventos.common.utils.DataUtils.FUSO_HORARIO_PADRAO_SISTEMA;

@Service
@RequiredArgsConstructor
public class ConsultaEventoService {

    private final QueryDslDAO queryDslDAO;

    private final UsuarioService usuarioService;

    private final UsuarioAutenticacaoService usuarioAutenticacaoService;

    public Page<EventoProjection> buscarEventosPaginados(Long idUsuario, Pageable pageable, EventoFilterModel filtro){
        usuarioAutenticacaoService.validarAdminOuProprioUsuario(idUsuario);
        if(usuarioService.isUsuarioNaoExistente(idUsuario)){
            throw new NaoEncontradoException("Usuário não encontrado");
        }

        QEvento qEvento = QEvento.evento;
        QUsuario qUsuario = QUsuario.usuario;
        QCidade qCidade = QCidade.cidade;
        BooleanBuilder query = new BooleanBuilder();
        montarQuery(query, filtro, qEvento, qUsuario, idUsuario, qCidade);

        List<EventoProjection> eventos = queryDslDAO.query(q -> q.select(Projections.constructor(
                EventoProjection.class,
                qEvento.id,
                qEvento.nome,
                qEvento.dataEvento,
                qEvento.url,
                qEvento.dataCadastro,
                qEvento.dataAtualizacao,
                qCidade.id,
                qCidade.cep,
                qCidade.logradouro,
                qCidade.complemento,
                qCidade.bairro,
                qCidade.localidade,
                qCidade.uf,
                qCidade.dataCadastro,
                qCidade.dataAtualizacao
        ))).from(qEvento)
                .innerJoin(qEvento.cidade, qCidade)
                .innerJoin(qEvento.usuario, qUsuario)
                .where(query)
                .orderBy(montarOrderBy(pageable.getSort(), qEvento))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(eventos, pageable, eventos.size());
    }

    private void montarQuery(BooleanBuilder query, EventoFilterModel filtro, QEvento qEvento, QUsuario qUsuario, Long idUsuario, QCidade qCidade){
        if(filtro.isIdExistente()){
            query.and(qEvento.id.eq(filtro.getId()));
        }

        if(filtro.isNomeExistente()){
            query.and(qEvento.nome.likeIgnoreCase(Expressions.asString("%").concat(filtro.getNome().trim()).concat("%")));
        }

        if(filtro.isDataExistente()){
            query.and(qEvento.dataEvento.eq(filtro.getData()));
        }

        if(filtro.isUrlExistente()){
            query.and(qEvento.url.eq(filtro.getUrl()));
        }

        if(filtro.isDataCadastroExistente()){
            LocalDate dataCadastro = filtro.getDataCadastro();

            ZonedDateTime dataCadastroInicio = ZonedDateTime.of(dataCadastro, LocalTime.of(0, 0, 0), FUSO_HORARIO_PADRAO_SISTEMA);
            ZonedDateTime dataCadastroFim = ZonedDateTime.of(dataCadastro, LocalTime.of(23, 59, 59), FUSO_HORARIO_PADRAO_SISTEMA);

            query.and(qEvento.dataCadastro.between(dataCadastroInicio, dataCadastroFim));
        }

        if(filtro.isDataAtualizacaoExistente()){
            LocalDate dataAtualizacao = filtro.getDataAtualizacao();

            ZonedDateTime dataAtualizacaoInicio = ZonedDateTime.of(dataAtualizacao, LocalTime.of(0, 0, 0), FUSO_HORARIO_PADRAO_SISTEMA);
            ZonedDateTime dataAtualizacaoFim = ZonedDateTime.of(dataAtualizacao, LocalTime.of(23, 59, 59), FUSO_HORARIO_PADRAO_SISTEMA);

            query.and(qEvento.dataAtualizacao.between(dataAtualizacaoInicio, dataAtualizacaoFim));
        }

        if(filtro.isLocalidadeExistente()){
            query.and(qCidade.localidade.likeIgnoreCase(Expressions.asString("%").concat(filtro.getLocalidade().trim()).concat("%")));
        }

        if(filtro.isUfExistente()){
            query.and(qCidade.uf.eq(filtro.getUf()));
        }

        query.and(qUsuario.id.eq(idUsuario));
    }

    private OrderSpecifier<?>[] montarOrderBy(Sort sorts, QEvento qEvento){
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        if(sorts.isEmpty()){
            orderSpecifiers.add(orderByDataCadastroDesc(qEvento));
        } else {
            orderBy(sorts, orderSpecifiers, qEvento);
        }

        if(orderSpecifiers.isEmpty()){
            orderSpecifiers.add(orderByDataCadastroDesc(qEvento));
        }

        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }

    private void orderBy(Sort sorts, List<OrderSpecifier<?>> orderSpecifiers, QEvento qEvento){
        sorts.forEach(sort -> {
            String propriedade = sort.getProperty();
            String direcao = sort.getDirection().name();

            Order order = "asc".equalsIgnoreCase(direcao)? Order.ASC : Order.DESC;

            if(propriedade.equals("id")){
                orderSpecifiers.add(new OrderSpecifier<>(order, qEvento.id));
            }

            if(propriedade.equals("nome")){
                orderSpecifiers.add(new OrderSpecifier<>(order, qEvento.nome));
            }

            if(propriedade.equals("data")){
                orderSpecifiers.add(new OrderSpecifier<>(order, qEvento.dataEvento));
            }

            if(propriedade.equals("url")){
                orderSpecifiers.add(new OrderSpecifier<>(order, qEvento.url));
            }

            if(propriedade.equals("dataCadastro")){
                orderSpecifiers.add(new OrderSpecifier<>(order, qEvento.dataCadastro));
            }

            if(propriedade.equals("dataAtualizacao")){
                orderSpecifiers.add(new OrderSpecifier<>(order, qEvento.dataAtualizacao));
            }

            orderByCidade(order, propriedade, orderSpecifiers, qEvento.cidade);
        });
    }

    private void orderByCidade(Order order, String propriedade, List<OrderSpecifier<?>> orderSpecifiers, QCidade qCidade){
        if(propriedade.equals("cidade.cep")){
            orderSpecifiers.add(new OrderSpecifier<>(order, qCidade.cep));
        }

        if(propriedade.equals("cidade.logradouro")){
            orderSpecifiers.add(new OrderSpecifier<>(order, qCidade.logradouro));
        }

        if(propriedade.equals("cidade.complemento")){
            orderSpecifiers.add(new OrderSpecifier<>(order, qCidade.complemento));
        }

        if(propriedade.equals("cidade.bairro")){
            orderSpecifiers.add(new OrderSpecifier<>(order, qCidade.bairro));
        }

        if(propriedade.equals("cidade.localidade")){
            orderSpecifiers.add(new OrderSpecifier<>(order, qCidade.localidade));
        }

        if(propriedade.equals("cidade.uf")){
            orderSpecifiers.add(new OrderSpecifier<>(order, qCidade.uf));
        }

        if(propriedade.equals("cidade.id")){
            orderSpecifiers.add(new OrderSpecifier<>(order, qCidade.id));
        }

        if(propriedade.equals("cidade.dataCadastro")){
            orderSpecifiers.add(new OrderSpecifier<>(order, qCidade.dataCadastro));
        }

        if(propriedade.equals("cidade.dataAtualizacao")){
            orderSpecifiers.add(new OrderSpecifier<>(order, qCidade.dataAtualizacao));
        }
    }

    private OrderSpecifier<?> orderByDataCadastroDesc(QEvento qEvento){
        return qEvento.dataCadastro.desc();
    }
}
