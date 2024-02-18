package com.simplesdental.contatosapi.service.impl;

import com.simplesdental.contatosapi.model.CargoEnum;
import com.simplesdental.contatosapi.model.Profissional;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import com.simplesdental.contatosapi.repository.ProfissionaisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testes para a implementação do serviço de Profissionais.
 */
@ExtendWith(MockitoExtension.class)
class ProfissionaisServiceImplTest {

    @Mock
    private ProfissionaisRepository profissionaisRepository;

    @InjectMocks
    private ProfissionaisServiceImpl profissionaisService;

    private ProfissionalDTO profissionalDTO;

    @BeforeEach
    void setUp() {
        profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setNome("Joao");
        profissionalDTO.setNascimento(LocalDateTime.now());
        profissionalDTO.setCargo(CargoEnum.TESTER);
    }

    @Test
    void testCreateProfissional() {
        when(profissionaisRepository.save(any(Profissional.class))).thenReturn(new Profissional());

        ProfissionalDTO savedProfissional = profissionaisService.createProfissional(profissionalDTO);

        verify(profissionaisRepository).save(any(Profissional.class));

        assertNotNull(savedProfissional);
    }

    @Test
    void testFindById_ProfissionalExists() {
        when(profissionaisRepository.findById(anyInt())).thenReturn(Optional.of(new Profissional()));

        ProfissionalDTO profissional = profissionaisService.findById(1);

        assertNotNull(profissional);
    }

    @Test
    void testFindById_ProfissionalNotFound() {
        when(profissionaisRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> profissionaisService.findById(1));
    }

    @Test
    void testFindProfissionais() {
        when(profissionaisRepository.findAll()).thenReturn(Arrays.asList(new Profissional(), new Profissional()));

        List<ProfissionalDTO> profissionais = profissionaisService.findProfissionais(null, null);

        assertNotNull(profissionais);
        assertEquals(2, profissionais.size());
    }

    @Test
    void testFindProfissionaisWithCriteria() {

        List<Profissional> allProfissionais = Arrays.asList(
                new Profissional(1,"João", CargoEnum.SUPORTE, LocalDateTime.now(),LocalDateTime.now()),
                new Profissional(2,"Maria", CargoEnum.DESIGNER, LocalDateTime.now(), LocalDateTime.now())
        );
        when(profissionaisRepository.findAll()).thenReturn(allProfissionais);

        String q = "Suporte";
        List<String> fields = Arrays.asList("nome", "cargo");

        List<ProfissionalDTO> profissionais = profissionaisService.findProfissionais(q, fields);

        assertNotNull(profissionais);
        assertEquals(1, profissionais.size());

        ProfissionalDTO profissionalDTO = profissionais.get(0);
        assertNotNull(profissionalDTO.getNome());
        assertEquals(CargoEnum.SUPORTE, profissionalDTO.getCargo());
    }

    @Test
    void testUpdateProfissional() {
        when(profissionaisRepository.findById(anyInt())).thenReturn(Optional.of(new Profissional()));
        when(profissionaisRepository.save(any(Profissional.class))).thenReturn(new Profissional());

        ProfissionalDTO updatedProfissional = profissionaisService.updateProfissional(1, profissionalDTO);

        verify(profissionaisRepository).findById(1);
        verify(profissionaisRepository).save(any(Profissional.class));

        assertNotNull(updatedProfissional);
    }

    @Test
    void testDeleteProfissional_ProfissionalExists() {
        when(profissionaisRepository.existsById(anyInt())).thenReturn(true);

        profissionaisService.deleteProfissional(1);

        verify(profissionaisRepository).existsById(1);
        verify(profissionaisRepository).deleteById(1);
    }

    @Test
    void testDeleteProfissional_ProfissionalNotFound() {
        when(profissionaisRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> profissionaisService.deleteProfissional(1));

        verify(profissionaisRepository).existsById(1);
    }
}
