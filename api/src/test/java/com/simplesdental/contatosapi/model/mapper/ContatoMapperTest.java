package com.simplesdental.contatosapi.model.mapper;

import com.simplesdental.contatosapi.model.CargoEnum;
import com.simplesdental.contatosapi.model.Contato;
import com.simplesdental.contatosapi.model.Profissional;
import com.simplesdental.contatosapi.model.dto.ContatoDTO;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContatoMapperTest {

    @Test
    void testToDTO() {
        Profissional profissional = Profissional.builder()
                .id(1)
                .cargo(CargoEnum.TESTER)
                .createdDate(LocalDateTime.now())
                .nascimento(LocalDateTime.now())
                .nome("Joao")
                .build();

        Contato contato = Contato.builder()
                .id(1)
                .nome("João")
                .contato("joao@example.com")
                .createdDate(LocalDateTime.now())
                .profissional(profissional)
                .build();

        ContatoDTO contatoDTO = ContatoMapper.toDTO(contato);

        assertEquals(contato.getId(), contatoDTO.getId());
        assertEquals(contato.getNome(), contatoDTO.getNome());
        assertEquals(contato.getContato(), contatoDTO.getContato());
        assertEquals(contato.getCreatedDate(), contatoDTO.getCreatedDate());
        assertEquals(contato.getProfissional().getCargo(), contatoDTO.getProfissional().getCargo());
    }
    @Test
    public void testMapContatoToDTO_AllFields() {
        Contato contato = new Contato();
        contato.setId(1);
        contato.setNome("João");
        contato.setContato("123456789");
        contato.setCreatedDate(LocalDateTime.now());

        List<String> fields = new ArrayList<>();
        fields.add("nome");
        fields.add("contato");
        fields.add("createdDate");

        ContatoDTO contatoDTO = ContatoMapper.mapContatoToDTO(contato, fields);

        assertNotNull(contatoDTO);
        assertNull(contatoDTO.getId());
        assertEquals(contato.getNome(), contatoDTO.getNome());
        assertEquals(contato.getContato(), contatoDTO.getContato());
        assertEquals(contato.getCreatedDate(), contatoDTO.getCreatedDate());
        assertEquals(null, contatoDTO.getProfissional());
    }

    @Test
    void testToModel() {
        ProfissionalDTO profissional = ProfissionalDTO.builder()
                .id(1)
                .cargo(CargoEnum.TESTER)
                .createdDate(LocalDateTime.now())
                .nascimento(LocalDateTime.now())
                .nome("Joao")
                .build();

        ContatoDTO contatoDTO = ContatoDTO.builder()
                .id(1)
                .nome("João")
                .contato("joao@example.com")
                .createdDate(LocalDateTime.now())
                .profissional(profissional)
                .build();

        Contato contato = ContatoMapper.toModel(contatoDTO);

        assertEquals(contato.getId(), contatoDTO.getId());
        assertEquals(contato.getNome(), contatoDTO.getNome());
        assertEquals(contato.getContato(), contatoDTO.getContato());
        assertEquals(contato.getCreatedDate(), contatoDTO.getCreatedDate());
        assertEquals(contato.getProfissional().getCargo(), contatoDTO.getProfissional().getCargo());
    }
}
