package com.simplesdental.contatosapi.model.mapper;

import com.simplesdental.contatosapi.model.CargoEnum;
import com.simplesdental.contatosapi.model.Contato;
import com.simplesdental.contatosapi.model.Profissional;
import com.simplesdental.contatosapi.model.dto.ContatoReceiver;
import com.simplesdental.contatosapi.model.dto.ContatoResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ContatoMapperTest {

    @Test
    void testToReceiver() {
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

        ContatoReceiver contatoReceiver = ContatoMapper.toReceiver(contato);

        assertEquals(contato.getNome(), contatoReceiver.getNome());
        assertEquals(contato.getContato(), contatoReceiver.getContato());
        assertEquals(contato.getProfissional().getId(), contatoReceiver.getIdProfissional());
    }

    @Test
    void testToResponse() {
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

        ContatoResponse contatoResponse = ContatoMapper.toResponse(contato);

        assertEquals(contato.getNome(), contatoResponse.getNome());
        assertEquals(contato.getContato(), contatoResponse.getContato());
        assertEquals(contato.getProfissional().getId(), contatoResponse.getProfissional().getId());
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

        ContatoResponse contatoReceiver = ContatoMapper.mapContatoToDTO(contato, fields);

        assertNotNull(contatoReceiver);
        assertEquals(contato.getNome(), contatoReceiver.getNome());
        assertEquals(contato.getContato(), contatoReceiver.getContato());
        assertEquals(null, contatoReceiver.getProfissional());
    }


}
