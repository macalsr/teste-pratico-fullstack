package com.simplesdental.contatosapi.service;

import com.simplesdental.contatosapi.model.dto.ContatoDTO;
import java.util.List;

/**
 * Interface de serviço para operações relacionadas aos contatos.
 */
public interface ContatoService {

    /**
     * Cria um novo contato.
     *
     * @param contato O objeto ContatoDTO representando o novo contato a ser criado.
     * @return O objeto ContatoDTO representando o contato recém-criado.
     */
    ContatoDTO createContato(ContatoDTO contato);

    /**
     * Encontra um contato pelo ID.
     *
     * @param id O ID do contato a ser encontrado.
     * @return O objeto ContatoDTO representando o contato encontrado.
     */
    ContatoDTO findById(Integer id);

    /**
     * Encontra contatos com base em um termo de pesquisa e campos específicos a serem retornados.
     *
     * @param q      O termo de pesquisa.
     * @param fields A lista de campos específicos a serem retornados.
     * @return Uma lista de objetos ContatoDTO representando os contatos encontrados.
     */
    List<ContatoDTO> findContatos(String q, List<String> fields);

    /**
     * Atualiza um contato existente.
     *
     * @param id         O ID do contato a ser atualizado.
     * @param contatoDTO O objeto ContatoDTO contendo as informações atualizadas do contato.
     * @return O objeto ContatoDTO representando o contato atualizado.
     */
    ContatoDTO updateContato(Integer id, ContatoDTO contatoDTO);

    /**
     * Exclui um contato existente.
     *
     * @param id O ID do contato a ser excluído.
     */
    void deleteContato(Integer id);
}
