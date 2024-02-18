package com.simplesdental.contatosapi.model.dto;

import com.simplesdental.contatosapi.model.CargoEnum;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Classe DTO (Data Transfer Object) que representa um profissional.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfissionalDTO {

    /**
     * Nome do profissional.
     */
    private String nome;

    /**
     * Cargo do profissional.
     */
    private CargoEnum cargo;

    /**
     * Data de nascimento do profissional.
     */
    private LocalDateTime nascimento;

}
