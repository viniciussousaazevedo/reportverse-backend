package com.es.reportverse.service;

import com.es.reportverse.DTO.CadastroDTO;
import org.springframework.stereotype.Service;

@Service
public interface CadastroService {

    String cadastraUsuario(CadastroDTO request);

}
