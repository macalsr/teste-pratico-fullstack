package com.simplesdental.contatosapi.service.impl;

import com.simplesdental.contatosapi.model.CargoEnum;
import com.simplesdental.contatosapi.model.Contato;
import com.simplesdental.contatosapi.model.dto.ContatoDTO;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import com.simplesdental.contatosapi.model.mapper.ProfissionalMapper;
import com.simplesdental.contatosapi.repository.ContatoRepository;
import com.simplesdental.contatosapi.repository.ProfissionaisRepository;
import com.simplesdental.contatosapi.service.ProfissionaisService;
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
 * Testes para a implementação do serviço de Contatos.
 */
@ExtendWith(MockitoExtension.class)
class ContatoServiceImplTest {

    @Mock
    private ContatoRepository repository;

    @Mock
    private ProfissionaisRepository profissionaisRepository;

    @Mock
    private ProfissionaisService profissionaisService;

    @InjectMocks
    private ContatoServiceImpl contatoService;

    private ProfissionalDTO profissionalDTO;
    private Contato mockContato;

    private ContatoDTO mockContatoDTO;
    @BeforeEach
    void setUp() {
        profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setId(1);
        profissionalDTO.setNome("Joao");
        profissionalDTO.setNascimento(LocalDateTime.now());
        profissionalDTO.setCargo(CargoEnum.TESTER);

         mockContato = new Contato();
        mockContato.setId(1);
        mockContato.setNome("Test");
        mockContato.setContato("test@test.com");
        mockContato.setCreatedDate(LocalDateTime.now());
        mockContato.setProfissional(ProfissionalMapper.toModel(profissionalDTO));

        mockContatoDTO = new ContatoDTO();
        mockContatoDTO.setId(1);
        mockContatoDTO.setNome("Test");
        mockContatoDTO.setContato("test@test.com");
        mockContatoDTO.setProfissional(profissionalDTO);

    }

    @Test
    void testFindById_ContatoFound() {
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

        Contato mockContato1 = new Contato();
        mockContato1.setId(1);
        mockContato1.setNome(mockContatoDTO.getNome());
        mockContato1.setContato(mockContatoDTO.getContato());
        mockContato1.setCreatedDate(LocalDateTime.now());
        mockContato1.setProfissional(ProfissionalMapper.toModel(profissionalDTO));

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
    void testFindContatos() {
        Contato mockContato1 = mockContato;
        Contato mockContato2 = mockContato;
        mockContato2.setId(2);
        when(repository.findAll()).thenReturn(List.of(mockContato1, mockContato2));

        List<ContatoDTO> contatos = contatoService.findContatos(null, null);

        assertNotNull(contatos);
        assertEquals(2, contatos.size());
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

    @Test
    void testFindContatoWithCriteriaNome() {
        List<Contato> allContatos = Arrays.asList(
                new Contato(1,"João", "contato",LocalDateTime.now(), ProfissionalMapper.toModel(profissionalDTO)),
                new Contato(2,"Maria", "contato1",LocalDateTime.now(), ProfissionalMapper.toModel(profissionalDTO))
        );
        when(repository.findAll()).thenReturn(allContatos);

        String q = "Maria";
        List<String> fields = Arrays.asList("nome", "contato");

        List<ContatoDTO> contatoDTOList = contatoService.findContatos(q, fields);

        assertNotNull(contatoDTOList);
        assertEquals(1, contatoDTOList.size());

        ContatoDTO contatoDTO = contatoDTOList.get(0);
        assertNotNull(contatoDTO.getNome());
        assertNull(contatoDTO.getCreatedDate());
        assertEquals("Maria", contatoDTO.getNome());
    }

    @Test
    void testFindContatoWithCriteriaProfissional() {
        List<Contato> allContatos = Arrays.asList(
                new Contato(1,"João", "contato",LocalDateTime.now(), ProfissionalMapper.toModel(profissionalDTO)),
                new Contato(2,"Maria", "contato1",LocalDateTime.now(), ProfissionalMapper.toModel(profissionalDTO))
        );
        when(repository.findAll()).thenReturn(allContatos);
        when(profissionaisService.findById(1)).thenReturn(profissionalDTO);

        String q = "1";
        List<String> fields = Arrays.asList("nome", "contato", "profissional");

        List<ContatoDTO> contatoDTOList = contatoService.findContatos(q, fields);

        assertNotNull(contatoDTOList);
        assertEquals(1, contatoDTOList.size());

        ContatoDTO contatoDTO = contatoDTOList.get(0);
        assertNotNull(contatoDTO.getNome());
        assertNull(contatoDTO.getCreatedDate());
        assertEquals(profissionalDTO.getId(), contatoDTO.getProfissional().getId());
    }

    @Test
    void testUpdateContato() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Contato()));
        when(repository.save(any(Contato.class))).thenReturn(new Contato());

        ContatoDTO updated = contatoService.updateContato(1, mockContatoDTO);

        verify(repository).findById(1);
        verify(repository).save(any(Contato.class));

        assertNotNull(updated);
    }

    @Test
    void testDeleteContato_ContatoExists() {
        when(repository.existsById(anyInt())).thenReturn(true);

        contatoService.deleteContato(1);

        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    void testDeleteContato_ContatoNotFound() {
        when(repository.existsById(anyInt())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> contatoService.deleteContato(1));

        verify(repository).existsById(1);
    }
}
