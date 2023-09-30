package br.com.gabrielferreira.evento.service;

import br.com.gabrielferreira.evento.dto.response.CidadeResponseDTO;
import br.com.gabrielferreira.evento.entity.Cidade;
import br.com.gabrielferreira.evento.exception.MsgException;
import br.com.gabrielferreira.evento.exception.NaoEncontradoException;
import br.com.gabrielferreira.evento.repository.CidadeRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import static br.com.gabrielferreira.evento.dto.response.factory.CidadeResponseDTOFactory.*;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public List<CidadeResponseDTO> buscarCidades(){
        return toCidadesResponsesDtos(cidadeRepository.buscarCidades());
    }

    public CidadeResponseDTO buscarCidadePorId(Long id){
        return toCidadeResponseDto(buscarCidade(id));
    }

    public CidadeResponseDTO buscarCidadePorCodigo(String codigo){
        if(StringUtils.isBlank(codigo)){
            throw new MsgException("É necessário informar o código");
        }

        Cidade cidade = cidadeRepository.buscarCidadePorCodigo(codigo)
                .orElseThrow(() -> new NaoEncontradoException("Cidade não encontrada"));
        return toCidadeResponseDto(cidade);
    }

    public Cidade buscarCidade(Long id){
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Cidade não encontrada"));
    }
}
