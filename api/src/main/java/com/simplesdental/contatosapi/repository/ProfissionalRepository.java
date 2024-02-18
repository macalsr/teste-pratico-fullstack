package com.simplesdental.contatosapi.repository;

import com.simplesdental.contatosapi.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repositório para operações de persistência relacionadas à entidade Profissional.
 */
@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {

}
