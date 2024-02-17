package com.simplesdental.contatosapi.service;


import com.simplesdental.contatosapi.model.dto.ContatoDTO;

import java.util.List;

public interface ContatoService {
    ContatoDTO createContato(ContatoDTO contato);

    ContatoDTO findById(Integer id);

    List<ContatoDTO> findContatos(String q, List<String> fields);

    ContatoDTO updateContato(Integer id, ContatoDTO contatoDTO);

    void deleteContato(Integer id);
}
