package com.es.reportverse.service;

import com.es.reportverse.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final static String USUARIO_NAO_ENCONTRADO = "Usuário com e-mail %s não encontrado";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USUARIO_NAO_ENCONTRADO, email)));
    }
}
