package com.simplesdental.contatosapi.controller;

import com.simplesdental.contatosapi.model.Profissional;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import com.simplesdental.contatosapi.service.ProfissionaisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controlador para gerenciamento de profissionais.
 */
@RestController
@Tag(name = "Profissionais", description = "APIs de gerenciamento de Profissionais")
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionaisService service;

    /**
     * Retorna uma lista de profissionais com base nos critérios especificados.
     *
     * @param q      Critério de busca.
     * @param fields Campos a serem retornados.
     * @return Lista de profissionais encontrados.
     */
    @GetMapping
    public ResponseEntity<List<Profissional>> getProfissionais(@RequestParam(required = false) String q,
                                                                  @RequestParam(required = false) List<String> fields) {
        List<Profissional> profissionais = service.findProfissionais(q, fields);
        return ResponseEntity.ok(profissionais);
    }

    /**
     * Retorna um profissional pelo ID.
     *
     * @param id ID do profissional.
     * @return Profissional encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Profissional> getProfissionalById(@PathVariable Integer id) {
        Profissional profissional = service.findById(id);
        if (profissional != null) {
            return ResponseEntity.ok(profissional);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado");
        }
    }

    /**
     * Cria um novo profissional.
     *
     * @param profissionalDTO Dados do profissional a ser criado.
     * @return Mensagem de sucesso.
     */
    @PostMapping
    public ResponseEntity<String> createProfissional(@RequestBody ProfissionalDTO profissionalDTO) {
        try {
            Profissional profissional = service.createProfissional(profissionalDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Profissional cadastrado com sucesso: " + profissional.getId());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do profissional já existe", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao cadastrar o profissional", e);
        }
    }

    /**
     * Atualiza um profissional existente.
     *
     * @param id             ID do profissional a ser atualizado.
     * @param profissionalDTO Novos dados do profissional.
     * @return Mensagem de sucesso.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfissional(@PathVariable Integer id, @RequestBody ProfissionalDTO profissionalDTO) {
        try {
            service.updateProfissional(id, profissionalDTO);
            return ResponseEntity.ok("Profissional atualizado com sucesso");
        } catch (ResponseStatusException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getReason());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getReason());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    /**
     * Exclui um profissional pelo ID.
     *
     * @param id ID do profissional a ser excluído.
     * @return Mensagem de sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfissional(@PathVariable Integer id) {
        try {
            service.deleteProfissional(id);
            return ResponseEntity.ok("Profissional excluído com sucesso");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
