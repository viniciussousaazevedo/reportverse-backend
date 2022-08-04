package com.es.reportverse.DTO;

import com.es.reportverse.enums.UserRole;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {

    private String name;

    private String username;

    private UserRole userRole;

}
