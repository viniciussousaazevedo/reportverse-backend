package com.es.reportverse.service;

import com.es.reportverse.DTO.CadastroUsuarioDTO;
import com.es.reportverse.model.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService {

    Usuario cadastraUsuario(CadastroUsuarioDTO cadastroUsuarioDTO);

}
