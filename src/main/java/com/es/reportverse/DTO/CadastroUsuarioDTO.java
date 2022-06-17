package com.es.reportverse.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CadastroUsuarioDTO {

    private String nome;

    private String email;

    private String senha;

    private String confirmacaoSenha;

}
