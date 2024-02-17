package com.simplesdental.contatosapi.model.mapper;

import com.simplesdental.contatosapi.model.Profissional;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;

import java.util.List;
import java.util.stream.Collectors;

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
        ProfissionalDTO profissionalDTO = ProfissionalDTO.builder()
                .id(profissional.getId())
                .cargo(profissional.getCargo())
                .nascimento(profissional.getNascimento())
                .createdDate(profissional.getCreatedDate())
                .nome(profissional.getNome())
                .build();
        if(profissional.getContatos() != null) {
            profissionalDTO.setContatos(profissional.getContatos().stream()
                    .map(ContatoMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        return profissionalDTO;
    }

    /**
     * Mapeia campos selecionados de um objeto Profissional para um objeto ProfissionalDTO.
     *
     * @param profissional O objeto Profissional a ser mapeado.
     * @param fields       A lista de campos a serem incluídos no ProfissionalDTO mapeado.
     * @return Objeto ProfissionalDTO com os campos selecionados mapeados do Profissional.
     */
    public static ProfissionalDTO mapProfissionaisToDTO(Profissional profissional, List<String> fields) {
        ProfissionalDTO profissionalDTO = new ProfissionalDTO();
        if (fields == null || fields.isEmpty()) {
            profissionalDTO.setNome(profissional.getNome());
            profissionalDTO.setCargo(profissional.getCargo());
            profissionalDTO.setNascimento(profissional.getNascimento());
            profissionalDTO.setCreatedDate(profissional.getCreatedDate());
            profissionalDTO.setId(profissional.getId());
            if(profissional.getContatos() != null) {
                profissionalDTO.setContatos(
                        profissional.getContatos().stream()
                                .map(ContatoMapper::toDTO)
                                .collect(Collectors.toList())
                );
            }
        } else {
            for (String field : fields) {
                switch (field) {
                    case "nome":
                        profissionalDTO.setNome(profissional.getNome());
                        break;
                    case "cargo":
                        profissionalDTO.setCargo(profissional.getCargo());
                        break;
                    case "nascimento":
                        profissionalDTO.setNascimento(profissional.getNascimento());
                        break;
                    case "createdDate":
                        profissionalDTO.setCreatedDate(profissional.getCreatedDate());
                        break;
                    case "id":
                        profissionalDTO.setId(profissional.getId());
                        break;
                    case "contatos":
                        profissionalDTO.setContatos(
                                profissional.getContatos().stream()
                                        .map(ContatoMapper::toDTO)
                                        .collect(Collectors.toList())
                        );
                        break;
                    default:
                        break;
                }
            }
        }
        return profissionalDTO;
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
                .id(profissionalDTO.getId())
                .nome(profissionalDTO.getNome())
                .cargo(profissionalDTO.getCargo())
                .nascimento(profissionalDTO.getNascimento())
                .createdDate(profissionalDTO.getCreatedDate())
                .contatos(profissionalDTO.getContatos().stream()
                        .map(ContatoMapper::toModel)
                        .collect(Collectors.toList()))
                .build();
    }
}
