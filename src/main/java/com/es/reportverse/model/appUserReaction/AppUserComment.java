package com.es.reportverse.model.appUserReaction;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppUserComment extends AppUserReaction{

    private String text;

    private Boolean isAuthorAnonymous = false;
}
