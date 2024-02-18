package com.simplesdental.contatosapi.service.impl;

import com.simplesdental.contatosapi.model.Contato;
import com.simplesdental.contatosapi.model.dto.ContatoReceiver;
import com.simplesdental.contatosapi.model.dto.ContatoResponse;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import com.simplesdental.contatosapi.model.mapper.ContatoMapper;
import com.simplesdental.contatosapi.model.mapper.ProfissionalMapper;
import com.simplesdental.contatosapi.repository.ContatoRepository;
import com.simplesdental.contatosapi.service.ContatoService;
import com.simplesdental.contatosapi.service.ProfissionaisService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação do serviço para operações relacionadas a contatos.
 */
@Service
public class ContatoServiceImpl implements ContatoService {

    @Autowired
    private ContatoRepository repository;

    @Autowired
    private ProfissionaisService profissionaisService;


    /**
     * Busca um contato pelo ID.
     *
     * @param id ID do contato a ser buscado.
     * @return DTO do contato encontrado.
     * @throws ResponseStatusException se o contato não for encontrado.
     */
    @Override
    public ContatoResponse findById(Integer id) {
        Contato contato = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));
        return ContatoMapper.toResponse(contato);
    }

    /**
     * Busca uma lista de contatos com base nos critérios especificados.
     *
     * @param q      Critério de busca.
     * @param fields Campos a serem retornados.
     * @return Lista de DTOs dos contatos encontrados.
     */
    @Override
    public List<ContatoResponse> findContatos(String q, List<String> fields) {
        List<Contato> allContatos = repository.findAll();

        List<Contato> filteredContatos = allContatos;
        if (q != null && !q.isEmpty()) {
            filteredContatos = allContatos.stream()
                    .filter(contatos -> contatosMatchesCriteria(contatos, q))
                    .collect(Collectors.toList());
        }
        return filteredContatos.stream()
                .map(contatos -> ContatoMapper.mapContatoToDTO(contatos, fields))
                .collect(Collectors.toList());
    }
    private boolean contatosMatchesCriteria(Contato contato, String q) {
        if (contato.getNome().toLowerCase().contains(q.toLowerCase())) {
            return true;
        }
        if (contato.getContato().equalsIgnoreCase(q)) {
            return true;
        }
        if (contato.getCreatedDate().toString().equalsIgnoreCase(q)) {
            return true;
        }
        try {
            int id = Integer.parseInt(q);
            ProfissionalDTO profissionalDTO = profissionaisService.findById(id);
            return contato.getProfissional() == ProfissionalMapper.toModel(profissionalDTO) || contato.getId() == Integer.parseInt(q);
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Cria um novo contato.
     *
     * @param contatoReceiver DTO do contato a ser criado.
     * @return DTO do contato criado.
     */
    @Override
    public ContatoReceiver createContato(ContatoReceiver contatoReceiver) {
        Contato contato = new Contato();
        contato.setCreatedDate(LocalDateTime.now());
        ProfissionalDTO profissional = profissionaisService.findById(contatoReceiver.getIdProfissional());
        contato.setProfissional(ProfissionalMapper.toModel(profissional));
        contato.setContato(contatoReceiver.getContato());
        contato.setNome(contatoReceiver.getNome());
        return ContatoMapper.toReceiver(repository.save(contato));
    }

    /**
     * Atualiza um contato existente.
     *
     * @param id          ID do contato a ser atualizado.
     * @param contatoReceiver DTO com os novos dados do contato.
     * @return DTO do contato atualizado.
     * @throws ResponseStatusException se o contato não for encontrado.
     */
    @Override
    public ContatoReceiver updateContato(Integer id, ContatoReceiver contatoReceiver) {
        Contato contato = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));
        BeanUtils.copyProperties(contatoReceiver, contato, "createdDate");
        Contato updatedContato = repository.save(contato);
        return ContatoMapper.toReceiver(updatedContato);
    }

    /**
     * Exclui um contato pelo ID.
     *
     * @param id ID do contato a ser excluído.
     * @throws ResponseStatusException se o contato não for encontrado.
     */
    @Override
    public void deleteContato(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado");
        }
        repository.deleteById(id);
    }
}
