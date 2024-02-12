package br.com.gabrielferreira.eventos.common.config;

import br.com.gabrielferreira.eventos.domain.service.security.TokenService;
import br.com.gabrielferreira.eventos.domain.service.security.UsuarioAutenticacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UsuarioAutenticacaoService usuarioAutenticacaoService;

    private final TokenService tokenService;

    // Config senha criptografada
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Config de recurso estaticos
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/**.html"), new AntPathRequestMatcher("/**.css")
                , new AntPathRequestMatcher("/**.js")));
    }

    // Config a partir da autenticação
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Config seguranca
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Não é para criar sessão
                .csrf(AbstractHttpConfigurer::disable) // Desabilitar o csrf
                .addFilterBefore(new JWTValidatorTokenFilter(tokenService, usuarioAutenticacaoService), UsernamePasswordAuthenticationFilter.class) // Verificar se o token está valido cada requisição
                .authenticationProvider(new AppAuthenticationProvider(usuarioAutenticacaoService, passwordEncoder()))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/login/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/perfis/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/enderecos/**").permitAll()
                        .anyRequest().authenticated()) // Endpoins permissão
                .build();
    }
}
