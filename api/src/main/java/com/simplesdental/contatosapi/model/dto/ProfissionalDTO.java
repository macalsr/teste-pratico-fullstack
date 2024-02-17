package com.simplesdental.contatosapi.model.dto;


import com.simplesdental.contatosapi.model.CargoEnum;
import com.simplesdental.contatosapi.model.Profissional;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfissionalDTO {

    private Integer id;

    private String nome;

    private CargoEnum cargo;

    private LocalDateTime nascimento;

    private LocalDateTime createdDate;


    public Profissional toModel() {
        return Profissional.builder()
                .nome(this.nome)
                .cargo(this.cargo)
                .nascimento(this.nascimento)
                .createdDate(this.createdDate)
                .build();
    }
}
