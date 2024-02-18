package com.simplesdental.contatosapi.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContatoReceiver {

    private String nome;

    private String contato;

    private Integer idProfissional;

}
