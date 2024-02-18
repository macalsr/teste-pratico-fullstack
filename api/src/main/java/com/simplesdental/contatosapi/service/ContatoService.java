package com.simplesdental.contatosapi.service;

import com.simplesdental.contatosapi.model.dto.ContatoReceiver;
import com.simplesdental.contatosapi.model.dto.ContatoResponse;

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
    ContatoReceiver createContato(ContatoReceiver contato);

    /**
     * Encontra um contato pelo ID.
     *
     * @param id O ID do contato a ser encontrado.
     * @return O objeto ContatoDTO representando o contato encontrado.
     */
    ContatoResponse findById(Integer id);

    /**
     * Encontra contatos com base em um termo de pesquisa e campos específicos a serem retornados.
     *
     * @param q      O termo de pesquisa.
     * @param fields A lista de campos específicos a serem retornados.
     * @return Uma lista de objetos ContatoDTO representando os contatos encontrados.
     */
    List<ContatoResponse> findContatos(String q, List<String> fields);

    /**
     * Atualiza um contato existente.
     *
     * @param id         O ID do contato a ser atualizado.
     * @param contatoReceiver O objeto ContatoDTO contendo as informações atualizadas do contato.
     * @return O objeto ContatoDTO representando o contato atualizado.
     */
    ContatoReceiver updateContato(Integer id, ContatoReceiver contatoReceiver);

    /**
     * Exclui um contato existente.
     *
     * @param id O ID do contato a ser excluído.
     */
    void deleteContato(Integer id);
}
