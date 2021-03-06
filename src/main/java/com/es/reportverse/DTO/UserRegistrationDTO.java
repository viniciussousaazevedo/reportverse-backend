package com.es.reportverse.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRegistrationDTO {

    private String name;

    private String username;

    private String password;

    private String passwordConfirmation;

}
