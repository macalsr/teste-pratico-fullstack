package com.simplesdental.contatosapi.controller;

import com.simplesdental.contatosapi.model.dto.ContatoDTO;
import com.simplesdental.contatosapi.service.ContatoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContatosControllerTest {

    @Mock
    private ContatoService contatoService;

    @InjectMocks
    private ContatosController contatosController;

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
        when(contatoService.findContatos(null, null)).thenReturn(expectedContatos);

        ResponseEntity<List<ContatoDTO>> response = contatosController.getContatos(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedContatos, response.getBody());
        verify(contatoService, times(1)).findContatos(null, null);
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

}
