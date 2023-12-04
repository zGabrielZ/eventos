package br.com.gabrielferreira.evento.service.validation;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.repository.CidadeRepository;
import br.com.gabrielferreira.evento.repository.projection.CidadeProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CidadeValidator {

    private final CidadeRepository cidadeRepository;

    public void validarCampos(CidadeDomain cidadeDomain){
        cidadeDomain.setNome(cidadeDomain.getNome().trim());
        cidadeDomain.setCodigo(cidadeDomain.getCodigo().trim());
    }

    public void validarNome(CidadeDomain cidadeDomain){
        Optional<CidadeProjection> cidadeEncontrado = cidadeRepository.existeNomeCidade(cidadeDomain.getNome());
        if(cidadeDomain.getId() == null && cidadeEncontrado.isPresent()){
            throw new MsgException(String.format("Não vai ser possível cadastrar esta cidade pois o nome '%s' já foi cadastrado", cidadeDomain.getNome()));
        } else if(cidadeDomain.getId() != null && cidadeEncontrado.isPresent() && !cidadeDomain.getId().equals(cidadeEncontrado.get().getId())){
            throw new MsgException(String.format("Não vai ser possível atualizar esta cidade pois o nome '%s' já foi cadastrado", cidadeDomain.getNome()));
        }
    }

    public void validarCodigo(CidadeDomain cidadeDomain){
        validarCampoCodigo(cidadeDomain.getCodigo());
        Optional<CidadeProjection> cidadeEncontrado = cidadeRepository.existeCodigoCidade(cidadeDomain.getCodigo());
        if(cidadeDomain.getId() == null && cidadeEncontrado.isPresent()){
            throw new MsgException(String.format("Não vai ser possível cadastrar esta cidade pois o código '%s' já foi cadastrado", cidadeDomain.getCodigo()));
        } else if(cidadeDomain.getId() != null && cidadeEncontrado.isPresent() && !cidadeDomain.getId().equals(cidadeEncontrado.get().getId())){
            throw new MsgException(String.format("Não vai ser possível atualizar esta cidade pois o código '%s' já foi cadastrado", cidadeDomain.getCodigo()));
        }
    }

    private void validarCampoCodigo(String codigo){
        boolean isEspacoEmBranco = isEspacoEmBranco(codigo);
        if(isEspacoEmBranco){
            throw new MsgException(String.format("Não vai ser possível cadastrar esta cidade pois o código '%s' possui espaço em branco", codigo));
        }

        boolean isTodasLetrasMaiusculas = isTodasLetrasMaiusculas(codigo);
        if(!isTodasLetrasMaiusculas){
            throw new MsgException(String.format("Não vai ser possível cadastrar esta cidade pois o código '%s' tem que ser toda maiúsculas", codigo));
        }
    }

    private boolean isEspacoEmBranco(String valor){
        for (Character caractere : valor.toCharArray()) {
            if (Character.isWhitespace(caractere)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTodasLetrasMaiusculas(String valor){
        return valor.equals(valor.toUpperCase());
    }
}
