package com.simplesdental.contatosapi.model.mapper;

import com.simplesdental.contatosapi.model.CargoEnum;
import com.simplesdental.contatosapi.model.Profissional;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfissionalMapperTest {

    @Test
     void testToDTO() {
        Profissional profissional = new Profissional();
        profissional.setId(1);
        profissional.setNome("João");
        profissional.setCargo(CargoEnum.DESENVOLVEDOR);
        profissional.setNascimento(LocalDateTime.now());
        profissional.setCreatedDate(LocalDateTime.now());

        ProfissionalDTO profissionalDTO = ProfissionalMapper.toDTO(profissional);

        assertNotNull(profissionalDTO);
        assertEquals(profissional.getNome(), profissionalDTO.getNome());
        assertEquals(profissional.getCargo(), profissionalDTO.getCargo());
        assertEquals(profissional.getNascimento(), profissionalDTO.getNascimento());
    }

    @Test
     void testMapProfissionaisToDTO() {
        Profissional profissional = new Profissional();
        profissional.setId(1);
        profissional.setNome("João");
        profissional.setCargo(CargoEnum.DESENVOLVEDOR);
        profissional.setNascimento(LocalDateTime.now());
        profissional.setCreatedDate(LocalDateTime.now());

        List<String> fields = new ArrayList<>();
        fields.add("nome");
        fields.add("cargo");
        fields.add("nascimento");
        fields.add("createdDate");

        ProfissionalDTO profissionalDTO = ProfissionalMapper.mapProfissionaisToDTO(profissional, fields);

        assertNotNull(profissionalDTO);
        assertEquals(profissional.getNome(), profissionalDTO.getNome());
        assertEquals(profissional.getCargo(), profissionalDTO.getCargo());
        assertEquals(profissional.getNascimento(), profissionalDTO.getNascimento());
    }

   @Test
   void testToModel() {
      ProfissionalDTO profissionalDTO = new ProfissionalDTO();
      profissionalDTO.setNome("João");
      profissionalDTO.setCargo(CargoEnum.DESENVOLVEDOR);
      profissionalDTO.setNascimento(LocalDateTime.now());

      Profissional profissional = ProfissionalMapper.toModel(profissionalDTO);

      assertNotNull(profissional);
      assertEquals(profissional.getNome(), profissionalDTO.getNome());
      assertEquals(profissional.getCargo(), profissionalDTO.getCargo());
      assertEquals(profissional.getNascimento(), profissionalDTO.getNascimento());
   }
}
