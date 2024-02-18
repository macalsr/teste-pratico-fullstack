package com.simplesdental.contatosapi.service;

import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import java.util.List;

/**
 * Interface de serviço para operações relacionadas aos profissionais.
 */
public interface ProfissionaisService {

    /**
     * Cria um novo profissional.
     *
     * @param profissionalDTO O objeto ProfissionalDTO representando o novo profissional a ser criado.
     * @return O objeto ProfissionalDTO representando o profissional recém-criado.
     */
    ProfissionalDTO createProfissional(ProfissionalDTO profissionalDTO);

    /**
     * Encontra um profissional pelo ID.
     *
     * @param id O ID do profissional a ser encontrado.
     * @return O objeto ProfissionalDTO representando o profissional encontrado.
     */
    ProfissionalDTO findById(Integer id);


    /**
     * Encontra profissionais com base em um termo de pesquisa e campos específicos a serem retornados.
     *
     * @param q      O termo de pesquisa.
     * @param fields A lista de campos específicos a serem retornados.
     * @return Uma lista de objetos ProfissionalDTO representando os profissionais encontrados.
     */
    List<ProfissionalDTO> findProfissionais(String q, List<String> fields);

    /**
     * Atualiza um profissional existente.
     *
     * @param id              O ID do profissional a ser atualizado.
     * @param profissionalDTO O objeto ProfissionalDTO contendo as informações atualizadas do profissional.
     * @return O objeto ProfissionalDTO representando o profissional atualizado.
     */
    ProfissionalDTO updateProfissional(Integer id, ProfissionalDTO profissionalDTO);

    /**
     * Exclui um profissional existente.
     *
     * @param id O ID do profissional a ser excluído.
     */
    void deleteProfissional(Integer id);
}
