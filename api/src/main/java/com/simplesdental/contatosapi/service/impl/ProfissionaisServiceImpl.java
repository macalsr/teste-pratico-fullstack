package com.simplesdental.contatosapi.service.impl;

import com.simplesdental.contatosapi.model.Profissional;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import com.simplesdental.contatosapi.model.mapper.ProfissionalMapper;
import com.simplesdental.contatosapi.repository.ProfissionaisRepository;
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
 * Implementação do serviço para operações relacionadas a profissionais.
 */
@Service
public class ProfissionaisServiceImpl implements ProfissionaisService {

    @Autowired
    private ProfissionaisRepository profissionaisRepository;


    /**
     * Cria um novo profissional.
     *
     * @param profissionalDTO DTO do profissional a ser criado.
     * @return DTO do profissional criado.
     */
    @Override
    public ProfissionalDTO createProfissional(ProfissionalDTO profissionalDTO) {
        Profissional profissional = ProfissionalMapper.toModel(profissionalDTO);
        profissional.setCreatedDate(LocalDateTime.now());
        return ProfissionalMapper.toDTO(profissionaisRepository.save(profissional));
    }

    /**
     * Busca um profissional pelo ID.
     *
     * @param id ID do profissional a ser buscado.
     * @return DTO do profissional encontrado.
     * @throws ResponseStatusException se o profissional não for encontrado.
     */
    @Override
    public ProfissionalDTO findById(Integer id) {
        Profissional profissional = profissionaisRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));
        return ProfissionalMapper.toDTO(profissional);
    }

    /**
     * Busca uma lista de profissionais com base nos critérios especificados.
     *
     * @param q      Critério de busca.
     * @param fields Campos a serem retornados.
     * @return Lista de DTOs dos profissionais encontrados.
     */
    @Override
    public List<ProfissionalDTO> findProfissionais(String q, List<String> fields) {
        List<Profissional> allProfissionais = profissionaisRepository.findAll();

        List<Profissional> filteredProfissionais = allProfissionais;
        if (q != null && !q.isEmpty()) {
            filteredProfissionais = allProfissionais.stream()
                    .filter(profissional -> profissionalMatchesCriteria(profissional, q))
                    .collect(Collectors.toList());
        }
        return filteredProfissionais.stream()
                .map(profissional -> ProfissionalMapper.mapProfissionaisToDTO(profissional, fields))
                .collect(Collectors.toList());
    }

    /**
     * Verifica se um profissional corresponde ao critério de busca.
     *
     * @param profissional Profissional a ser verificado.
     * @param q            Critério de busca.
     * @return true se o profissional corresponder ao critério, false caso contrário.
     */
    private boolean profissionalMatchesCriteria(Profissional profissional, String q) {
        if (profissional.getNome().toLowerCase().contains(q.toLowerCase())) {
            return true;
        }

        if (profissional.getCargo().toString().equals(q.toUpperCase())) {
            return true;
        }
        if (profissional.getNascimento().toString().equalsIgnoreCase(q)) {
            return true;
        }
        if (profissional.getCreatedDate().toString().equalsIgnoreCase(q)) {
            return true;
        }
        try {
            int id = Integer.parseInt(q);
            return profissional.getId() == id;
        } catch (NumberFormatException e) {
            // Ignore parsing exceptions, as q might not be an integer
        }
        return false;
    }

    /**
     * Atualiza um profissional existente.
     *
     * @param id              ID do profissional a ser atualizado.
     * @param profissionalDTO DTO com os novos dados do profissional.
     * @return DTO do profissional atualizado.
     * @throws ResponseStatusException se o profissional não for encontrado.
     */
    @Override
    public ProfissionalDTO updateProfissional(Integer id, ProfissionalDTO profissionalDTO) {
        Profissional profissional = profissionaisRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));

        BeanUtils.copyProperties(profissionalDTO, profissional, "createdDate");
        Profissional updatedProfissional = profissionaisRepository.save(profissional);
        return ProfissionalMapper.toDTO(updatedProfissional);
    }

    /**
     * /**
     * Exclui um profissional pelo ID.
     *
     * @param id ID do profissional a ser excluído.
     * @throws ResponseStatusException se o profissional não for encontrado.
     */
    @Override
    public void deleteProfissional(Integer id) {
        if (!profissionaisRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado");
        }
        profissionaisRepository.deleteById(id);
    }
}
