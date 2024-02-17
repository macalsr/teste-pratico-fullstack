package com.simplesdental.contatosapi.repository;


import com.simplesdental.contatosapi.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionaisRepository extends JpaRepository<Profissional, Integer> {


}
