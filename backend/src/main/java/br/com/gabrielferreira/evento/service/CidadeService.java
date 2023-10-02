package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.factory.domain.CidadeDomainFactory;
import br.com.gabrielferreira.evento.repository.CidadeRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    private final CidadeDomainFactory cidadeDomainFactory;

    public List<CidadeDomain> buscarCidades(){
        return cidadeDomainFactory.toCidadesDomains(cidadeRepository.buscarCidades());
    }

    public CidadeDomain buscarCidadePorId(Long id){
        return cidadeDomainFactory.toCidadeDomain(buscarCidade(id));
    }

    public CidadeDomain buscarCidadePorCodigo(String codigo){
        if(StringUtils.isBlank(codigo)){
            throw new MsgException("É necessário informar o código");
        }

        Cidade cidade = cidadeRepository.buscarCidadePorCodigo(codigo)
                .orElseThrow(() -> new NaoEncontradoException("Cidade não encontrada"));
        return cidadeDomainFactory.toCidadeDomain(cidade);
    }

    private Cidade buscarCidade(Long id){
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Cidade não encontrada"));
    }
}
