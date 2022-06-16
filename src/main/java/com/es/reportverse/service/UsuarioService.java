package com.es.reportverse.service;

import com.es.reportverse.DTO.CadastroUsuarioDTO;
import com.es.reportverse.model.Usuario;

public interface UsuarioService {

    Usuario cadastraUsuario(CadastroUsuarioDTO cadastroUsuarioDTO);

    void salvaUsuario(Usuario usuario);
}
