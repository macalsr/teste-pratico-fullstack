package com.simplesdental.contatosapi.controller;

import com.simplesdental.contatosapi.model.dto.ContatoDTO;
import com.simplesdental.contatosapi.service.ContatoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciamento de contatos.
 */
@RestController
@Tag(name = "Contacts", description = "APIs de gerenciamento de Contatos")
@RequestMapping("/contatos")
public class ContatosController {

    @Autowired
    private ContatoService contatoService;

    /**
     * Retorna uma lista de contatos com base nos critérios especificados.
     *
     * @param q      Filtro de busca.
     * @param fields Campos a serem retornados.
     * @return Lista de contatos encontrados.
     */
    @GetMapping
    public ResponseEntity<List<ContatoDTO>> getContatos(@RequestParam(required = false) String q,
                                                        @RequestParam(required = false) List<String> fields) {
        try {
            List<ContatoDTO> contatos = contatoService.findContatos(q, fields);
            return ResponseEntity.ok(contatos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Retorna um contato pelo ID.
     *
     * @param id ID do contato.
     * @return Contato encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContatoDTO> getContatoById(@PathVariable Integer id) {
        try {
            ContatoDTO contato = contatoService.findById(id);
            return contato != null ? ResponseEntity.ok(contato) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Cria um novo contato.
     *
     * @param contatoDTO Dados do contato a serem cadastrados.
     * @return Mensagem de sucesso.
     */
    @PostMapping
    public ResponseEntity<String> createContato(@RequestBody ContatoDTO contatoDTO) {
        try {
            ContatoDTO savedContato = contatoService.createContato(contatoDTO);
            return ResponseEntity.ok("Sucesso contato com id " + savedContato.getId() + " cadastrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Atualiza um contato existente.
     *
     * @param id         ID do contato a ser atualizado.
     * @param contatoDTO Novos dados do contato.
     * @return Mensagem de sucesso.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateContato(@PathVariable Integer id, @RequestBody ContatoDTO contatoDTO) {
        try {
            ContatoDTO updatedContato = contatoService.updateContato(id, contatoDTO);
            return updatedContato != null ? ResponseEntity.ok("Sucesso contato alterado") : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Exclui um contato pelo ID.
     *
     * @param id ID do contato a ser excluído.
     * @return Mensagem de sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContato(@PathVariable Integer id) {
        try {
            contatoService.deleteContato(id);
            return ResponseEntity.ok("Sucesso contato excluído");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
