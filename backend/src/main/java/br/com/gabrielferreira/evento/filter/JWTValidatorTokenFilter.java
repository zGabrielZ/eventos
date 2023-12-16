package br.com.gabrielferreira.evento.filter;

import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.service.security.TokenService;
import br.com.gabrielferreira.evento.service.security.UsuarioAutenticacaoService;
import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTValidatorTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UsuarioAutenticacaoService usuarioAutenticacaoService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Recuperar token que foi enviado
        String token = recuperarToken(request);

        // Verificar se o token está valido, caso estiver valido, autentica o usuário
        if(tokenService.isTokenValido(token)){
            Claims claims = tokenService.extrairTodoClaims(token);
            // Caso exstir o claims, pegar o id do usuário e autenticar
            if(claims != null){
                autenticarUsuario(claims);
            }
        }


        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request){
        String headerToken = request.getHeader("Authorization");
        if(StringUtils.isBlank(headerToken) || !headerToken.startsWith("Bearer ")){
            return null;
        }
        return headerToken.substring(7);
    }

    private void autenticarUsuario(Claims claims){
        Long idUsuario = Long.valueOf(String.valueOf(claims.get("idUsuario")));
        UsuarioDomain usuarioDomain = usuarioAutenticacaoService.buscarUsuarioPorId(idUsuario);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuarioDomain, null, usuarioDomain.getPerfis());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
