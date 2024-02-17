package com.simplesdental.contatosapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe que representa um profissional.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profissionais")
@Builder
public class Profissional {

    /**
     * Identificador único do profissional.
     */
    @Id
    @SequenceGenerator(name = "profissionais_id_seq", sequenceName = "profissionais_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profissionais_id_seq")
    private Integer id;

    /**
     * Nome do profissional.
     */
    @NonNull
    @Column(name = "nome")
    private String nome;

    /**
     * Cargo do profissional.
     */
    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cargo")
    private CargoEnum cargo;

    /**
     * Data de nascimento do profissional.
     */
    @NonNull
    @Column(name = "nascimento")
    private LocalDateTime nascimento;

    /**
     * Data de criação do registro do profissional.
     */
    @CreationTimestamp
    @Column(name = "createdDate", updatable = false)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL)
    private List<Contato> contatos;
}
