package com.simplesdental.contatosapi.repository;

import com.simplesdental.contatosapi.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repositório para operações de persistência relacionadas à entidade Contato.
 */
@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {
}
