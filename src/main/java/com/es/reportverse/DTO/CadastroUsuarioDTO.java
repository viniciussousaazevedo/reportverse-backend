package com.es.reportverse.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CadastroUsuarioDTO {

    private String nome;

    private String email;

    private String senha;

    private String confirmacaoSenha;

}
