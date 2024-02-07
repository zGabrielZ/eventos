package br.com.gabrielferreira.eventos.domain.service;

import br.com.gabrielferreira.eventos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.eventos.domain.model.Cidade;
import br.com.gabrielferreira.eventos.domain.model.Evento;
import br.com.gabrielferreira.eventos.domain.model.Usuario;
import br.com.gabrielferreira.eventos.domain.repository.EventoRepository;
import br.com.gabrielferreira.eventos.domain.service.validator.EventoValidator;
import br.com.gabrielferreira.eventos.integration.model.CepIntegrationModel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    private final EventoValidator eventoValidator;

    private final EnderecoService enderecoService;

    private final UsuarioService usuarioService;

    @Transactional
    public Evento cadastrarEvento(Long idUsuario, Evento evento){
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);

        eventoValidator.validarCampos(evento);
        eventoValidator.validarNomeEvento(evento.getId(), evento.getNome());

        CepIntegrationModel cepIntegrationModel = enderecoService.buscarCep(evento.getCidade().getCep());
        preencherCamposCep(cepIntegrationModel, evento.getCidade());

        evento.setUsuario(usuario);
        evento = eventoRepository.save(evento);
        return evento;
    }

    public Evento buscarEventoPorId(Long idUsuario, Long id){
        return eventoRepository.buscarPorId(idUsuario, id)
                .orElseThrow(() -> new NaoEncontradoException("Evento não encontrado"));
    }

    @Transactional
    public Evento atualizarEventoPorId(Long idUsuario, Long id, Evento evento){
        Evento eventoEncontrado = buscarEventoPorId(idUsuario, id);

        eventoValidator.validarCampos(evento);
        eventoValidator.validarNomeEvento(eventoEncontrado.getId(), evento.getNome());

        preencherCamposEvento(eventoEncontrado, evento);

        eventoEncontrado = eventoRepository.save(eventoEncontrado);
        return eventoEncontrado;
    }

    @Transactional
    public void deletarEventoPorId(Long idUsuario, Long id){
        Evento eventoEncontrado = buscarEventoPorId(idUsuario, id);
        eventoRepository.delete(eventoEncontrado);
    }

    private void preencherCamposCep(CepIntegrationModel cepIntegrationModel, Cidade cidade){
        cidade.setLogradouro(cepIntegrationModel.getLogradouro());
        cidade.setComplemento(StringUtils.isNotBlank(cidade.getComplemento()) ? cidade.getComplemento() : cepIntegrationModel.getComplemento());
        cidade.setBairro(cepIntegrationModel.getBairro());
        cidade.setLocalidade(cepIntegrationModel.getLocalidade());
        cidade.setUf(cepIntegrationModel.getUf());
        cidade.setIbge(cepIntegrationModel.getIbge());
        cidade.setGia(cepIntegrationModel.getGia());
        cidade.setDdd(cepIntegrationModel.getDdd());
        cidade.setSiafi(cepIntegrationModel.getSiafi());
    }

    private void preencherCamposEvento(Evento eventoEncontrado, Evento evento){
        eventoEncontrado.setNome(evento.getNome());
        eventoEncontrado.setDataEvento(evento.getDataEvento());
        eventoEncontrado.setUrl(evento.getUrl());
        eventoEncontrado.getCidade().setComplemento(StringUtils.isNotBlank(evento.getCidade().getComplemento()) ? evento.getCidade().getComplemento() : eventoEncontrado.getCidade().getComplemento());

        if(!eventoEncontrado.getCidade().getCep().equals(evento.getCidade().getCep())){
            CepIntegrationModel cepIntegrationModel = enderecoService.buscarCep(evento.getCidade().getCep());
            preencherCamposCep(cepIntegrationModel, evento.getCidade());
            preencherCamposCidade(eventoEncontrado.getCidade(), evento.getCidade());
        }
    }

    private void preencherCamposCidade(Cidade cidadeEncontrado, Cidade cidade){
        cidadeEncontrado.setCep(cidade.getCep());
        cidadeEncontrado.setLogradouro(cidade.getLogradouro());
        cidadeEncontrado.setComplemento(cidade.getComplemento());
        cidadeEncontrado.setBairro(cidade.getBairro());
        cidadeEncontrado.setLocalidade(cidade.getLocalidade());
        cidadeEncontrado.setUf(cidade.getUf());
        cidadeEncontrado.setIbge(cidade.getIbge());
        cidadeEncontrado.setGia(cidade.getGia());
        cidadeEncontrado.setDdd(cidade.getDdd());
        cidadeEncontrado.setSiafi(cidade.getSiafi());
    }
}
