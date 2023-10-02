package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.domain.CidadeDomain;
import br.com.gabrielferreira.evento.entity.Cidade;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.mapper.domain.CidadeDomainMapper;
import br.com.gabrielferreira.evento.repository.CidadeRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    private final CidadeDomainMapper cidadeDomainMapper;

    public List<CidadeDomain> buscarCidades(){
        List<Cidade> cidades = cidadeRepository.buscarCidades();
        return cidadeDomainMapper.toCidadesDomains(cidades);
    }

    public CidadeDomain buscarCidadePorId(Long id){
        Cidade cidade = cidadeRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Cidade não encontrada"));
        return cidadeDomainMapper.toCidadeDomain(cidade);
    }

    public CidadeDomain buscarCidadePorCodigo(String codigo){
        if(StringUtils.isBlank(codigo)){
            throw new MsgException("É necessário informar o código");
        }

        Cidade cidade = cidadeRepository.buscarCidadePorCodigo(codigo)
                .orElseThrow(() -> new NaoEncontradoException("Cidade não encontrada"));
        return cidadeDomainMapper.toCidadeDomain(cidade);
    }
}
