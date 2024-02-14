package br.com.gabrielferreira.eventos.api.controller;

import br.com.gabrielferreira.eventos.api.mapper.EventoMapper;
import br.com.gabrielferreira.eventos.api.model.EventoModel;
import br.com.gabrielferreira.eventos.api.model.EventoResumidoModel;
import br.com.gabrielferreira.eventos.api.model.input.EventoInputModel;
import br.com.gabrielferreira.eventos.api.model.params.EventoParamsModel;
import br.com.gabrielferreira.eventos.domain.dao.filter.EventoFilterModel;
import br.com.gabrielferreira.eventos.domain.dao.projection.EventoProjection;
import br.com.gabrielferreira.eventos.domain.model.Evento;
import br.com.gabrielferreira.eventos.domain.service.ConsultaEventoService;
import br.com.gabrielferreira.eventos.domain.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Evento Controller", description = "Endpoints para realizar eventos por usuário")
@RestController
@RequestMapping("/usuarios/{idUsuario}/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    private final ConsultaEventoService consultaEventoService;

    private final EventoMapper eventoMapper;

    @Operation(summary = "Cadastrar evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento cadastrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventoModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Regra de negócio",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Não permitido para realizar requisição",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<EventoModel> cadastrarEvento(@PathVariable Long idUsuario, @Valid @RequestBody EventoInputModel eventoInputModel){
        Evento evento = eventoMapper.toEvento(eventoInputModel);
        Evento eventoCadastrado = eventoService.cadastrarEvento(idUsuario, evento);
        EventoModel eventoModel = eventoMapper.toEventoModel(eventoCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(eventoModel.getId()).toUri();
        return ResponseEntity.created(uri).body(eventoModel);
    }

    @Operation(summary = "Buscar evento por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventoModel.class)) }),
            @ApiResponse(responseCode = "403", description = "Não permitido para realizar requisição",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventoModel> buscarEventoPorId(@PathVariable Long idUsuario, @PathVariable Long id){
        Evento evento = eventoService.buscarEventoPorId(idUsuario, id);
        EventoModel eventoModel = eventoMapper.toEventoModel(evento);

        return ResponseEntity.ok().body(eventoModel);
    }

    @Operation(summary = "Atualizar evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventoModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Regra de negócio",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Não permitido para realizar requisição",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário ou Evento não encontrado",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventoModel> atualizarEventoPorId(@PathVariable Long idUsuario, @PathVariable Long id, @Valid @RequestBody EventoInputModel eventoInputModel){
        Evento evento = eventoMapper.toEvento(eventoInputModel);
        Evento eventoAtualizado = eventoService.atualizarEventoPorId(idUsuario, id, evento);
        EventoModel eventoModel = eventoMapper.toEventoModel(eventoAtualizado);

        return ResponseEntity.ok().body(eventoModel);
    }

    @Operation(summary = "Deletar evento por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evento deletado",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Não permitido para realizar requisição",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEventoPorId(@PathVariable Long idUsuario, @PathVariable Long id){
        eventoService.deletarEventoPorId(idUsuario, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar eventos paginados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos encontrados",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventoModel.class)) }),
            @ApiResponse(responseCode = "403", description = "Não permitido para realizar requisição",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<EventoResumidoModel>> buscarEventosPaginados(@PathVariable Long idUsuario, @PageableDefault(size = 5) Pageable pageable, @Valid EventoParamsModel params){
        EventoFilterModel eventoFilterModel = eventoMapper.toEventoFilterModel(params);
        Page<EventoProjection> eventoProjections = consultaEventoService.buscarEventosPaginados(idUsuario, pageable, eventoFilterModel);
        Page<EventoResumidoModel> eventoResumidoModels = eventoMapper.toEventosResumidosModels(eventoProjections);

        return ResponseEntity.ok().body(eventoResumidoModels);
    }
}
