package com.simplesdental.contatosapi.model.mapper;

import com.simplesdental.contatosapi.model.Contato;
import com.simplesdental.contatosapi.model.dto.ContatoReceiver;
import com.simplesdental.contatosapi.model.dto.ContatoResponse;
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
    public static ContatoReceiver toReceiver(Contato contato) {
        ContatoReceiver contatoReceiver = ContatoReceiver.builder()
                .nome(contato.getNome())
                .contato(contato.getContato())
                .build();

        if (contato.getProfissional() != null) {
            contatoReceiver.setIdProfissional(contato.getProfissional().getId());
        }
        return contatoReceiver;
    }
    public static ContatoResponse toResponse(Contato contato) {
        ContatoResponse contatoResponse = ContatoResponse.builder()
                .id(contato.getId())
                .nome(contato.getNome())
                .contato(contato.getContato())
                .profissional(contato.getProfissional())
                .createdDate(contato.getCreatedDate())
                .build();

        if (contato.getProfissional() != null) {
            contatoResponse.setProfissional(contato.getProfissional());
        }
        return contatoResponse;
    }

    /**
     * Mapeia campos selecionados de um objeto Contato para um objeto ContatoDTO.
     *
     * @param contato O objeto Contato a ser mapeado.
     * @param fields  A lista de campos a serem incluídos no ContatoDTO mapeado.
     * @return Objeto ContatoDTO com os campos selecionados mapeados do Contato.
     */
    public static ContatoResponse mapContatoToDTO(Contato contato, List<String> fields) {
        ContatoResponse contatoResponse = new ContatoResponse();

        if (fields == null || fields.isEmpty()) {
            contatoResponse.setId(contato.getId());
            contatoResponse.setCreatedDate(contato.getCreatedDate());
            contatoResponse.setNome(contato.getNome());
            contatoResponse.setContato(contato.getContato());
            if (contato.getProfissional() != null) {
                contatoResponse.setProfissional(contato.getProfissional());
            }
        } else {
            for (String field : fields) {
                switch (field) {
                    case "nome":
                        contatoResponse.setNome(contato.getNome());
                        break;
                    case "contato":
                        contatoResponse.setContato(contato.getContato());
                        break;
                    case "profissional":
                        contatoResponse.setProfissional(contato.getProfissional());
                        break;
                    case "id":
                        contatoResponse.setId(contato.getId());
                        break;
                    case "createdDate":
                        contatoResponse.setCreatedDate(contato.getCreatedDate());
                        break;
                    default:
                        break;
                }
            }
        }

        return contatoResponse;
    }

}
