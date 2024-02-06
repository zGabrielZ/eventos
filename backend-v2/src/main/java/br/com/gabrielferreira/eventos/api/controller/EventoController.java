package br.com.gabrielferreira.eventos.api.controller;

import br.com.gabrielferreira.eventos.api.mapper.EventoMapper;
import br.com.gabrielferreira.eventos.api.model.EventoModel;
import br.com.gabrielferreira.eventos.api.model.input.EventoInputModel;
import br.com.gabrielferreira.eventos.domain.model.Evento;
import br.com.gabrielferreira.eventos.domain.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios/{idUsuario}/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

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
}
