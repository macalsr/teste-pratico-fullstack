package com.simplesdental.contatosapi.model.dto;

import com.simplesdental.contatosapi.model.Profissional;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContatoDTO {

    private Integer id;

    private String nome;

    private String contato;

    private ProfissionalDTO profissional;

    private LocalDateTime createdDate;

}
