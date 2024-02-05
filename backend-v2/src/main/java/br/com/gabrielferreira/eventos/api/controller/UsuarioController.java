package br.com.gabrielferreira.eventos.api.controller;

import br.com.gabrielferreira.eventos.api.mapper.UsuarioMapper;
import br.com.gabrielferreira.eventos.api.model.UsuarioModel;
import br.com.gabrielferreira.eventos.api.model.UsuarioResumidoModel;
import br.com.gabrielferreira.eventos.api.model.input.UsuarioInputModel;
import br.com.gabrielferreira.eventos.api.model.params.UsuarioParamsModel;
import br.com.gabrielferreira.eventos.domain.dao.filter.UsuarioFilterModel;
import br.com.gabrielferreira.eventos.domain.dao.projection.UsuarioProjection;
import br.com.gabrielferreira.eventos.domain.model.Usuario;
import br.com.gabrielferreira.eventos.domain.service.ConsultaUsuarioService;
import br.com.gabrielferreira.eventos.domain.service.UsuarioService;
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
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final ConsultaUsuarioService consultaUsuarioService;

    private final UsuarioMapper usuarioMapper;

    @PostMapping
    public ResponseEntity<UsuarioModel> cadastrarUsuario(@Valid @RequestBody UsuarioInputModel usuarioInputModel){
        Usuario usuario = usuarioMapper.toUsuario(usuarioInputModel);
        Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
        UsuarioModel usuarioModel = usuarioMapper.toUsuarioModel(usuarioCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(usuarioModel.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> buscarUsuarioPorId(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        UsuarioModel usuarioModel = usuarioMapper.toUsuarioModel(usuario);

        return ResponseEntity.ok().body(usuarioModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> atualizarUsuarioPorId(@PathVariable Long id, @Valid @RequestBody UsuarioInputModel usuarioInputModel){
        Usuario usuario = usuarioMapper.toUsuario(usuarioInputModel);
        Usuario usuarioAtualizado = usuarioService.atualizarUsuarioPorId(id, usuario);
        UsuarioModel usuarioModel = usuarioMapper.toUsuarioModel(usuarioAtualizado);

        return ResponseEntity.ok().body(usuarioModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPorId(@PathVariable Long id){
        usuarioService.deletarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioResumidoModel>> buscarUsuariosPaginados(@PageableDefault(size = 5) Pageable pageable, @Valid UsuarioParamsModel params){
        UsuarioFilterModel usuarioFilterModel = usuarioMapper.toUsuarioFilterModel(params);
        Page<UsuarioProjection> usuarioProjections = consultaUsuarioService.buscarUsuariosPaginados(pageable, usuarioFilterModel);
        Page<UsuarioResumidoModel> usuarioResumidoModels = usuarioMapper.toUsuarioResumidosModels(usuarioProjections);

        return ResponseEntity.ok().body(usuarioResumidoModels);
    }
}
