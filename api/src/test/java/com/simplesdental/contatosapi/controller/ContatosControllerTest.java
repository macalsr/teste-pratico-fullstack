package com.simplesdental.contatosapi.controller;

import com.simplesdental.contatosapi.model.CargoEnum;
import com.simplesdental.contatosapi.model.dto.ContatoDTO;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
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
        List<ContatoDTO> expectedContatos = Collections.emptyList();
        when(contatoService.findContatos(null, null)).thenReturn(expectedContatos);

        ResponseEntity<List<ContatoDTO>> response = contatosController.getContatos(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedContatos, response.getBody());
        verify(contatoService, times(1)).findContatos(null, null);
    }
    @Test
    void getContatos_WhenCalled_ReturnsListOfContatos() {
        List<ContatoDTO> expectedContatos = Collections.emptyList();
        when(contatoService.findContatos("Maria", Arrays.asList("nome", "contato"))).thenReturn(expectedContatos);

        ResponseEntity<List<ContatoDTO>> response = contatosController.getContatos("Maria", Arrays.asList("nome", "contato"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedContatos, response.getBody());
        verify(contatoService, times(1)).findContatos("Maria", Arrays.asList("nome", "contato"));
    }
    @Test
    void getContatoById_WhenValidId_ReturnsContato() {
        int id = 1;
        ContatoDTO expectedContato = new ContatoDTO();
        when(contatoService.findById(id)).thenReturn(expectedContato);

        ResponseEntity<ContatoDTO> response = contatosController.getContatoById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedContato, response.getBody());
        verify(contatoService, times(1)).findById(id);
    }

    @Test
    public void testCreateContato() {
        ContatoDTO mockContato = new ContatoDTO();
        when(contatoService.createContato(any())).thenReturn(mockContato);

        ResponseEntity<String> response = contatosController.createContato(new ContatoDTO());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sucesso contato com id " + mockContato.getId() + " cadastrado", response.getBody());
    }


    @Test
    public void testUpdateContato_Success() {
        ProfissionalDTO profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setId(1);
        profissionalDTO.setNome("Joao");
        profissionalDTO.setNascimento(LocalDateTime.now());
        profissionalDTO.setCargo(CargoEnum.TESTER);

        ContatoDTO mockContato = new ContatoDTO();
        mockContato.setId(1);
        mockContato.setNome("Test");
        mockContato.setContato("test@test.com");
        mockContato.setProfissional(profissionalDTO);
        when(contatoService.updateContato(1, mockContato)).thenReturn(mockContato);

        ResponseEntity<String> response = contatosController.updateContato(1, mockContato);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sucesso contato alterado", response.getBody());
    }

    @Test
    public void testUpdateContato_ContatoNotFound() {
        when(contatoService.updateContato(1, new ContatoDTO()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));

        ResponseEntity<String> response = contatosController.updateContato(1, new ContatoDTO());

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
