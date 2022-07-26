package com.es.reportverse.DTO;

import com.es.reportverse.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private UserDTO appUser;

    private String text;

    private Boolean isAuthorAnonymous;
}
