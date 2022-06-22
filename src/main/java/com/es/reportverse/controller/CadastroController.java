package com.es.reportverse.controller;

import com.es.reportverse.DTO.CadastroDTO;
import com.es.reportverse.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/cadastro")
public class CadastroController {

    @Autowired
    CadastroService cadastroService;

    @PostMapping
    public String cadastraUsuario(@RequestBody CadastroDTO request) {
        return cadastroService.cadastraUsuario(request);
    }
}
