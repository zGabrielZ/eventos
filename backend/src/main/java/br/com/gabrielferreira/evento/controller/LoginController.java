package br.com.gabrielferreira.evento.controller;

import br.com.gabrielferreira.evento.dto.request.LoginRequestDTO;
import br.com.gabrielferreira.evento.dto.response.LoginResponseDTO;
import br.com.gabrielferreira.evento.service.security.TokenService;
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

import static br.com.gabrielferreira.evento.factory.dto.LoginDTOFactory.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        UsernamePasswordAuthenticationToken dadosLogin = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getSenha());
        Authentication authentication =  authenticationManager.authenticate(dadosLogin);

        LoginResponseDTO token = toLoginResponseDto(tokenService.gerarLoginToken(authentication));
        return ResponseEntity.ok(token);
    }
}
