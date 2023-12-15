package br.com.gabrielferreira.evento.config;

import br.com.gabrielferreira.evento.exception.handler.ServiceHandlerAutenticacao;
import br.com.gabrielferreira.evento.exception.handler.ServiceHandlerPermissao;
import br.com.gabrielferreira.evento.service.security.UsuarioAutenticacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UsuarioAutenticacaoService usuarioAutenticacaoService;

    private final ObjectMapper objectMapper;

    //Config seguranca
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        return http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Não é pra criar sessão
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(new AppAuthenticationProvider(usuarioAutenticacaoService, passwordEncoder()))
                .authorizeHttpRequests(auth -> auth.requestMatchers(new MvcRequestMatcher(introspector, "/login/**")).permitAll())
                .exceptionHandling(eh -> eh.authenticationEntryPoint(new ServiceHandlerAutenticacao(objectMapper)) // Mensagem personalizada quando não for autenticado
                        .accessDeniedHandler(new ServiceHandlerPermissao(objectMapper))) // Mensagem personalizada quando não tiver permissão
                .build();
    }

    // Config a partir da autenticação
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Config de recurso estaticos
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"),
                new AntPathRequestMatcher("/**.html")));
    }

    // Config senha criptografada
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
