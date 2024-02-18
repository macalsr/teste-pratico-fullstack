package com.simplesdental.contatosapi.controller;

import com.simplesdental.contatosapi.model.dto.ContatoReceiver;
import com.simplesdental.contatosapi.model.dto.ContatoResponse;
import com.simplesdental.contatosapi.service.ContatoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Tag(name = "Contacts", description = "APIs de gerenciamento de Contatos")
@RequestMapping("/contatos")
public class ContatosController {

    @Autowired
    private ContatoService contatoService;

    @GetMapping
    public ResponseEntity<List<ContatoResponse>> getContatos(@RequestParam(required = false) String q,
                                                             @RequestParam(required = false) List<String> fields) {
        try {
            List<ContatoResponse> contatos = contatoService.findContatos(q, fields);
            return ResponseEntity.ok(contatos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoResponse> getContatoById(@PathVariable Integer id) {
        try {
            ContatoResponse contato = contatoService.findById(id);
            if (contato != null) {
                return ResponseEntity.ok(contato);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> createContato(@RequestBody ContatoReceiver contatoReceiver) {
        try {
            ContatoResponse savedContato = contatoService.createContato(contatoReceiver);
            return ResponseEntity.ok("Sucesso contato com id " + savedContato.getId() + " cadastrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContato(@PathVariable Integer id, @RequestBody ContatoReceiver contatoReceiver) {
        try {
            contatoService.updateContato(id, contatoReceiver);
            return ResponseEntity.ok("Sucesso contato alterado");
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContato(@PathVariable Integer id) {
        try {
            contatoService.deleteContato(id);
            return ResponseEntity.ok("Sucesso contato excluído");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
