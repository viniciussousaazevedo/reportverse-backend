package com.es.reportverse.controller;

import com.es.reportverse.DTO.CadastroUsuarioDTO;
import com.es.reportverse.DTO.UsuarioDTO;
import com.es.reportverse.model.Usuario;
import com.es.reportverse.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastraUsuario(@RequestBody CadastroUsuarioDTO cadastroUsuarioDTO) {

        Usuario usuario = this.usuarioService.cadastraUsuario(cadastroUsuarioDTO);
        UsuarioDTO usuarioDTO = this.modelMapper.map(usuario, UsuarioDTO.class);

        return new ResponseEntity<>(usuarioDTO, HttpStatus.CREATED);
    }
}
