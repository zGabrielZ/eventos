package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.dto.CidadeDTO;
import br.com.gabrielferreira.evento.entities.Cidade;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.CidadeRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import static br.com.gabrielferreira.evento.dto.factory.CidadeDTOFactory.*;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public List<CidadeDTO> buscarCidades(){
        return toCidadesDtos(cidadeRepository.buscarCidades());
    }

    public CidadeDTO buscarCidadePorId(Long id){
        return toCidadeDto(buscarCidade(id));
    }

    public CidadeDTO buscarCidadePorCodigo(String codigo){
        if(StringUtils.isBlank(codigo)){
            throw new MsgException("É necessário informar o código");
        }

        Cidade cidade = cidadeRepository.buscarCidadePorCodigo(codigo)
                .orElseThrow(() -> new NaoEncontradoException("Cidade não encontrada"));
        return toCidadeDto(cidade);
    }

    public Cidade buscarCidade(Long id){
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Cidade não encontrada"));
    }
}
