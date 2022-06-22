package com.es.reportverse.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CadastroDTO {

    private String nome;

    private String email;

    private String senha;

    private String confirmacaoSenha;

}
