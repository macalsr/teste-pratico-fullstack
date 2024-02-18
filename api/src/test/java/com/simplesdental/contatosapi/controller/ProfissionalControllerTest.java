package com.simplesdental.contatosapi.controller;

import com.simplesdental.contatosapi.model.CargoEnum;
import com.simplesdental.contatosapi.model.Profissional;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import com.simplesdental.contatosapi.repository.ProfissionaisRepository;
import com.simplesdental.contatosapi.service.ProfissionaisService;
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
public class ProfissionalControllerTest {

    @Mock
    private ProfissionaisService profissionaisService;

    @InjectMocks
    private ProfissionalController profissionalController;

    @Mock
    private ProfissionaisRepository repository;
    @Test
    void getProfissionais_WhenCalled_ReturnsListOfProfissionaisVazio() {
        List<Profissional> expectedProfissionais = Collections.emptyList();
        when(profissionaisService.findProfissionais(null, null)).thenReturn(expectedProfissionais);

        ResponseEntity<List<Profissional>> response = profissionalController.getProfissionais(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProfissionais, response.getBody());
        verify(profissionaisService, times(1)).findProfissionais(null, null);
    }

    @Test
    void getProfissionais_WhenCalled_ReturnsListOfProfissionais() {
        List<Profissional> expectedProfissionais = Collections.emptyList();
        when(profissionaisService.findProfissionais("Maria", Arrays.asList("nome", "cargo"))).thenReturn(expectedProfissionais);

        ResponseEntity<List<Profissional>> response = profissionalController.getProfissionais("Maria", Arrays.asList("nome", "cargo"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProfissionais, response.getBody());
        verify(profissionaisService, times(1)).findProfissionais("Maria", Arrays.asList("nome", "cargo"));
    }

    @Test
    void getProfissionaisById_WhenValidId_ReturnsProfissionais() {
        int id = 1;
        Profissional expectedProfissional = new Profissional();
        when(profissionaisService.findById(id)).thenReturn(expectedProfissional);

        ResponseEntity<Profissional> response = profissionalController.getProfissionalById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProfissional, response.getBody());
        verify(profissionaisService, times(1)).findById(id);
    }

    @Test
    public void testCreateProfissional() {
        Profissional profissional = new Profissional();
        when(profissionaisService.createProfissional(any())).thenReturn(profissional);

        ResponseEntity<String> response = profissionalController.createProfissional(new ProfissionalDTO());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Profissional cadastrado com sucesso: " + profissional.getNome(), response.getBody());
    }

    @Test
    public void testUpdateProfissional_Success() {
        ProfissionalDTO profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setNome("Joao");
        profissionalDTO.setNascimento(LocalDateTime.now());
        profissionalDTO.setCargo(CargoEnum.TESTER);

        when(profissionaisService.updateProfissional(1, profissionalDTO)).thenReturn(profissionalDTO);

        ResponseEntity<String> response = profissionalController.updateProfissional(1, profissionalDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Profissional atualizado com sucesso", response.getBody());
    }

    @Test
    public void testUpdateProfissional_ProfissionalNotFound() {
        when(profissionaisService.updateProfissional(1, new ProfissionalDTO()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));

        ResponseEntity<String> response = profissionalController.updateProfissional(1, new ProfissionalDTO());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteProfissional_ProfissionalExists() {
        ResponseEntity<String> response = profissionalController.deleteProfissional(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(profissionaisService).deleteProfissional(1);
    }

    @Test
    public void testDeleteProfissional_ProfissionalNotFound() {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"))
                .when(profissionaisService).deleteProfissional(1);

        ResponseEntity<String> response = profissionalController.deleteProfissional(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
