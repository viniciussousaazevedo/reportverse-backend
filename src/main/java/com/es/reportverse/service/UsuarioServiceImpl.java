package com.es.reportverse.service;

import com.es.reportverse.DTO.CadastroUsuarioDTO;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.Universitario;
import com.es.reportverse.model.Usuario;
import com.es.reportverse.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario cadastraUsuario(CadastroUsuarioDTO cadastroUsuarioDTO) {

        this.validaEmail(cadastroUsuarioDTO.getEmail());
        this.validaSenha(cadastroUsuarioDTO.getSenha(), cadastroUsuarioDTO.getConfirmacaoSenha());

        Usuario usuario = new Universitario(
                cadastroUsuarioDTO.getNome(),
                cadastroUsuarioDTO.getEmail(),
                cadastroUsuarioDTO.getSenha()
        );

        this.salvaUsuario(usuario);

        return usuario;
    }

    private void validaEmail(String email) {
        if (this.usuarioRepository.findByEmail(email).isPresent()) {
            throw new ApiRequestException(("O endereço de e-mail informado já existe no sistema."));
        }
    }

    private void validaSenha(String senha, String confirmacaoSenha) {
        if (!senha.equals(confirmacaoSenha)) {
            throw new ApiRequestException(("A Senha informada não coincide com a confirmação de senha"));
        }
    }

    @Override
    public void salvaUsuario(Usuario usuario) {
        this.usuarioRepository.save(usuario);
    }
}
