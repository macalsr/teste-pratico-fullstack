package com.simplesdental.contatosapi.service.impl;

import com.simplesdental.contatosapi.model.CargoEnum;
import com.simplesdental.contatosapi.model.Contato;
import com.simplesdental.contatosapi.model.dto.ContatoDTO;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import com.simplesdental.contatosapi.repository.ContatoRepository;
import com.simplesdental.contatosapi.service.ProfissionaisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
class ContatoServiceImplTest {

    @Mock
    private ContatoRepository repository;

    @Mock
    private ProfissionaisService profissionaisService;

    @InjectMocks
    private ContatoServiceImpl contatoService;

    private ProfissionalDTO profissionalDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setId(1);
        profissionalDTO.setNome("Joao");
        profissionalDTO.setNascimento(LocalDateTime.now());
        profissionalDTO.setCargo(CargoEnum.TESTER);
    }

    @Test
    void testFindById_ContatoFound() {
        Contato mockContato = new Contato();
        mockContato.setId(1);
        mockContato.setNome("Test");
        mockContato.setContato("test@test.com");
        mockContato.setCreatedDate(LocalDateTime.now());
        mockContato.setProfissional(profissionalDTO.toModel());
        when(repository.findById(1)).thenReturn(Optional.of(mockContato));

        ContatoDTO result = contatoService.findById(1);

        assertNotNull(result);
        assertEquals(mockContato.getId(), result.getId());
        assertEquals(mockContato.getNome(), result.getNome());
        assertEquals(mockContato.getContato(), result.getContato());
        assertEquals(mockContato.getCreatedDate(), result.getCreatedDate());
    }

    @Test
    void testFindById_ContatoNotFound() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> contatoService.findById(1));
    }

    @Test
    void testCreateContato() {
        ContatoDTO mockContatoDTO = new ContatoDTO();
        mockContatoDTO.setId(1);
        mockContatoDTO.setNome("Test");
        mockContatoDTO.setContato("test@test.com");
        mockContatoDTO.setProfissional(profissionalDTO);

        Contato mockContato = new Contato();
        mockContato.setId(1);
        mockContato.setNome(mockContatoDTO.getNome());
        mockContato.setContato(mockContatoDTO.getContato());
        mockContato.setCreatedDate(LocalDateTime.now());
        mockContato.setProfissional(profissionalDTO.toModel());

        when(repository.save(any(Contato.class))).thenAnswer(invocation -> {
            Contato savedContato = invocation.getArgument(0);
            savedContato.setId(1);
            savedContato.setProfissional(mockContato.getProfissional());
            savedContato.setNome(mockContato.getNome());
            savedContato.setContato(mockContato.getContato());
            savedContato.setCreatedDate(mockContato.getCreatedDate());
            return savedContato;
        });

        ContatoDTO result = contatoService.createContato(mockContatoDTO);

        verify(repository).save(any(Contato.class));

        assertNotNull(result);
        assertEquals(mockContatoDTO.getNome(), result.getNome());
        assertEquals(mockContatoDTO.getContato(), result.getContato());
        assertNotNull(result.getId());
    }

    @Test
    void testFindContatos_NoCriteria() {
        Contato mockContato1 = new Contato();
        Contato mockContato2 = new Contato();

        when(repository.findAll()).thenReturn(List.of(mockContato1, mockContato2));

        List<ContatoDTO> result = contatoService.findContatos(null, null);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

}
