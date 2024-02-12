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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios/{idUsuario}/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    private final ConsultaEventoService consultaEventoService;

    private final EventoMapper eventoMapper;

    @PostMapping
    public ResponseEntity<EventoModel> cadastrarEvento(@PathVariable Long idUsuario, @Valid @RequestBody EventoInputModel eventoInputModel){
        Evento evento = eventoMapper.toEvento(eventoInputModel);
        Evento eventoCadastrado = eventoService.cadastrarEvento(idUsuario, evento);
        EventoModel eventoModel = eventoMapper.toEventoModel(eventoCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(eventoModel.getId()).toUri();
        return ResponseEntity.created(uri).body(eventoModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoModel> buscarEventoPorId(@PathVariable Long idUsuario, @PathVariable Long id){
        Evento evento = eventoService.buscarEventoPorId(idUsuario, id);
        EventoModel eventoModel = eventoMapper.toEventoModel(evento);

        return ResponseEntity.ok().body(eventoModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoModel> atualizarEventoPorId(@PathVariable Long idUsuario, @PathVariable Long id, @Valid @RequestBody EventoInputModel eventoInputModel){
        Evento evento = eventoMapper.toEvento(eventoInputModel);
        Evento eventoAtualizado = eventoService.atualizarEventoPorId(idUsuario, id, evento);
        EventoModel eventoModel = eventoMapper.toEventoModel(eventoAtualizado);

        return ResponseEntity.ok().body(eventoModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEventoPorId(@PathVariable Long idUsuario, @PathVariable Long id){
        eventoService.deletarEventoPorId(idUsuario, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<EventoResumidoModel>> buscarEventosPaginados(@PathVariable Long idUsuario, @PageableDefault(size = 5) Pageable pageable, @Valid EventoParamsModel params){
        EventoFilterModel eventoFilterModel = eventoMapper.toEventoFilterModel(params);
        Page<EventoProjection> eventoProjections = consultaEventoService.buscarEventosPaginados(idUsuario, pageable, eventoFilterModel);
        Page<EventoResumidoModel> eventoResumidoModels = eventoMapper.toEventosResumidosModels(eventoProjections);

        return ResponseEntity.ok().body(eventoResumidoModels);
    }
}
