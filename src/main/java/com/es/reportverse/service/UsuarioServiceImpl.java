package com.es.reportverse.service;

import com.es.reportverse.DTO.CadastroUsuarioDTO;
import com.es.reportverse.enums.TipoUsuario;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.Usuario;
import com.es.reportverse.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final static String USUARIO_NAO_ENCONTRADO = "Usuário com e-mail %s não encontrado";
    private final static String EMAIL_JA_CADASTRADO = "e-mail %s já se encontra cadastrado";
    private final static String SENHAS_DESIGUAIS = "A Senha informada não coincide com a confirmação de senha";
    private final static String PERMISSAO_NAO_ENCONTRADA = "Permissão de nome %s não encontrada";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USUARIO_NAO_ENCONTRADO, email)));
    }

    @Override
    public Usuario cadastraUsuario(CadastroUsuarioDTO cadastroUsuarioDTO) {
        this.validaEmail(cadastroUsuarioDTO.getEmail());
        this.validaConfirmacaoSenha(cadastroUsuarioDTO.getSenha(), cadastroUsuarioDTO.getConfirmacaoSenha());

        cadastroUsuarioDTO.setSenha(bCryptPasswordEncoder.encode(cadastroUsuarioDTO.getSenha()));

        Usuario usuario = this.modelMapper.map(cadastroUsuarioDTO, Usuario.class);
        usuario.setTipo(TipoUsuario.UNIVERSITARIO);

        this.salvaUsuario(usuario);
        return usuario;
    }

    private void validaEmail(String email) {
        if (this.usuarioRepository.findByEmail(email).isPresent()) {
            throw new ApiRequestException(String.format(EMAIL_JA_CADASTRADO, email));
        }
    }

    private void validaConfirmacaoSenha(String senha, String confirmacaoSenha) {
        if (!senha.equals(confirmacaoSenha)) {
            throw new ApiRequestException(SENHAS_DESIGUAIS);
        }
    }

    @Override
    public void salvaUsuario(Usuario usuario) {
        this.usuarioRepository.save(usuario);
    }
}
