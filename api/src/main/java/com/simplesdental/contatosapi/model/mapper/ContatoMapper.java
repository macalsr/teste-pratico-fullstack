package com.simplesdental.contatosapi.model.mapper;

import com.simplesdental.contatosapi.model.Contato;
import com.simplesdental.contatosapi.model.dto.ContatoDTO;
import lombok.Builder;

import java.util.List;

/**
 * Classe de mapeamento responsável por mapear entre objetos Contato e ContatoDTO.
 */
@Builder
public class ContatoMapper {

    /**
     * Converte um objeto Contato em um objeto ContatoDTO.
     *
     * @param contato O objeto Contato a ser convertido.
     * @return Objeto ContatoDTO representando o Contato.
     */
    public static ContatoDTO toDTO(Contato contato) {
        ContatoDTO contatoDTO = ContatoDTO.builder()
                .id(contato.getId())
                .nome(contato.getNome())
                .contato(contato.getContato())
                .createdDate(contato.getCreatedDate())
                .build();

        if (contato.getProfissional() != null) {
            contatoDTO.setProfissional(ProfissionalMapper.toDTO(contato.getProfissional()));
        }
        return contatoDTO;
    }

    /**
     * Mapeia campos selecionados de um objeto Contato para um objeto ContatoDTO.
     *
     * @param contato O objeto Contato a ser mapeado.
     * @param fields  A lista de campos a serem incluídos no ContatoDTO mapeado.
     * @return Objeto ContatoDTO com os campos selecionados mapeados do Contato.
     */
    public static ContatoDTO mapContatoToDTO(Contato contato, List<String> fields) {
        ContatoDTO contatoDTO = new ContatoDTO();

        if (fields == null || fields.isEmpty()) {
            contatoDTO.setId(contato.getId());
            contatoDTO.setNome(contato.getNome());
            contatoDTO.setContato(contato.getContato());
            contatoDTO.setCreatedDate(contato.getCreatedDate());
            if (contato.getProfissional() != null) {
                contatoDTO.setProfissional(ContatoMapper.toDTO(contato).getProfissional());
            }
        } else {
            for (String field : fields) {
                switch (field) {
                    case "nome":
                        contatoDTO.setNome(contato.getNome());
                        break;
                    case "contato":
                        contatoDTO.setContato(contato.getContato());
                        break;
                    case "profissional":
                        contatoDTO.setProfissional(toDTO(contato).getProfissional());
                        break;
                    case "createdDate":
                        contatoDTO.setCreatedDate(contato.getCreatedDate());
                        break;
                    case "id":
                        contatoDTO.setId(contato.getId());
                        break;
                    default:
                        break;
                }
            }
        }

        return contatoDTO;
    }

    /**
     * Converte um objeto ContatoDTO em um objeto Contato.
     *
     * @param contatoDTO O objeto ContatoDTO a ser convertido.
     * @return Objeto Contato representando o ContatoDTO.
     */
    public static Contato toModel(ContatoDTO contatoDTO) {
        return Contato.builder()
                .id(contatoDTO.getId())
                .contato(contatoDTO.getContato())
                .profissional(ProfissionalMapper.toModel(contatoDTO.getProfissional()))
                .nome(contatoDTO.getNome()).build();
    }
}
