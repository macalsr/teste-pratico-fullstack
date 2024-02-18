package com.simplesdental.contatosapi.model.mapper;

import com.simplesdental.contatosapi.model.Profissional;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;

import java.util.List;

/**
 * Classe de mapeamento responsável por mapear entre objetos Profissional e ProfissionalDTO.
 */
public class ProfissionalMapper {

    /**
     * Converte um objeto Profissional em um objeto ProfissionalDTO.
     *
     * @param profissional O objeto Profissional a ser convertido.
     * @return Objeto ProfissionalDTO representando o Profissional.
     */
    public static ProfissionalDTO toDTO(Profissional profissional) {
        if(profissional == null){
            return null;
        }
        return ProfissionalDTO.builder()
                .cargo(profissional.getCargo())
                .nascimento(profissional.getNascimento())
                .nome(profissional.getNome())
                .build();
    }

    /**
     * Mapeia campos selecionados de um objeto Profissional para um objeto ProfissionalDTO.
     *
     * @param profissional O objeto Profissional a ser mapeado.
     * @param fields       A lista de campos a serem incluídos no ProfissionalDTO mapeado.
     * @return Objeto ProfissionalDTO com os campos selecionados mapeados do Profissional.
     */
    public static Profissional mapProfissionais(Profissional profissional, List<String> fields) {
        Profissional profissionalMapped = new Profissional();
        if (fields == null || fields.isEmpty()) {
            return profissional;
        } else {
            for (String field : fields) {
                switch (field) {
                    case "nome":
                        profissionalMapped.setNome(profissional.getNome());
                        break;
                    case "cargo":
                        profissionalMapped.setCargo(profissional.getCargo());
                        break;
                    case "nascimento":
                        profissionalMapped.setNascimento(profissional.getNascimento());
                        break;
                    case "id":
                        profissionalMapped.setId(profissional.getId());
                        break;
                    case "createdDate":
                        profissionalMapped.setCreatedDate(profissional.getCreatedDate());
                        break;
                    default:
                        break;
                }
            }
        }
        return profissionalMapped;
    }

    /**
     * Converte este objeto ProfissionalDTO em um objeto Profissional.
     *
     * @return Objeto Profissional correspondente a este ProfissionalDTO.
     */
    public static Profissional toModel(ProfissionalDTO profissionalDTO) {
        if (profissionalDTO == null) {
            return null;
        }
        return Profissional.builder()
                .nome(profissionalDTO.getNome())
                .cargo(profissionalDTO.getCargo())
                .nascimento(profissionalDTO.getNascimento())
                .build();
    }
}
