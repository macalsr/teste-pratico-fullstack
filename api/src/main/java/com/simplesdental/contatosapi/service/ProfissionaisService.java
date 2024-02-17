package com.simplesdental.contatosapi.service;

import com.simplesdental.contatosapi.model.dto.ContatoDTO;
import com.simplesdental.contatosapi.model.dto.ProfissionalDTO;

import java.util.List;

public interface ProfissionaisService {

    ProfissionalDTO createProfissional(ProfissionalDTO profissionalDTO);

    ProfissionalDTO findById(Integer id);

    List<ProfissionalDTO> findProfissionais(String q, List<String> fields);

    ProfissionalDTO updateProfissional(Integer id, ProfissionalDTO profissionalDTO);

    void deleteProfissional(Integer id);
}
