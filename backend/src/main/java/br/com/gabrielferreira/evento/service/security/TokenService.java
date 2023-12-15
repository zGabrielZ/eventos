package br.com.gabrielferreira.evento.service.security;

import br.com.gabrielferreira.evento.domain.LoginDomain;
import br.com.gabrielferreira.evento.domain.UsuarioDomain;
import br.com.gabrielferreira.evento.exception.MsgException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

import static br.com.gabrielferreira.evento.utils.DataUtils.*;
import static br.com.gabrielferreira.evento.factory.domain.LoginDomainFactory.*;

@Service
public class TokenService {

    private final String chave;

    private final String expiracao;

    public TokenService(@Value("${jwt.secret}") String chave, @Value("${jwt.expiration}") String expiracao) {
        this.chave = chave;
        this.expiracao = expiracao;
    }

    public LoginDomain gerarLoginToken(Authentication authentication){
        String token = gerarToken(authentication);

        if(isTokenValido(token)){
            Claims claims = extrairTodoClaims(token);
            Long idUsuario = Long.valueOf(claims.get("idUsuario").toString());
            long milisegundoDataInicio = Long.parseLong(String.valueOf(claims.get("dataAtualMilisegundo")));
            ZonedDateTime dataInicio = Instant.ofEpochMilli(milisegundoDataInicio).atZone(FUSO_HORARIO_PADRAO_SISTEMA);

            long milisegundoDataFim = Long.parseLong(String.valueOf(claims.get("dataExpiracaoMilisegundo")));
            ZonedDateTime dataFim = Instant.ofEpochMilli(milisegundoDataFim).atZone(FUSO_HORARIO_PADRAO_SISTEMA);

            return toLoginDomain(idUsuario, "Bearer", token, dataInicio, dataFim);
        } else {
            throw new MsgException("Erro ao logar no sistema");
        }
    }

    private String gerarToken(Authentication authentication){
        SecretKey secretKey = getSecret();
        ZonedDateTime dataAtual = ZonedDateTime.now(UTC);
        ZonedDateTime dataExpiracao = dataAtual.plus(Duration.ofMillis(Long.parseLong(expiracao)));
        UsuarioDomain usuarioDomain = (UsuarioDomain) authentication.getPrincipal();

        return Jwts.builder()
                .issuer("API Evento")
                .subject("API de Eventos")
                .issuedAt(Date.from(dataAtual.toInstant()))
                .expiration(Date.from(dataExpiracao.toInstant()))
                .claim("idUsuario", usuarioDomain.getId())
                .claim("dataAtualMilisegundo", dataAtual.toInstant().toEpochMilli())
                .claim("dataExpiracaoMilisegundo", dataExpiracao.toInstant().toEpochMilli())
                .signWith(secretKey)
                .compact();
    }

    public Claims extrairTodoClaims(String token){
        return Jwts.parser().verifyWith(getSecret()).build().parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValido(String token){
        try {
            extrairTodoClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }


    private SecretKey getSecret(){
        return Keys.hmacShaKeyFor(chave.getBytes(StandardCharsets.UTF_8));
    }
}
