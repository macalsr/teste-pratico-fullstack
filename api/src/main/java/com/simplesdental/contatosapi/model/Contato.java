package com.simplesdental.contatosapi.model;

import com.simplesdental.contatosapi.model.dto.ContatoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "contatos")
public class Contato implements Serializable {

    private static final long serialVersionUID = -3656431259068389491L;

    @Id
    @SequenceGenerator(name = "contatos_id_seq", sequenceName = "contatos_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contatos_id_seq")
    private Integer id;

    @NonNull
    @Column(name = "nome")
    private String nome;

    @NonNull
    @Column(name = "contato")
    private String contato;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profissional_id", referencedColumnName = "id")
    private Profissional profissional;

    public ContatoDTO toDTO() {
        return ContatoDTO.builder()
                .id(this.id)
                .nome(this.nome)
                .contato(this.contato)
                .profissional(this.profissional.toDTO())
                .build();
    }
    public ContatoDTO mapContatosToDTO(Contato contato, List<String> fields){
        ContatoDTO contatoDTO = new ContatoDTO();
        if (fields == null || fields.isEmpty()) {
            contatoDTO.setNome(contato.getNome());
            contatoDTO.setContato(contato.getContato());
            contatoDTO.setCreatedDate(contato.getCreatedDate());
            contatoDTO.setProfissional(contato.getProfissional().toDTO());
            contatoDTO.setId(contato.getId());
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
                        contatoDTO.setProfissional(contato.toDTO().getProfissional());
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
}
