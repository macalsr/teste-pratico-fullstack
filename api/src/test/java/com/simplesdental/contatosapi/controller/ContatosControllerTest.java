package com.simplesdental.contatosapi.controller;

import com.simplesdental.contatosapi.model.CargoEnum;
import com.simplesdental.contatosapi.model.Profissional;
import com.simplesdental.contatosapi.model.dto.ContatoReceiver;
import com.simplesdental.contatosapi.model.dto.ContatoResponse;
import com.simplesdental.contatosapi.repository.ContatoRepository;
import com.simplesdental.contatosapi.service.ContatoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContatosControllerTest {

    @Mock
    private ContatoService contatoService;

    @InjectMocks
    private ContatosController contatosController;

    @Mock
    private ContatoRepository repository;


    @Test
    void getContatos_WhenCalled_ReturnsListOfContatosVazio() {
        List<ContatoResponse> expectedContatos = Collections.emptyList();
        when(contatoService.findContatos(null, null)).thenReturn(expectedContatos);

        ResponseEntity<List<ContatoResponse>> response = contatosController.getContatos(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedContatos, response.getBody());
        verify(contatoService, times(1)).findContatos(null, null);
    }
    @Test
    void getContatos_WhenCalled_ReturnsListOfContatos() {
        List<ContatoResponse> expectedContatos = Collections.emptyList();
        when(contatoService.findContatos("Maria", Arrays.asList("nome", "contato"))).thenReturn(expectedContatos);

        ResponseEntity<List<ContatoResponse>> response = contatosController.getContatos("Maria", Arrays.asList("nome", "contato"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedContatos, response.getBody());
        verify(contatoService, times(1)).findContatos("Maria", Arrays.asList("nome", "contato"));
    }
    @Test
    void getContatoById_WhenValidId_ReturnsContato() {
        int id = 1;
        ContatoResponse expectedContato = new ContatoResponse();
        when(contatoService.findById(id)).thenReturn(expectedContato);

        ResponseEntity<ContatoResponse> response = contatosController.getContatoById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedContato, response.getBody());
        verify(contatoService, times(1)).findById(id);
    }

    @Test
    public void testCreateContato() {
        ContatoResponse mockContato = new ContatoResponse();
        when(contatoService.createContato(any())).thenReturn(mockContato);

        ResponseEntity<String> response = contatosController.createContato(new ContatoReceiver());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sucesso contato com id " + mockContato.getNome() + " cadastrado", response.getBody());
    }


    @Test
    public void testUpdateContato_Success() {
        Profissional profissional = new Profissional();
        profissional.setNome("Joao");
        profissional.setNascimento(LocalDateTime.now());
        profissional.setCargo(CargoEnum.TESTER);

        ContatoReceiver mockContato = new ContatoReceiver();
        mockContato.setNome("Test");
        mockContato.setContato("test@test.com");
        mockContato.setIdProfissional(profissional.getId());
        when(contatoService.updateContato(1, mockContato)).thenReturn(mockContato);

        ResponseEntity<String> response = contatosController.updateContato(1, mockContato);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sucesso contato alterado", response.getBody());
    }

    @Test
    public void testUpdateContato_ContatoNotFound() {
        when(contatoService.updateContato(1, new ContatoReceiver()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));

        ResponseEntity<String> response = contatosController.updateContato(1, new ContatoReceiver());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    public void testDeleteContato_ContatoExists() {
        ResponseEntity<String> response = contatosController.deleteContato(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(contatoService).deleteContato(1);
    }

    @Test
    public void testDeleteContato_ContatoNotFound() {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"))
                .when(contatoService).deleteContato(1);

        ResponseEntity<String> response = contatosController.deleteContato(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
