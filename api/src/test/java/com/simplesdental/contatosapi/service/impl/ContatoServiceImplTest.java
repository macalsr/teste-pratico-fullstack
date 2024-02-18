package com.simplesdental.contatosapi.service.impl;

import com.simplesdental.contatosapi.model.CargoEnum;
import com.simplesdental.contatosapi.model.Contato;
import com.simplesdental.contatosapi.model.Profissional;
import com.simplesdental.contatosapi.model.dto.ContatoReceiver;
import com.simplesdental.contatosapi.model.dto.ContatoResponse;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import com.simplesdental.contatosapi.model.mapper.ProfissionalMapper;
import com.simplesdental.contatosapi.repository.ContatoRepository;
import com.simplesdental.contatosapi.repository.ProfissionalRepository;
import com.simplesdental.contatosapi.service.ProfissionalService;
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
    private ProfissionalRepository profissionalRepository;

    @Mock
    private ProfissionalService profissionalService;

    @InjectMocks
    private ContatoServiceImpl contatoService;

    private ProfissionalDTO profissionalDTO;

    private Profissional profissional;
    private Contato mockContato;

    private ContatoReceiver mockContatoReceiver;
    @BeforeEach
    void setUp() {
        profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setNome("Joao");
        profissionalDTO.setNascimento(LocalDateTime.now());
        profissionalDTO.setCargo(CargoEnum.TESTER);

        profissional = new Profissional();
        profissional.setId(1);
        profissional.setNome("Joao");
        profissional.setNascimento(LocalDateTime.now());
        profissional.setCargo(CargoEnum.TESTER);

        mockContato = new Contato();
        mockContato.setId(1);
        mockContato.setNome("Test");
        mockContato.setContato("test@test.com");
        mockContato.setCreatedDate(LocalDateTime.now());
        mockContato.setProfissional(ProfissionalMapper.toModel(profissionalDTO));

        mockContatoReceiver = new ContatoReceiver();
        mockContatoReceiver.setNome("Test");
        mockContatoReceiver.setContato("test@test.com");
        mockContatoReceiver.setIdProfissional(profissional.getId());

    }

    @Test
    void testFindById_ContatoFound() {
        when(repository.findById(1)).thenReturn(Optional.of(mockContato));

        ContatoResponse result = contatoService.findById(1);
        assertNotNull(result);
        assertEquals(mockContato.getNome(), result.getNome());
        assertEquals(mockContato.getContato(), result.getContato());
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
        mockContato1.setNome(mockContatoReceiver.getNome());
        mockContato1.setContato(mockContatoReceiver.getContato());
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

        ContatoResponse result = contatoService.createContato(mockContatoReceiver);

        verify(repository).save(any(Contato.class));

        assertNotNull(result);
        assertEquals(mockContatoReceiver.getNome(), result.getNome());
        assertEquals(mockContatoReceiver.getContato(), result.getContato());
    }
    @Test
    void testFindContatos() {
        Contato mockContato1 = mockContato;
        Contato mockContato2 = mockContato;
        mockContato2.setId(2);
        when(repository.findAll()).thenReturn(List.of(mockContato1, mockContato2));

        List<ContatoResponse> contatos = contatoService.findContatos(null, null);

        assertNotNull(contatos);
        assertEquals(2, contatos.size());
    }
    @Test
    void testFindContatos_NoCriteria() {
        Contato mockContato1 = new Contato();
        Contato mockContato2 = new Contato();

        when(repository.findAll()).thenReturn(List.of(mockContato1, mockContato2));

        List<ContatoResponse> result = contatoService.findContatos(null, null);

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

        List<ContatoResponse> contatoReceiverList = contatoService.findContatos(q, fields);

        assertNotNull(contatoReceiverList);
        assertEquals(1, contatoReceiverList.size());

        ContatoResponse contatoReceiver = contatoReceiverList.get(0);
        assertNotNull(contatoReceiver.getNome());
        assertEquals("Maria", contatoReceiver.getNome());
    }

    @Test
    void testFindContatoWithCriteriaProfissional() {
        List<Contato> allContatos = Arrays.asList(
                new Contato(1,"João", "contato",LocalDateTime.now(), ProfissionalMapper.toModel(profissionalDTO)),
                new Contato(2,"Maria", "contato1",LocalDateTime.now(), ProfissionalMapper.toModel(profissionalDTO))
        );
        when(repository.findAll()).thenReturn(allContatos);
        when(profissionalService.findById(1)).thenReturn(profissional);

        String q = "1";
        List<String> fields = Arrays.asList("nome", "contato", "profissional");

        List<ContatoResponse> contatoReceiverList = contatoService.findContatos(q, fields);

        assertNotNull(contatoReceiverList);
        assertEquals(1, contatoReceiverList.size());

        ContatoResponse contatoReceiver = contatoReceiverList.get(0);
        assertNotNull(contatoReceiver.getNome());
    }

    @Test
    void testUpdateContato() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Contato()));
        when(repository.save(any(Contato.class))).thenReturn(new Contato());

        ContatoReceiver updated = contatoService.updateContato(1, mockContatoReceiver);

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
