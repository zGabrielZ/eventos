package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.CidadeRepository;
import br.com.gabrielferreira.evento.service.validation.CidadeValidator;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.gabrielferreira.evento.factory.domain.CidadeDomainFactory.*;
import static br.com.gabrielferreira.evento.factory.entity.CidadeFactory.*;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    private final CidadeValidator cidadeValidator;

    @Transactional
    public CidadeDomain cadastrarCidade(CidadeDomain cidadeDomain){
        cidadeValidator.validarCampos(cidadeDomain);
        cidadeValidator.validarNome(cidadeDomain);
        cidadeValidator.validarCodigo(cidadeDomain);

        Cidade cidade = toCreateCidade(cidadeDomain);
        cidade = cidadeRepository.save(cidade);
        return toCidadeDomain(cidade);
    }

    public List<CidadeDomain> buscarCidades(){
        List<Cidade> cidades = cidadeRepository.buscarCidades();
        return toCidadesDomains(cidades);
    }

    public CidadeDomain buscarCidadePorId(Long id){
        Cidade cidade = cidadeRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Cidade não encontrada"));
        return toCidadeDomain(cidade);
    }

    public CidadeDomain buscarCidadePorCodigo(String codigo){
        if(StringUtils.isBlank(codigo)){
            throw new MsgException("É necessário informar o código");
        }

        Cidade cidade = cidadeRepository.buscarCidadePorCodigo(codigo)
                .orElseThrow(() -> new NaoEncontradoException("Cidade não encontrada"));
        return toCidadeDomain(cidade);
    }
}
