package br.com.gabrielferreira.eventos.api.controller;

import br.com.gabrielferreira.eventos.api.mapper.LoginMapper;
import br.com.gabrielferreira.eventos.api.model.LoginModel;
import br.com.gabrielferreira.eventos.api.model.input.LoginInputModel;
import br.com.gabrielferreira.eventos.domain.model.Usuario;
import br.com.gabrielferreira.eventos.domain.service.security.TokenService;
import br.com.gabrielferreira.eventos.domain.service.security.UsuarioAutenticacaoService;
import br.com.gabrielferreira.eventos.domain.service.security.model.InformacoesTokenModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login Controller", description = "Endpoints para realizar login")
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UsuarioAutenticacaoService usuarioAutenticacaoService;

    private final LoginMapper loginMapper;

    @Operation(summary = "Logar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário logado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Regra de negócio",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<LoginModel> login(@Valid @RequestBody LoginInputModel loginInputModel){
        UsernamePasswordAuthenticationToken dadosLogin = new UsernamePasswordAuthenticationToken(loginInputModel.getEmail(), loginInputModel.getSenha());
        Authentication authentication =  authenticationManager.authenticate(dadosLogin);

        InformacoesTokenModel informacoesTokenModel = tokenService.gerarLoginToken((Usuario) authentication.getPrincipal());
        LoginModel loginModel = loginMapper.toLoginModel(informacoesTokenModel);
        return ResponseEntity.ok(loginModel);
    }

    @Operation(summary = "Refresh token do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token atualizado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginModel.class)) }),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado",
                    content = @Content)
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<LoginModel> refreshToken(){
        Usuario usuario = usuarioAutenticacaoService.usuarioAutenticado();

        InformacoesTokenModel informacoesTokenModel = tokenService.gerarLoginToken(usuario);
        LoginModel loginModel = loginMapper.toLoginModel(informacoesTokenModel);
        return ResponseEntity.ok(loginModel);
    }
}
