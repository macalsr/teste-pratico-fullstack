package com.simplesdental.contatosapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Classe que representa um contato.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "contatos")
public class Contato implements Serializable {

    /**
     * Identificador único do contato.
     */
    @Id
    @SequenceGenerator(name = "contatos_id_seq", sequenceName = "contatos_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contatos_id_seq")
    private Integer id;

    /**
     * Nome do contato.
     */
    @NonNull
    @Column(name = "nome")
    private String nome;

    /**
     * Informações de contato.
     */
    @NonNull
    @Column(name = "contato")
    private String contato;

    /**
     * Data de criação do registro do contato.
     */
    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    /**
     * Profissional associado ao contato.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profissional_id", referencedColumnName = "id")
    private Profissional profissional;

}
