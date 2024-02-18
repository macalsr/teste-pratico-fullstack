package com.simplesdental.contatosapi.service.impl;

import com.simplesdental.contatosapi.model.Profissional;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import com.simplesdental.contatosapi.model.mapper.ProfissionalMapper;
import com.simplesdental.contatosapi.repository.ProfissionalRepository;
import com.simplesdental.contatosapi.service.ProfissionalService;
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
public class ProfissionalServiceImpl implements ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;


    /**
     * Cria um novo profissional.
     *
     * @param profissionalDTO DTO do profissional a ser criado.
     * @return DTO do profissional criado.
     */
    @Override
    public Profissional createProfissional(ProfissionalDTO profissionalDTO) {
        Profissional profissional = ProfissionalMapper.toModel(profissionalDTO);
        profissional.setCreatedDate(LocalDateTime.now());
        return profissionalRepository.save(profissional);
    }

    /**
     * Busca um profissional pelo ID.
     *
     * @param id ID do profissional a ser buscado.
     * @return DTO do profissional encontrado.
     * @throws ResponseStatusException se o profissional não for encontrado.
     */
    @Override
    public Profissional findById(Integer id) {
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));
    }

    /**
     * Busca uma lista de profissionais com base nos critérios especificados.
     *
     * @param q      Critério de busca.
     * @param fields Campos a serem retornados.
     * @return Lista de DTOs dos profissionais encontrados.
     */
    @Override
    public List<Profissional> findProfissionais(String q, List<String> fields) {
        List<Profissional> allProfissionais = profissionalRepository.findAll();

        List<Profissional> filteredProfissionais = allProfissionais;
        if (q != null && !q.isEmpty()) {
            filteredProfissionais = allProfissionais.stream()
                    .filter(profissional -> profissionalMatchesCriteria(profissional, q))
                    .collect(Collectors.toList());
        }
        return filteredProfissionais.stream()
                .map(profissional -> ProfissionalMapper.mapProfissionais(profissional, fields))
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
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));

        BeanUtils.copyProperties(profissionalDTO, profissional, "createdDate");
        Profissional updatedProfissional = profissionalRepository.save(profissional);
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
        if (!profissionalRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado");
        }
        profissionalRepository.deleteById(id);
    }
}
