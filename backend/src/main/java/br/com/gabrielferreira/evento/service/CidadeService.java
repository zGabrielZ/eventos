package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.CidadeRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import static br.com.gabrielferreira.evento.factory.domain.v2.CidadeDomainFactory.*;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;

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
