package com.simplesdental.contatosapi.repository;


import com.simplesdental.contatosapi.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {

    List<Contato> findByNomeContainingOrContatoContaining(String nome, String contato);
}
