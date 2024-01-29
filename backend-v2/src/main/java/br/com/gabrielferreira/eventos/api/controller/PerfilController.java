package br.com.gabrielferreira.eventos.api.controller;

import br.com.gabrielferreira.eventos.api.mapper.PerfilMapper;
import br.com.gabrielferreira.eventos.api.model.PerfilModel;
import br.com.gabrielferreira.eventos.domain.model.Perfil;
import br.com.gabrielferreira.eventos.domain.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    private final PerfilMapper perfilMapper;

    @GetMapping
    public ResponseEntity<List<PerfilModel>> buscarPerfis(){
        List<Perfil> perfis = perfilService.buscarPerfis();
        List<PerfilModel> perfilModels = perfilMapper.toPerfisModels(perfis);

        return ResponseEntity.ok().body(perfilModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilModel> buscarPerfilPorId(@PathVariable Long id){
        Perfil perfil = perfilService.buscarPerfilPorId(id);
        PerfilModel perfilModel = perfilMapper.toPerfilModel(perfil);

        return ResponseEntity.ok().body(perfilModel);
    }
}
