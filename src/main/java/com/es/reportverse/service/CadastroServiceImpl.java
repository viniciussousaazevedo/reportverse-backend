package com.es.reportverse.service;

import com.es.reportverse.DTO.CadastroDTO;
import org.springframework.stereotype.Service;

@Service
public class CadastroServiceImpl implements CadastroService {
    @Override
    public String cadastraUsuario(CadastroDTO request) {
        return "it works";
    }
}
