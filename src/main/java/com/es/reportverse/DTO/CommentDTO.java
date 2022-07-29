package com.es.reportverse.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private UserDTO appUser;

    private String text;

    private Boolean isAuthorAnonymous;

    private Date creationDate;
}
