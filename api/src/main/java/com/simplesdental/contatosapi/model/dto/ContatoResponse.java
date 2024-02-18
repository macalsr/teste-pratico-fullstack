package com.simplesdental.contatosapi.model.dto;

import com.simplesdental.contatosapi.model.Profissional;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContatoResponse {

    private Integer id;

    private String nome;

    private String contato;

    private Profissional profissional;

    private LocalDateTime createdDate;


}
