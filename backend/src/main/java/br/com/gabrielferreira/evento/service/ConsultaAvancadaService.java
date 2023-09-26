package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.dao.QueryDslDAO;
import br.com.gabrielferreira.evento.dto.CidadeDTO;
import br.com.gabrielferreira.evento.dto.EventoDTO;
import br.com.gabrielferreira.evento.dto.EventoFiltroDTO;
import br.com.gabrielferreira.evento.entities.Evento;
import br.com.gabrielferreira.evento.entities.QEvento;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static br.com.gabrielferreira.evento.utils.DataUtils.*;
import static br.com.gabrielferreira.evento.utils.PageUtils.*;

@Service
@RequiredArgsConstructor
public class ConsultaAvancadaService {

    private final QueryDslDAO queryDslDAO;

    public Page<EventoDTO> buscarEventos(EventoFiltroDTO filtros, Pageable pageable, Map<String, String> atributoDtoToEntity){
        validarPropriedadeInformada(pageable.getSort(), EventoDTO.class);
        pageable = validarOrderBy(pageable, atributoDtoToEntity);

        QEvento qEvento = QEvento.evento;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        validarFiltroEventos(filtros, booleanBuilder, qEvento);

        List<EventoDTO> eventoDTOS = queryDslDAO.query(q -> q.select(Projections.constructor(
                        EventoDTO.class,
                        qEvento.id,
                        qEvento.nome,
                        qEvento.dataEvento,
                        qEvento.url,
                        Projections.constructor(
                                CidadeDTO.class,
                                qEvento.cidade.id,
                                qEvento.cidade.nome,
                                qEvento.cidade.codigo
                        ),
                        qEvento.createdAt,
                        qEvento.updatedAt
                ))).from(qEvento)
                .innerJoin(qEvento.cidade)
                .where(booleanBuilder)
                .orderBy(getOrder(pageable.getSort(), Evento.class))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(eventoDTOS, pageable, eventoDTOS.size());
    }

    private void validarFiltroEventos(EventoFiltroDTO filtros, BooleanBuilder booleanBuilder, QEvento qEvento){
        if(filtros.isIdExistente()){
            booleanBuilder.and(qEvento.id.eq(filtros.getId()));
        }

        if(filtros.isNomeExistente()){
            booleanBuilder.and(qEvento.nome.likeIgnoreCase(Expressions.asString("%").concat(filtros.getNome().trim()).concat("%")));
        }

        if(filtros.isDataExistente()){
            booleanBuilder.and(qEvento.dataEvento.goe(filtros.getData()));
        }

        if(filtros.isUrlExistente()){
            booleanBuilder.and(qEvento.url.eq(filtros.getUrl()));
        }

        if(filtros.isIdCidadeExistente()){
            booleanBuilder.and(qEvento.cidade.id.eq(filtros.getIdCidade()));
        }

        if(filtros.isCreatedAtExistente()){
            ZonedDateTime createdAt = filtros.getCreatedAt().atStartOfDay(ZoneId.of(AMERICA_SAO_PAULO));
            booleanBuilder.and(qEvento.createdAt.goe(createdAt));
        }

        if(filtros.isUpdatedAtExistente()){
            ZonedDateTime updatedAt = filtros.getUpdatedAt().atStartOfDay(ZoneId.of(AMERICA_SAO_PAULO));
            booleanBuilder.and(qEvento.updatedAt.goe(updatedAt));
        }
    }

    private OrderSpecifier<?>[] getOrder(Sort sorts, Class<?> classe){
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        PathBuilder<?> entityPath = new PathBuilder<>(classe, classe.getSimpleName().toLowerCase());
        for (Sort.Order sort : sorts) {
            String propriedade = sort.getProperty();
            String direcao = sort.getDirection().name();

            OrderSpecifier<?> orderSpecifier = "asc".equalsIgnoreCase(direcao)
                    ? entityPath.getString(propriedade).asc()
                    : entityPath.getString(propriedade).desc();

            orderSpecifiers.add(orderSpecifier);
        }

        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }
}
