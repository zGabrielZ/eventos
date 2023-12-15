package br.com.gabrielferreira.evento.config;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.service.security.UsuarioAutenticacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioAutenticacaoService usuarioAutenticacaoService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String senha = authentication.getCredentials().toString();

        UserDetails userDetails = usuarioAutenticacaoService.loadUserByUsername(email);
        boolean isMatchSenha = passwordEncoder.matches(senha, userDetails.getPassword());
        if(!isMatchSenha){
            log.warn("Senha do e-mail {} inválida", email);
            throw new MsgException("Senha inválida");
        }
        log.info("E-mail {} combina com a senha", email);
        UsuarioDomain usuarioDomain = (UsuarioDomain) userDetails;
        return new UsernamePasswordAuthenticationToken(usuarioDomain, null, usuarioDomain.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
