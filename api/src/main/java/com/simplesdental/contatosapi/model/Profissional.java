package com.simplesdental.contatosapi.model;

import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profissionais")
@Builder
public class Profissional {

    @Id
    @SequenceGenerator(name = "profissionais_id_seq", sequenceName = "profissionais_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profissionais_id_seq")
    private Integer id;

    @NonNull
    @Column(name = "nome")
    private String nome;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cargo")
    private CargoEnum cargo;

    @NonNull
    @Column(name = "nascimento")
    private LocalDateTime nascimento;

    @CreationTimestamp
    @Column(name = "createdDate", updatable = false)
    private LocalDateTime createdDate;

    public ProfissionalDTO toDTO() {
        return ProfissionalDTO.builder()
                .cargo(this.cargo)
                .nascimento(this.nascimento)
                .nome(this.nome)
                .build();
    }
    public ProfissionalDTO mapProfissionaisToDTO(Profissional profissional, List<String> fields){
        ProfissionalDTO profissionalDTO = new ProfissionalDTO();
        if (fields == null || fields.isEmpty()) {
            // Se nenhum campo especificado, mapeia todos os campos do Profissionais para ProfissionaisDTO
            profissionalDTO.setNome(profissional.getNome());
            profissionalDTO.setCargo(profissional.getCargo());
            profissionalDTO.setNascimento(profissional.getNascimento());
            profissionalDTO.setCreatedDate(profissional.getCreatedDate());
            profissionalDTO.setId(profissional.getId());
            // Mapeie outros campos conforme necessário
        } else {
            // Mapeia apenas os campos especificados em 'fields' do Profissionais para ProfissionaisDTO
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
                    default:
                        break;
                }
            }
        }
        return profissionalDTO;
    }
}
